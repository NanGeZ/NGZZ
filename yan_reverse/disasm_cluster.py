#!/usr/bin/env python3
"""反汇编 .text 末尾 0x9e000-0xa2a20 区域（SHA常量密集引用区）"""
import struct
from capstone import Cs, CS_ARCH_ARM64, CS_MODE_ARM
from capstone.arm64 import ARM64_OP_IMM

data = open('jadx_out/resources/assets/vwwwwwvwww/arm64/lib830f622c2304076e.so','rb').read()
md = Cs(CS_ARCH_ARM64, CS_MODE_ARM)
md.detail = True

targets = {0x14D84: 'SHA256IV', 0x14C68: 'SHA1IV'}

START, END = 0x9e800, 0xa2a20
insns = list(md.disasm(data[START:END], START))

# 计算每个adr指向的目标
adr_targets = {}
for i, ins in enumerate(insns):
    if ins.mnemonic == 'adr':
        for op in ins.operands:
            if op.type == ARM64_OP_IMM:
                adr_targets[ins.address] = op.imm

for ins in insns:
    t = adr_targets.get(ins.address)
    tag = ''
    if t is not None:
        for ta, name in targets.items():
            if ta <= t < ta + 0x40:
                tag = f'   ; {name} @ {hex(t)}'
    print(f"{hex(ins.address)}: {ins.mnemonic} {ins.op_str}{tag}")
