#include <jni.h>
#include <cstdint>
#include <cstdio>
#include <cstring>
#include <ctime>
#include <string>
#include <vector>
#include <map>
#include <algorithm>
#include <zlib.h>
#include <android/log.h>

#define LOG_TAG "ngzzpack"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

namespace {

constexpr uint32_t kEocdSig = 0x06054b50;
constexpr uint32_t kEocd64Sig = 0x06064b50;
constexpr uint32_t kEocd64LocSig = 0x07064b50;
constexpr uint32_t kCenSig = 0x02014b50;
constexpr uint32_t kLocSig = 0x04034b50;

#pragma pack(push, 1)
struct Eocd {
    uint32_t sig;
    uint16_t disk;
    uint16_t cd_disk;
    uint16_t disk_entries;
    uint16_t total_entries;
    uint32_t cd_size;
    uint32_t cd_offset;
    uint16_t comment_len;
};

struct Eocd64Loc {
    uint32_t sig;
    uint32_t disk;
    uint64_t eocd64_offset;
    uint32_t total_disks;
};

struct Eocd64 {
    uint32_t sig;
    uint64_t size;
    uint16_t made_by;
    uint16_t extract;
    uint32_t disk;
    uint32_t cd_disk;
    uint64_t disk_entries;
    uint64_t total_entries;
    uint64_t cd_size;
    uint64_t cd_offset;
};

struct CenHeader {
    uint32_t sig;
    uint16_t made_by;
    uint16_t extract;
    uint16_t flags;
    uint16_t method;
    uint32_t mod_time;
    uint32_t crc32;
    uint32_t comp_size;
    uint32_t uncomp_size;
    uint16_t name_len;
    uint16_t extra_len;
    uint16_t comment_len;
    uint16_t disk;
    uint16_t int_attr;
    uint32_t ext_attr;
    uint32_t local_offset;
};

struct LocHeader {
    uint32_t sig;
    uint16_t extract;
    uint16_t flags;
    uint16_t method;
    uint32_t mod_time;
    uint32_t crc32;
    uint32_t comp_size;
    uint32_t uncomp_size;
    uint16_t name_len;
    uint16_t extra_len;
};
#pragma pack(pop)

struct Entry {
    std::string name;
    CenHeader header = {};
    std::vector<uint8_t> extra;
    std::vector<uint8_t> comment;
    std::vector<uint8_t> data;
    std::vector<uint8_t> local_extra;
};

struct Archive {
    std::vector<Entry> entries;
    std::vector<uint8_t> comment;
    bool zip64 = false;
};

uint16_t rd16(const uint8_t* p) { uint16_t v; memcpy(&v, p, 2); return v; }
uint32_t rd32(const uint8_t* p) { uint32_t v; memcpy(&v, p, 4); return v; }

bool find_eocd(const std::vector<uint8_t>& buf, size_t& out_off, Eocd& out) {
    if (buf.size() < sizeof(Eocd)) return false;
    size_t max_back = std::min<size_t>(buf.size(), 0xffff + sizeof(Eocd));
    for (size_t i = buf.size() - sizeof(Eocd); i + 1 > buf.size() - max_back; --i) {
        if (rd32(&buf[i]) == kEocdSig) {
            memcpy(&out, &buf[i], sizeof(Eocd));
            size_t end = i + sizeof(Eocd) + out.comment_len;
            if (end == buf.size()) {
                out_off = i;
                return true;
            }
        }
        if (i == 0) break;
    }
    return false;
}

bool parse_archive(const std::vector<uint8_t>& buf, Archive& ar, std::string& err) {
    Eocd eocd{};
    size_t eocd_off = 0;
    if (!find_eocd(buf, eocd_off, eocd)) {
        err = "not a zip: EOCD not found";
        return false;
    }
    uint64_t cd_offset = eocd.cd_offset;
    uint64_t cd_size = eocd.cd_size;
    uint64_t total = eocd.total_entries;
    if (eocd.disk_entries == 0xffff || eocd.total_entries == 0xffff ||
        eocd.cd_size == 0xffffffff || eocd.cd_offset == 0xffffffff) {
        ar.zip64 = true;
        if (eocd_off < sizeof(Eocd64Loc)) {
            err = "zip64 locator missing";
            return false;
        }
        Eocd64Loc loc{};
        memcpy(&loc, &buf[eocd_off - sizeof(Eocd64Loc)], sizeof(Eocd64Loc));
        if (loc.sig != kEocd64LocSig) {
            err = "zip64 locator signature mismatch";
            return false;
        }
        Eocd64 e64{};
        memcpy(&e64, &buf[loc.eocd64_offset], sizeof(Eocd64));
        if (e64.sig != kEocd64Sig) {
            err = "zip64 eocd signature mismatch";
            return false;
        }
        cd_offset = e64.cd_offset;
        cd_size = e64.cd_size;
        total = e64.total_entries;
    }
    if (cd_offset + cd_size > buf.size()) {
        err = "central directory out of range";
        return false;
    }
    size_t p = cd_offset;
    for (uint64_t i = 0; i < total; ++i) {
        if (p + sizeof(CenHeader) > buf.size()) {
            err = "truncated central directory";
            return false;
        }
        Entry e;
        memcpy(&e.header, &buf[p], sizeof(CenHeader));
        if (e.header.sig != kCenSig) {
            err = "central header signature mismatch";
            return false;
        }
        p += sizeof(CenHeader);
        e.name.assign(reinterpret_cast<const char*>(&buf[p]), e.header.name_len);
        p += e.header.name_len;
        e.extra.assign(buf.begin() + p, buf.begin() + p + e.header.extra_len);
        p += e.header.extra_len;
        e.comment.assign(buf.begin() + p, buf.begin() + p + e.header.comment_len);
        p += e.header.comment_len;

        LocHeader lh{};
        memcpy(&lh, &buf[e.header.local_offset], sizeof(LocHeader));
        if (lh.sig != kLocSig) {
            err = "local header signature mismatch for " + e.name;
            return false;
        }
        size_t data_off = e.header.local_offset + sizeof(LocHeader) + lh.name_len + lh.extra_len;
        e.local_extra.assign(buf.begin() + e.header.local_offset + sizeof(LocHeader) + lh.name_len,
                             buf.begin() + data_off);
        uint32_t comp = e.header.comp_size;
        if (comp == 0xffffffff) {
            err = "zip64 entry sizes unsupported: " + e.name;
            return false;
        }
        if (data_off + comp > buf.size()) {
            err = "entry data out of range: " + e.name;
            return false;
        }
        e.data.assign(buf.begin() + data_off, buf.begin() + data_off + comp);
        ar.entries.push_back(std::move(e));
    }
    ar.comment.assign(buf.begin() + eocd_off + sizeof(Eocd), buf.end());
    return true;
}

std::vector<uint8_t> serialize(const Archive& ar, std::string& err) {
    std::vector<uint8_t> out;
    std::vector<uint64_t> offsets;
    out.reserve(1 << 20);
    for (const auto& e : ar.entries) {
        offsets.push_back(out.size());
        LocHeader lh{};
        lh.sig = kLocSig;
        lh.extract = e.header.extract;
        lh.flags = e.header.flags & ~0x8;
        lh.method = e.header.method;
        lh.mod_time = e.header.mod_time;
        lh.crc32 = e.header.crc32;
        lh.comp_size = e.data.size();
        lh.uncomp_size = e.header.uncomp_size;
        lh.name_len = e.name.size();
        lh.extra_len = e.local_extra.size();
        const uint8_t* raw = reinterpret_cast<const uint8_t*>(&lh);
        out.insert(out.end(), raw, raw + sizeof(lh));
        out.insert(out.end(), e.name.begin(), e.name.end());
        out.insert(out.end(), e.local_extra.begin(), e.local_extra.end());
        out.insert(out.end(), e.data.begin(), e.data.end());
    }
    uint64_t cd_start = out.size();
    for (size_t i = 0; i < ar.entries.size(); ++i) {
        const auto& e = ar.entries[i];
        CenHeader ch = e.header;
        ch.local_offset = offsets[i];
        ch.flags &= ~0x8;
        ch.comp_size = e.data.size();
        const uint8_t* raw = reinterpret_cast<const uint8_t*>(&ch);
        out.insert(out.end(), raw, raw + sizeof(ch));
        out.insert(out.end(), e.name.begin(), e.name.end());
        out.insert(out.end(), e.extra.begin(), e.extra.end());
        out.insert(out.end(), e.comment.begin(), e.comment.end());
    }
    uint64_t cd_size = out.size() - cd_start;
    if (out.size() > 0xffffffffULL || ar.entries.size() > 0xffff) {
        err = "archive too large for 32-bit zip output";
        return {};
    }
    Eocd eocd{};
    eocd.sig = kEocdSig;
    eocd.total_entries = ar.entries.size();
    eocd.disk_entries = ar.entries.size();
    eocd.cd_size = cd_size;
    eocd.cd_offset = cd_start;
    eocd.comment_len = ar.comment.size();
    const uint8_t* raw = reinterpret_cast<const uint8_t*>(&eocd);
    out.insert(out.end(), raw, raw + sizeof(eocd));
    out.insert(out.end(), ar.comment.begin(), ar.comment.end());
    return out;
}

bool is_native_lib(const std::string& name) {
    return name.rfind("lib/", 0) == 0 && name.size() > 3 &&
           name.compare(name.size() - 3, 3, ".so") == 0;
}

void inject_lib(Archive& ar, const std::string& abi, const std::vector<uint8_t>& blob) {
    std::string name = "lib/" + abi + "/libngzz.so";
    for (auto& e : ar.entries) {
        if (e.name == name) {
            e.data = blob;
            e.header.method = 0;
            e.header.crc32 = crc32(0, blob.data(), blob.size());
            e.header.comp_size = blob.size();
            e.header.uncomp_size = blob.size();
            return;
        }
    }
    Entry e;
    e.name = name;
    e.header.sig = kCenSig;
    e.header.made_by = 0x031e;
    e.header.extract = 20;
    e.header.method = 0;
    e.header.crc32 = crc32(0, blob.data(), blob.size());
    e.header.comp_size = blob.size();
    e.header.uncomp_size = blob.size();
    e.data = blob;
    ar.entries.push_back(std::move(e));
}

void align_entries(Archive& ar, uint32_t align) {
    for (auto& e : ar.entries) {
        if (!is_native_lib(e.name)) continue;
        e.local_extra.clear();
        e.header.flags &= ~0x8;
    }
}

std::string jstr2std(JNIEnv* env, jstring s) {
    const char* c = env->GetStringUTFChars(s, nullptr);
    std::string out(c);
    env->ReleaseStringUTFChars(s, c);
    return out;
}

std::map<std::string, std::vector<uint8_t>> extract_payload(JNIEnv* env, jobject payload) {
    std::map<std::string, std::vector<uint8_t>> result;
    if (payload == nullptr) return result;

    jclass map_class = env->FindClass("java/util/Map");
    if (map_class == nullptr) return result;

    jmethodID entry_set = env->GetMethodID(map_class, "entrySet", "()Ljava/util/Set;");
    if (entry_set == nullptr) return result;

    jobject entry_set_obj = env->CallObjectMethod(payload, entry_set);
    if (entry_set_obj == nullptr) return result;

    jclass set_class = env->FindClass("java/util/Set");
    jmethodID to_array = env->GetMethodID(set_class, "toArray", "()[Ljava/lang/Object;");
    jobjectArray entries = (jobjectArray)env->CallObjectMethod(entry_set_obj, to_array);
    if (entries == nullptr) return result;

    jclass entry_class = env->FindClass("java/util/Map$Entry");
    jmethodID key_method = env->GetMethodID(entry_class, "getKey", "()Ljava/lang/Object;");
    jmethodID value_method = env->GetMethodID(entry_class, "getValue", "()Ljava/lang/Object;");

    jsize count = env->GetArrayLength(entries);
    for (jsize i = 0; i < count; i++) {
        jobject entry = env->GetObjectArrayElement(entries, i);
        jstring key = (jstring)env->CallObjectMethod(entry, key_method);
        jbyteArray value = (jbyteArray)env->CallObjectMethod(entry, value_method);

        const char* key_chars = env->GetStringUTFChars(key, nullptr);
        std::string abi = key_chars ? key_chars : "";
        if (key_chars) env->ReleaseStringUTFChars(key, key_chars);

        std::vector<uint8_t> blob;
        if (value != nullptr) {
            jsize len = env->GetArrayLength(value);
            blob.resize(static_cast<size_t>(len));
            env->GetByteArrayRegion(value, 0, len, reinterpret_cast<jbyte*>(blob.data()));
        }
        result[abi] = std::move(blob);
    }
    return result;
}

}  // namespace

