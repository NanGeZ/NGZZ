#ifndef NGZZ_OBFUSCATE_H
#define NGZZ_OBFUSCATE_H

#include <cstdint>
#include <cstddef>

namespace ngzz {

// XOR-based string encryption/decryption
template <size_t N>
class ObfString {
public:
    constexpr ObfString(const char (&str)[N], uint8_t key = 0x5A)
        : key_(key) {
        for (size_t i = 0; i < N; ++i) {
            data_[i] = str[i] ^ key_;
        }
    }

    const char* decrypt() const {
        static thread_local char buffer[N];
        for (size_t i = 0; i < N; ++i) {
            buffer[i] = data_[i] ^ key_;
        }
        return buffer;
    }

private:
    char data_[N];
    uint8_t key_;
};

// Compile-time string obfuscation
#define NGZZ_OBF(str) \
    ([]() -> const char* { \
        constexpr ngzz::ObfString<sizeof(str)> obf(str); \
        return obf.decrypt(); \
    }())

// XOR encrypt/decrypt byte buffer
inline void xor_crypt(uint8_t* data, size_t len, uint8_t key = 0x5A) {
    for (size_t i = 0; i < len; ++i) {
        data[i] ^= key;
    }
}

// Generate pseudo-random key from seed
constexpr uint8_t derive_key(uint32_t seed) {
    return static_cast<uint8_t>((seed * 0x9E3779B9) >> 24);
}

}  // namespace ngzz

#endif  // NGZZ_OBFUSCATE_H
