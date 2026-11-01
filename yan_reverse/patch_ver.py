#!/usr/bin/env python3
"""移除 so 的 GNU 符号版本机制：把 DT_VERSYM/DT_VERNEED/DT_VERNEEDNUM 标签改为未知tag"""
import struct

src = '/workspace/yan_reverse/jadx_out/resources/assets/vwwwwwvwww/arm64/lib830f622c2304076e.so'
dst = '/workspace/yan_reverse/dyn/packer_patched.so'
data = bytearray(open(src, 'rb').read())

e_phoff = struct.unpack_from('<Q', data, 0x20)[0]
e_phentsize = struct.unpack_from('<H', data, 0x36)[0]
e_phnum = struct.unpack_from('<H', data, 0x38)[0]
dyn_off = dyn_sz = 0
for i in range(e_phnum):
    p = e_phoff + i * e_phentsize
    if struct.unpack_from('<I', data, p)[0] == 2:
        dyn_off = struct.unpack_from('<Q', data, p + 0x08)[0]
        dyn_sz = struct.unpack_from('<Q', data, p + 0x20)[0]

JUNK = 0x6ffffec9  # 未知DT tag，加载器忽略
targets = {0x6ffffff0: 'DT_VERSYM', 0x6ffffffe: 'DT_VERNEED', 0x6fffffff: 'DT_VERNEEDNUM'}
pos = dyn_off
while pos < dyn_off + dyn_sz:
    tag, val = struct.unpack_from('<QQ', data, pos)
    if tag in targets:
        struct.pack_into('<Q', data, pos, JUNK)
        print(f'removed {targets[tag]}')
    if tag == 0: break
    pos += 16

open(dst, 'wb').write(data)
print('written', dst)