extern "C" JNIEXPORT jstring JNICALL
Java_com_ngzz_protect_NativePack_protect(
    JNIEnv* env, jobject thiz,
    jstring inputApk, jbyteArray config, jstring outputApk, jobject payload) {
    (void)thiz;

    std::string in = jstr2std(env, inputApk);
    std::string out = jstr2std(env, outputApk);

    jsize cfgLen = env->GetArrayLength(config);
    std::vector<uint8_t> cfg(cfgLen);
    env->GetByteArrayRegion(config, 0, cfgLen, reinterpret_cast<jbyte*>(cfg.data()));

    auto payload_map = extract_payload(env, payload);

    FILE* fin = fopen(in.c_str(), "rb");
    if (!fin) return env->NewStringUTF("无法读取输入 APK");
    fseek(fin, 0, SEEK_END);
    long size = ftell(fin);
    fseek(fin, 0, SEEK_SET);
    std::vector<uint8_t> buf(size);
    if (fread(buf.data(), 1, size, fin) != (size_t)size) {
        fclose(fin);
        return env->NewStringUTF("读取输入 APK 失败");
    }
    fclose(fin);

    Archive ar;
    std::string err;
    if (!parse_archive(buf, ar, err)) {
        LOGE("parse failed: %s", err.c_str());
        return env->NewStringUTF(("解析 APK 失败: " + err).c_str());
    }

    for (const auto& [abi, blob] : payload_map) {
        if (!blob.empty()) {
            inject_lib(ar, abi, blob);
        }
    }

    if (!cfg.empty()) {
        Entry ce;
        ce.name = "assets/ngzz/config.bin";
        ce.header.sig = kCenSig;
        ce.header.made_by = 0x031e;
        ce.header.extract = 20;
        ce.header.method = 0;  // STORED，运行时引擎直接 ZipFile 读取
        ce.header.crc32 = crc32(0, cfg.data(), cfg.size());
        ce.header.comp_size = cfg.size();
        ce.header.uncomp_size = cfg.size();
        ce.data = cfg;
        ar.entries.push_back(std::move(ce));
    }

    align_entries(ar, 4096);

    LOGI("injected libngzz.so; aligned entries");
    std::vector<uint8_t> out_buf = serialize(ar, err);
    if (out_buf.empty()) {
        return env->NewStringUTF(("打包失败: " + err).c_str());
    }

    FILE* fout = fopen(out.c_str(), "wb");
    if (!fout) return env->NewStringUTF("无法写出输出 APK");
    fwrite(out_buf.data(), 1, out_buf.size(), fout);
    fclose(fout);

    LOGI("protect ok: %s -> %s (%zu entries)", in.c_str(), out.c_str(), ar.entries.size());
    return env->NewStringUTF(out.c_str());
}
