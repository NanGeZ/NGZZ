#!/usr/bin/env python3
"""定位壳so中引用SHA常量的代码位置，追踪解密例程"""
import struct, sys
from capstone import Cs, CS_ARCH_ARM64, CS_MODE_ARM
from capstone.arm64 import ARM64_OP_IMM, ARM64_OP_REG

SO = 'jadx_out/resources/assets/vwwwwwvwww/arm64/lib830f622c2304076e.so'
data = open(SO, 'rb').read()

TEXT_VA, TEXT_OFF, TEXT_SZ = 0x33b40, 0x33b40, 0x6eee0
RODATA_VA = 0x14980
text = data[TEXT_OFF:TEXT_OFF+TEXT_SZ]

# 目标地址(文件偏移=VA): SHA256 IV @85380=0x14D84, SHA1 IV @85096=0x14C68
targets = {0x14D84: 'SHA256_IV', 0x14C68: 'SHA1_IV'}
md = Cs(CS_ARCH_ARM64, CS_MODE_ARM)
md.detail = True

def va_of(page, imm12):
    return page + imm12

page_map = {}
hits = []
insns = list(md.disasm(text, TEXT_VA))
print(f"disassembled {len(insns)} insns")

for i, ins in enumerate(insns):
    if ins.mnemonic == 'adrp':
        for op in ins.operands:
            if op.type == ARM64_OP_IMM:
                page_map[ins.address] = op.imm
    if ins.mnemonic == 'adr':
        for op in ins.operands:
            if op.type == ARM64_OP_IMM:
                if op.imm in targets or abs(op.imm - 0x14C00) < 0x1000:
                    hits.append((ins.address, 'adr', op.imm))

for i, ins in enumerate(insns):
    if ins.mnemonic in ('add', 'ldr', 'ldrb', 'ldrh') and ins.operands:
        # add xN, xM, #imm  → page from previous adrp xM
        if ins.operands[0].type == ARM64_OP_REG and len(ins.operands) >= 3:
            last_adrp = None
            # look back up to 8 insns for adrp of reg operand[1]
            reg = ins.operands[1].reg
            for j in range(i-1, max(0, i-10), -1):
                p = insns[j]
                if p.mnemonic == 'adrp' and p.operands[0].type == ARM64_OP_REG and p.operands[0].reg == reg:
                    last_adrp = p.operands[1].imm
                    break
            if last_adrp is not None and ins.operands[-1].type == ARM64_OP_IMM:
                addr = last_adrp + ins.operands[-1].imm
                for t, name in targets.items():
                    if t <= addr < t + 0x40:
                        hits.append((ins.address, ins.mnemonic + f' -> {name} @ {hex(addr)}', addr))

print(f"\n{len(hits)} hits:")
for addr, kind, tgt in hits:
    print(f"  {hex(addr)}: {kind}")

# 对每个命中，反汇编前后64字节
addrs = sorted(set(h[0] for h in hits))
shown = set()
for addr in addrs:
    if addr in shown: continue
    print(f"\n=== around {hex(addr)} ===")
    start = max(TEXT_VA, addr - 0x80)
    # 对齐到4
    start &= ~3
    cnt = 0
    for ins in md.disasm(data[start:start+0x180], start):
        mark = '>>>' if ins.address == addr else '   '
        print(f"{mark} {hex(ins.address)}: {ins.mnemonic} {ins.op_str}")
        cnt += 1
        if cnt > 90: break
    shown.add(addr)
