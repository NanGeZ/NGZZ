#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""Extract real-statement view of methods from bad-destr file."""
import re, sys

src = open('work/PmsHookProvider.bad.destr.java', encoding='utf-8').read()

# method boundaries: header lines at 4-space indent with ( ... ) {
hdr = re.compile(r'\n    +(?:private|public|protected|static|final)[^\n=(]*\([^\n]*\)(?: throws [^\n]*)? \{')
heads = [(m.start(), m.group(0).strip()) for m in hdr.finditer(src)]
heads.append((len(src), 'EOF'))

def condense(body):
    out = []
    for ln in body.split('\n'):
        s = ln.strip()
        if re.match(r'^(i\d* = |i\d* \^=|if \(i\d*|else if \(i\d*|while \(true\) \{|int i\d+ = \d+;|int i\d+ = 1|System\.out\.println)', s):
            continue
        if s in ('{', 'break;'):
            continue
        out.append(s)
    return '\n'.join(out)

want = sys.argv[1:]
os_dir = 'work/extracted'
import os
os.makedirs(os_dir, exist_ok=True)
for idx in range(len(heads) - 1):
    start, title = heads[idx]
    end = heads[idx + 1][0]
    # method name
    mo = re.search(r'([A-Za-z_$][\w$]*)\(', title)
    name = mo.group(1) if mo else '?'
    if want and name not in want:
        continue
    body = src[start:end]
    txt = '=== %s ===\n%s\n' % (title, condense(body))
    fn = os.path.join(os_dir, name + '.txt')
    with open(fn, 'w', encoding='utf-8') as f:
        f.write(txt)
    print('%-40s %6d bytes' % (name, len(txt)))
