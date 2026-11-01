#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""Deobfuscate PmsHookProvider.java:
1. decode short[] table strings:  mXXX(m19(), off, KEY^CONST, len)
2. decode m1(new int[]{...}) ASCII arrays
3. condense opaque state-machine scaffolding (keep real statements)
4. dump dispatcher map for mNN wrappers
"""
import re, sys

SRC = 'work/hook_out/sources/com/yan/hook/PmsHookProvider.java'
src = open(SRC, encoding='utf-8').read()

# ---- field values ----
F = {'C0000.f4': 87, 'C0001.f5': -579, 'C0002.f6': -482,
     'C0003.f12': 685, 'C0004.f13': -363}

# ---- f0short table ----
m = re.search(r'f0short = \{(.*?)\};', src, re.S)
tbl = [int(x) for x in m.group(1).split(',')]

def tdec(off, ln, key):
    if off < 0 or off + ln > len(tbl):
        return '<OOB:%d+%d>' % (off, ln)
    out = []
    for i in range(ln):
        out.append(chr((tbl[off + i] ^ key) & 0xFFFF))
    return ''.join(out)

# ---- 1. table-string calls ----
pat = re.compile(r'(?:m285|m191|m323|m169)\(m19\(\), (\d+), (C000\d\.f\d+) \^ (?:\((-?\d+)\)|(\d+)), (\d+)\)')
def rep(mo):
    off = int(mo.group(1))
    const = int(mo.group(3)) if mo.group(3) is not None else int(mo.group(4))
    ln = F[mo.group(2)] ^ const
    key = int(mo.group(5))
    s = tdec(off, ln, key)
    return '"%s"' % s
src, ntab = pat.subn(rep, src)

# ---- 2. m1(new int[]{...}) ----
def rep1(mo):
    vals = [int(x) for x in mo.group(1).split(',')]
    return '"' + ''.join(chr(v) for v in vals) + '"'
src, narr = re.subn(r'm1\(new int\[\]\{([\d, ]+)\}\)', rep1, src)

open('work/PmsHookProvider.destr.java', 'w', encoding='utf-8').write(src)
print('table strings decoded:', ntab, ' ascii arrays:', narr)

# ---- 3. condense scaffolding ----
lines = src.split('\n')
drop_exact = re.compile(
    r'^\s*(int i\d* = \d+;|i\d* \^= \d+;|while \(true\) \{|if \(i\d* != 14\) \{|'
    r'if \(i\d* != 49\) \{|i\d* = 1678;|i\d* = 1771;|i\d* = 48674;|i\d* = 48798;|'
    r'i\d* = 48891;|i\d* = 48922;|i\d* = \d+ \^ \d+;)$')
drop_contains = re.compile(r'System\.out\.println\(')
# also drop: `if (i == 204) {` / `if (i == 239) {` / `else if (i == 239) {` markers, and
# `if (i2 == N) {` scaffold lines, `} else if (i2 == 54) {` etc. but KEEP ternary assignments & real ifs
drop_state = re.compile(r'^\s*(\}|else \})? ?if \(i\d* (==|!=) \d+\) ?\{?$')
drop_state2 = re.compile(r'^\s*(\}|else \}) ?if \(i\d* (==|!=) \d+\) ?\{?$')
assign_state = re.compile(r'^\s*i\d* = \d+;$')

out = []
for ln in lines:
    s = ln.strip()
    if drop_exact.match(ln):
        continue
    if drop_state.match(ln) or drop_state2.match(ln):
        continue
    if assign_state.match(ln):
        continue
    if drop_contains.search(s) and ('println' in s):
        continue
    out.append(ln)
open('work/PmsHookProvider.condensed.java', 'w', encoding='utf-8').write('\n'.join(out))
print('condensed lines:', len(out), 'of', len(lines))

# ---- 4. dispatcher map ----
disp = {}
for mo in re.finditer(r'public static ([\w\[\]<>\.]+) (m\d+)\(([^)]*)\) \{(.*?)\n    \}', src, re.S):
    ret, name, params, body = mo.groups()
    real = [l.strip() for l in body.split('\n')
            if l.strip() and not re.match(r'(int i\d*|i\d* \^=|while|if \(i|else|i\d* =|System\.out)', l.strip())
            and l.strip() not in ('{', '}', 'break;')]
    disp[name] = (ret, real[:3])
with open('work/dispatch_map.txt', 'w', encoding='utf-8') as f:
    for k, v in sorted(disp.items(), key=lambda x: int(x[0][1:])):
        f.write('%s -> %s : %s\n' % (k, v[0], v[1]))
print('dispatchers:', len(disp))
