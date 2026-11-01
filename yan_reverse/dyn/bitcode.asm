
packer_unpacked.so:     file format elf64-littleaarch64


Disassembly of section .bitcode:

00000000000a2a20 <JNI_OnLoad@@Base-0x1e18>:
   a2a20:	d10343ff 	sub	sp, sp, #0xd0
   a2a24:	a9097bfd 	stp	x29, x30, [sp, #144]
   a2a28:	f90053f7 	str	x23, [sp, #160]
   a2a2c:	a90b57f6 	stp	x22, x21, [sp, #176]
   a2a30:	a90c4ff4 	stp	x20, x19, [sp, #192]
   a2a34:	910243fd 	add	x29, sp, #0x90
   a2a38:	d53bd057 	mrs	x23, tpidr_el0
   a2a3c:	aa0003f3 	orr	x19, xzr, x0
   a2a40:	f94016e8 	ldr	x8, [x23, #40]
   a2a44:	f81f83a8 	stur	x8, [x29, #-8]
   a2a48:	f9400008 	ldr	x8, [x0]
   a2a4c:	f9429d08 	ldr	x8, [x8, #1336]
   a2a50:	d63f0100 	blr	x8
   a2a54:	aa0003f4 	orr	x20, xzr, x0
   a2a58:	b0fffba1 	adrp	x1, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a2a5c:	91247821 	add	x1, x1, #0x91e
   a2a60:	aa1303e0 	orr	x0, xzr, x19
   a2a64:	97fe7b31 	bl	41728 <JNI_OnUnload@@Base+0xc748>
   a2a68:	aa0003e1 	orr	x1, xzr, x0
   a2a6c:	d0fffba2 	adrp	x2, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a2a70:	91027842 	add	x2, x2, #0x9e
   a2a74:	aa1303e0 	orr	x0, xzr, x19
   a2a78:	aa1403e3 	orr	x3, xzr, x20
   a2a7c:	97fe7b48 	bl	4179c <JNI_OnUnload@@Base+0xc7bc>
   a2a80:	f0fffb95 	adrp	x21, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a2a84:	913e8eb5 	add	x21, x21, #0xfa3
   a2a88:	aa0003f4 	orr	x20, xzr, x0
   a2a8c:	d0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a2a90:	912ba108 	add	x8, x8, #0xae8
   a2a94:	aa1303e0 	orr	x0, xzr, x19
   a2a98:	aa1503e1 	orr	x1, xzr, x21
   a2a9c:	f9000ff5 	str	x21, [sp, #24]
   a2aa0:	a9004fe8 	stp	x8, x19, [sp]
   a2aa4:	97fe7b21 	bl	41728 <JNI_OnUnload@@Base+0xc748>
   a2aa8:	f0fffb96 	adrp	x22, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a2aac:	9126bed6 	add	x22, x22, #0x9af
   a2ab0:	aa0003e1 	orr	x1, xzr, x0
   a2ab4:	aa1303e0 	orr	x0, xzr, x19
   a2ab8:	aa1603e2 	orr	x2, xzr, x22
   a2abc:	97fe7b38 	bl	4179c <JNI_OnUnload@@Base+0xc7bc>
   a2ac0:	f9000be0 	str	x0, [sp, #16]
   a2ac4:	910003e0 	add	x0, sp, #0x0
   a2ac8:	aa1403e1 	orr	x1, xzr, x20
   a2acc:	97fe8d4e 	bl	46004 <JNI_OnUnload@@Base+0x11024>
   a2ad0:	aa1303e0 	orr	x0, xzr, x19
   a2ad4:	aa1503e1 	orr	x1, xzr, x21
   a2ad8:	97fe7b14 	bl	41728 <JNI_OnUnload@@Base+0xc748>
   a2adc:	aa0003e1 	orr	x1, xzr, x0
   a2ae0:	aa1303e0 	orr	x0, xzr, x19
   a2ae4:	aa1603e2 	orr	x2, xzr, x22
   a2ae8:	97fe7b2d 	bl	4179c <JNI_OnUnload@@Base+0xc7bc>
   a2aec:	aa0003f5 	orr	x21, xzr, x0
   a2af0:	940015ac 	bl	a81a0 <clock@plt>
   a2af4:	6f00e400 	movi	v0.2d, #0x0
   a2af8:	aa0003f4 	orr	x20, xzr, x0
   a2afc:	d0fffba0 	adrp	x0, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a2b00:	9102d000 	add	x0, x0, #0xb4
   a2b04:	910083e1 	add	x1, sp, #0x20
   a2b08:	3c86c3e0 	stur	q0, [sp, #108]
   a2b0c:	ad0183e0 	stp	q0, q0, [sp, #48]
   a2b10:	ad0283e0 	stp	q0, q0, [sp, #80]
   a2b14:	3d800be0 	str	q0, [sp, #32]
   a2b18:	940015a6 	bl	a81b0 <__system_property_get@plt>
   a2b1c:	7100041f 	subs	wzr, w0, #0x1
   a2b20:	540001ab 	b.lt	a2b54 <JNI_OnUnload@@Base+0x6db74>  // b.tstop
   a2b24:	910083e0 	add	x0, sp, #0x20
   a2b28:	940015a6 	bl	a81c0 <atoi@plt>
   a2b2c:	7100001f 	subs	wzr, w0, #0x0
   a2b30:	5400012d 	b.le	a2b54 <JNI_OnUnload@@Base+0x6db74>
   a2b34:	71005c1f 	subs	wzr, w0, #0x17
   a2b38:	540000e3 	b.cc	a2b54 <JNI_OnUnload@@Base+0x6db74>  // b.lo, b.ul, b.last
   a2b3c:	f9400be1 	ldr	x1, [sp, #16]
   a2b40:	aa1303e0 	orr	x0, xzr, x19
   a2b44:	aa1f03e2 	orr	x2, xzr, xzr
   a2b48:	aa1503e3 	orr	x3, xzr, x21
   a2b4c:	97fe8cdd 	bl	45ec0 <JNI_OnUnload@@Base+0x10ee0>
   a2b50:	14000006 	b	a2b68 <JNI_OnUnload@@Base+0x6db88>
   a2b54:	f9400be1 	ldr	x1, [sp, #16]
   a2b58:	aa1303e0 	orr	x0, xzr, x19
   a2b5c:	aa1f03e2 	orr	x2, xzr, xzr
   a2b60:	aa1503e3 	orr	x3, xzr, x21
   a2b64:	97fe8cf1 	bl	45f28 <JNI_OnUnload@@Base+0x10f48>
   a2b68:	aa0003f3 	orr	x19, xzr, x0
   a2b6c:	d503201f 	hint	#0x0
   a2b70:	70ba88e0 	adr	x0, 17c8f <_ZTSNSt6__ndk117bad_function_callE@@Base-0x125b>
   a2b74:	aa1403e1 	orr	x1, xzr, x20
   a2b78:	97fe83aa 	bl	43a20 <JNI_OnUnload@@Base+0xea40>
   a2b7c:	f94016e8 	ldr	x8, [x23, #40]
   a2b80:	f85f83a9 	ldur	x9, [x29, #-8]
   a2b84:	eb09011f 	subs	xzr, x8, x9
   a2b88:	54000101 	b.ne	a2ba8 <JNI_OnUnload@@Base+0x6dbc8>  // b.any
   a2b8c:	aa1303e0 	orr	x0, xzr, x19
   a2b90:	a94c4ff4 	ldp	x20, x19, [sp, #192]
   a2b94:	f94053f7 	ldr	x23, [sp, #160]
   a2b98:	a94b57f6 	ldp	x22, x21, [sp, #176]
   a2b9c:	a9497bfd 	ldp	x29, x30, [sp, #144]
   a2ba0:	910343ff 	add	sp, sp, #0xd0
   a2ba4:	d65f03c0 	ret
   a2ba8:	9400158a 	bl	a81d0 <__stack_chk_fail@plt>
   a2bac:	d10303ff 	sub	sp, sp, #0xc0
   a2bb0:	a9067bfd 	stp	x29, x30, [sp, #96]
   a2bb4:	f9003bfb 	str	x27, [sp, #112]
   a2bb8:	a90867fa 	stp	x26, x25, [sp, #128]
   a2bbc:	a9095ff8 	stp	x24, x23, [sp, #144]
   a2bc0:	a90a57f6 	stp	x22, x21, [sp, #160]
   a2bc4:	a90b4ff4 	stp	x20, x19, [sp, #176]
   a2bc8:	910183fd 	add	x29, sp, #0x60
   a2bcc:	d53bd05b 	mrs	x27, tpidr_el0
   a2bd0:	aa0303e1 	orr	x1, xzr, x3
   a2bd4:	aa0203f5 	orr	x21, xzr, x2
   a2bd8:	f9401768 	ldr	x8, [x27, #40]
   a2bdc:	aa0003f3 	orr	x19, xzr, x0
   a2be0:	f81f83a8 	stur	x8, [x29, #-8]
   a2be4:	97ffff8f 	bl	a2a20 <JNI_OnUnload@@Base+0x6da40>
   a2be8:	d0fffba8 	adrp	x8, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a2bec:	91159109 	add	x9, x8, #0x564
   a2bf0:	aa0003f4 	orr	x20, xzr, x0
   a2bf4:	d0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a2bf8:	912c6108 	add	x8, x8, #0xb18
   a2bfc:	9100c3e0 	add	x0, sp, #0x30
   a2c00:	a9034fe8 	stp	x8, x19, [sp, #48]
   a2c04:	b0fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a2c08:	912a9508 	add	x8, x8, #0xaa5
   a2c0c:	a904a7e8 	stp	x8, x9, [sp, #72]
   a2c10:	f90023f5 	str	x21, [sp, #64]
   a2c14:	97fe8ca7 	bl	45eb0 <JNI_OnUnload@@Base+0x10ed0>
   a2c18:	b0fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a2c1c:	9113f109 	add	x9, x8, #0x4fc
   a2c20:	f9000fe0 	str	x0, [sp, #24]
   a2c24:	d0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a2c28:	912d2108 	add	x8, x8, #0xb48
   a2c2c:	910023e0 	add	x0, sp, #0x8
   a2c30:	a900cfe8 	stp	x8, x19, [sp, #8]
   a2c34:	90fffba8 	adrp	x8, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a2c38:	91347508 	add	x8, x8, #0xd1d
   a2c3c:	a90227e8 	stp	x8, x9, [sp, #32]
   a2c40:	97fe8cd4 	bl	45f90 <JNI_OnUnload@@Base+0x10fb0>
   a2c44:	f9400268 	ldr	x8, [x19]
   a2c48:	aa0003f8 	orr	x24, xzr, x0
   a2c4c:	aa1303e0 	orr	x0, xzr, x19
   a2c50:	aa1403e1 	orr	x1, xzr, x20
   a2c54:	f942ad08 	ldr	x8, [x8, #1368]
   a2c58:	d63f0100 	blr	x8
   a2c5c:	f9400268 	ldr	x8, [x19]
   a2c60:	2a0003f9 	orr	w25, wzr, w0
   a2c64:	aa1303e0 	orr	x0, xzr, x19
   a2c68:	aa1803e1 	orr	x1, xzr, x24
   a2c6c:	f942ad08 	ldr	x8, [x8, #1368]
   a2c70:	d63f0100 	blr	x8
   a2c74:	2a0003f5 	orr	w21, wzr, w0
   a2c78:	d0fffba1 	adrp	x1, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a2c7c:	9129ac21 	add	x1, x1, #0xa6b
   a2c80:	aa1303e0 	orr	x0, xzr, x19
   a2c84:	97fe7aa9 	bl	41728 <JNI_OnUnload@@Base+0xc748>
   a2c88:	f9400268 	ldr	x8, [x19]
   a2c8c:	0b1902b6 	add	w22, w21, w25
   a2c90:	aa0003e2 	orr	x2, xzr, x0
   a2c94:	aa1303e0 	orr	x0, xzr, x19
   a2c98:	2a1603e1 	orr	w1, wzr, w22
   a2c9c:	aa1f03e3 	orr	x3, xzr, xzr
   a2ca0:	f942b108 	ldr	x8, [x8, #1376]
   a2ca4:	d63f0100 	blr	x8
   a2ca8:	710006bf 	subs	wzr, w21, #0x1
   a2cac:	aa0003f7 	orr	x23, xzr, x0
   a2cb0:	5400024b 	b.lt	a2cf8 <JNI_OnUnload@@Base+0x6dd18>  // b.tstop
   a2cb4:	2a1f03fa 	orr	w26, wzr, wzr
   a2cb8:	f9400268 	ldr	x8, [x19]
   a2cbc:	aa1303e0 	orr	x0, xzr, x19
   a2cc0:	aa1803e1 	orr	x1, xzr, x24
   a2cc4:	2a1a03e2 	orr	w2, wzr, w26
   a2cc8:	f942b508 	ldr	x8, [x8, #1384]
   a2ccc:	d63f0100 	blr	x8
   a2cd0:	f9400268 	ldr	x8, [x19]
   a2cd4:	aa0003e3 	orr	x3, xzr, x0
   a2cd8:	aa1303e0 	orr	x0, xzr, x19
   a2cdc:	aa1703e1 	orr	x1, xzr, x23
   a2ce0:	2a1a03e2 	orr	w2, wzr, w26
   a2ce4:	f942b908 	ldr	x8, [x8, #1392]
   a2ce8:	d63f0100 	blr	x8
   a2cec:	1100075a 	add	w26, w26, #0x1
   a2cf0:	6b1a02bf 	subs	wzr, w21, w26
   a2cf4:	54fffe21 	b.ne	a2cb8 <JNI_OnUnload@@Base+0x6dcd8>  // b.any
   a2cf8:	7100073f 	subs	wzr, w25, #0x1
   a2cfc:	5400026b 	b.lt	a2d48 <JNI_OnUnload@@Base+0x6dd68>  // b.tstop
   a2d00:	2a1f03f8 	orr	w24, wzr, wzr
   a2d04:	f9400268 	ldr	x8, [x19]
   a2d08:	aa1303e0 	orr	x0, xzr, x19
   a2d0c:	aa1403e1 	orr	x1, xzr, x20
   a2d10:	2a1803e2 	orr	w2, wzr, w24
   a2d14:	f942b508 	ldr	x8, [x8, #1384]
   a2d18:	d63f0100 	blr	x8
   a2d1c:	f9400268 	ldr	x8, [x19]
   a2d20:	aa0003e3 	orr	x3, xzr, x0
   a2d24:	0b1802a2 	add	w2, w21, w24
   a2d28:	aa1303e0 	orr	x0, xzr, x19
   a2d2c:	aa1703e1 	orr	x1, xzr, x23
   a2d30:	f942b908 	ldr	x8, [x8, #1392]
   a2d34:	d63f0100 	blr	x8
   a2d38:	11000718 	add	w24, w24, #0x1
   a2d3c:	0b1802a8 	add	w8, w21, w24
   a2d40:	6b16011f 	subs	wzr, w8, w22
   a2d44:	54fffe0b 	b.lt	a2d04 <JNI_OnUnload@@Base+0x6dd24>  // b.tstop
   a2d48:	910023e0 	add	x0, sp, #0x8
   a2d4c:	aa1703e1 	orr	x1, xzr, x23
   a2d50:	97fe8c94 	bl	45fa0 <JNI_OnUnload@@Base+0x10fc0>
   a2d54:	f9401768 	ldr	x8, [x27, #40]
   a2d58:	f85f83a9 	ldur	x9, [x29, #-8]
   a2d5c:	eb09011f 	subs	xzr, x8, x9
   a2d60:	54000121 	b.ne	a2d84 <JNI_OnUnload@@Base+0x6dda4>  // b.any
   a2d64:	a94b4ff4 	ldp	x20, x19, [sp, #176]
   a2d68:	f9403bfb 	ldr	x27, [sp, #112]
   a2d6c:	a94a57f6 	ldp	x22, x21, [sp, #160]
   a2d70:	a9495ff8 	ldp	x24, x23, [sp, #144]
   a2d74:	a94867fa 	ldp	x26, x25, [sp, #128]
   a2d78:	a9467bfd 	ldp	x29, x30, [sp, #96]
   a2d7c:	910303ff 	add	sp, sp, #0xc0
   a2d80:	d65f03c0 	ret
   a2d84:	94001513 	bl	a81d0 <__stack_chk_fail@plt>
   a2d88:	d10503ff 	sub	sp, sp, #0x140
   a2d8c:	a9117bfd 	stp	x29, x30, [sp, #272]
   a2d90:	a91257fc 	stp	x28, x21, [sp, #288]
   a2d94:	a9134ff4 	stp	x20, x19, [sp, #304]
   a2d98:	910443fd 	add	x29, sp, #0x110
   a2d9c:	6f00e400 	movi	v0.2d, #0x0
   a2da0:	d53bd055 	mrs	x21, tpidr_el0
   a2da4:	aa0203f3 	orr	x19, xzr, x2
   a2da8:	f94016a8 	ldr	x8, [x21, #40]
   a2dac:	910003e1 	add	x1, sp, #0x0
   a2db0:	52802002 	movz	w2, #0x100
   a2db4:	aa0003f4 	orr	x20, xzr, x0
   a2db8:	f81f83a8 	stur	x8, [x29, #-8]
   a2dbc:	ad0003e0 	stp	q0, q0, [sp]
   a2dc0:	ad0103e0 	stp	q0, q0, [sp, #32]
   a2dc4:	ad0203e0 	stp	q0, q0, [sp, #64]
   a2dc8:	ad0303e0 	stp	q0, q0, [sp, #96]
   a2dcc:	ad0403e0 	stp	q0, q0, [sp, #128]
   a2dd0:	ad0503e0 	stp	q0, q0, [sp, #160]
   a2dd4:	ad0603e0 	stp	q0, q0, [sp, #192]
   a2dd8:	ad0703e0 	stp	q0, q0, [sp, #224]
   a2ddc:	97fe81f2 	bl	435a4 <JNI_OnUnload@@Base+0xe5c4>
   a2de0:	910003e3 	add	x3, sp, #0x0
   a2de4:	aa1403e0 	orr	x0, xzr, x20
   a2de8:	aa1303e2 	orr	x2, xzr, x19
   a2dec:	97ffff70 	bl	a2bac <JNI_OnUnload@@Base+0x6dbcc>
   a2df0:	aa1403e0 	orr	x0, xzr, x20
   a2df4:	94000f4a 	bl	a6b1c <JNI_OnLoad@@Base+0x22e4>
   a2df8:	f94016a8 	ldr	x8, [x21, #40]
   a2dfc:	f85f83a9 	ldur	x9, [x29, #-8]
   a2e00:	eb09011f 	subs	xzr, x8, x9
   a2e04:	540000c1 	b.ne	a2e1c <JNI_OnUnload@@Base+0x6de3c>  // b.any
   a2e08:	a9534ff4 	ldp	x20, x19, [sp, #304]
   a2e0c:	a95257fc 	ldp	x28, x21, [sp, #288]
   a2e10:	a9517bfd 	ldp	x29, x30, [sp, #272]
   a2e14:	910503ff 	add	sp, sp, #0x140
   a2e18:	d65f03c0 	ret
   a2e1c:	940014ed 	bl	a81d0 <__stack_chk_fail@plt>
   a2e20:	d104c3ff 	sub	sp, sp, #0x130
   a2e24:	a90d7bfd 	stp	x29, x30, [sp, #208]
   a2e28:	a90e6ffc 	stp	x28, x27, [sp, #224]
   a2e2c:	a90f67fa 	stp	x26, x25, [sp, #240]
   a2e30:	a9105ff8 	stp	x24, x23, [sp, #256]
   a2e34:	a91157f6 	stp	x22, x21, [sp, #272]
   a2e38:	a9124ff4 	stp	x20, x19, [sp, #288]
   a2e3c:	910343fd 	add	x29, sp, #0xd0
   a2e40:	d53bd048 	mrs	x8, tpidr_el0
   a2e44:	aa0003f3 	orr	x19, xzr, x0
   a2e48:	aa0303f6 	orr	x22, xzr, x3
   a2e4c:	f90007e8 	str	x8, [sp, #8]
   a2e50:	f9401508 	ldr	x8, [x8, #40]
   a2e54:	f81f03a8 	stur	x8, [x29, #-16]
   a2e58:	d0fffba8 	adrp	x8, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a2e5c:	91159109 	add	x9, x8, #0x564
   a2e60:	d0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a2e64:	912c6108 	add	x8, x8, #0xb18
   a2e68:	f81d83a2 	stur	x2, [x29, #-40]
   a2e6c:	a93c83a8 	stp	x8, x0, [x29, #-56]
   a2e70:	b0fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a2e74:	912a9508 	add	x8, x8, #0xaa5
   a2e78:	d100e3a0 	sub	x0, x29, #0x38
   a2e7c:	a93e27a8 	stp	x8, x9, [x29, #-32]
   a2e80:	97fe8c0c 	bl	45eb0 <JNI_OnUnload@@Base+0x10ed0>
   a2e84:	b0fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a2e88:	9113f109 	add	x9, x8, #0x4fc
   a2e8c:	f81b03a0 	stur	x0, [x29, #-80]
   a2e90:	d0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a2e94:	912d2108 	add	x8, x8, #0xb48
   a2e98:	d10183a0 	sub	x0, x29, #0x60
   a2e9c:	a93a4fa8 	stp	x8, x19, [x29, #-96]
   a2ea0:	90fffba8 	adrp	x8, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a2ea4:	91347508 	add	x8, x8, #0xd1d
   a2ea8:	a93ba7a8 	stp	x8, x9, [x29, #-72]
   a2eac:	97fe8c39 	bl	45f90 <JNI_OnUnload@@Base+0x10fb0>
   a2eb0:	f9400268 	ldr	x8, [x19]
   a2eb4:	aa0003f4 	orr	x20, xzr, x0
   a2eb8:	aa1303e0 	orr	x0, xzr, x19
   a2ebc:	aa1403e1 	orr	x1, xzr, x20
   a2ec0:	f942ad08 	ldr	x8, [x8, #1368]
   a2ec4:	d63f0100 	blr	x8
   a2ec8:	f9400268 	ldr	x8, [x19]
   a2ecc:	2a0003f5 	orr	w21, wzr, w0
   a2ed0:	aa1303e0 	orr	x0, xzr, x19
   a2ed4:	aa1603e1 	orr	x1, xzr, x22
   a2ed8:	aa1f03e2 	orr	x2, xzr, xzr
   a2edc:	f942a508 	ldr	x8, [x8, #1352]
   a2ee0:	d63f0100 	blr	x8
   a2ee4:	aa0003f6 	orr	x22, xzr, x0
   a2ee8:	d000005c 	adrp	x28, ac000 <pthread_key_delete@plt+0x3580>
   a2eec:	912de39c 	add	x28, x28, #0xb78
   a2ef0:	b0fffbbb 	adrp	x27, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a2ef4:	9121c37b 	add	x27, x27, #0x870
   a2ef8:	710006bf 	subs	wzr, w21, #0x1
   a2efc:	5400064b 	b.lt	a2fc4 <JNI_OnUnload@@Base+0x6dfe4>  // b.tstop
   a2f00:	2a1f03f8 	orr	w24, wzr, wzr
   a2f04:	2a1503f7 	orr	w23, wzr, w21
   a2f08:	14000004 	b	a2f18 <JNI_OnUnload@@Base+0x6df38>
   a2f0c:	11000718 	add	w24, w24, #0x1
   a2f10:	6b1802bf 	subs	wzr, w21, w24
   a2f14:	540005a0 	b.eq	a2fc8 <JNI_OnUnload@@Base+0x6dfe8>  // b.none
   a2f18:	f9400268 	ldr	x8, [x19]
   a2f1c:	aa1303e0 	orr	x0, xzr, x19
   a2f20:	aa1403e1 	orr	x1, xzr, x20
   a2f24:	2a1803e2 	orr	w2, wzr, w24
   a2f28:	f942b508 	ldr	x8, [x8, #1384]
   a2f2c:	d63f0100 	blr	x8
   a2f30:	f0fffb88 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a2f34:	913edd08 	add	x8, x8, #0xfb7
   a2f38:	f9002fe0 	str	x0, [sp, #88]
   a2f3c:	910123e0 	add	x0, sp, #0x48
   a2f40:	a90623fb 	stp	x27, x8, [sp, #96]
   a2f44:	a904cffc 	stp	x28, x19, [sp, #72]
   a2f48:	97fe8c1b 	bl	45fb4 <JNI_OnUnload@@Base+0x10fd4>
   a2f4c:	d0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a2f50:	912aa108 	add	x8, x8, #0xaa8
   a2f54:	a902cfe8 	stp	x8, x19, [sp, #40]
   a2f58:	b0fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a2f5c:	91247908 	add	x8, x8, #0x91e
   a2f60:	a903a3e0 	stp	x0, x8, [sp, #56]
   a2f64:	9100a3e0 	add	x0, sp, #0x28
   a2f68:	97fe8c17 	bl	45fc4 <JNI_OnUnload@@Base+0x10fe4>
   a2f6c:	b4fffd00 	cbz	x0, a2f0c <JNI_OnUnload@@Base+0x6df2c>
   a2f70:	f9400268 	ldr	x8, [x19]
   a2f74:	aa0003f9 	orr	x25, xzr, x0
   a2f78:	aa1303e0 	orr	x0, xzr, x19
   a2f7c:	aa1903e1 	orr	x1, xzr, x25
   a2f80:	aa1f03e2 	orr	x2, xzr, xzr
   a2f84:	f942a508 	ldr	x8, [x8, #1352]
   a2f88:	d63f0100 	blr	x8
   a2f8c:	aa1603e1 	orr	x1, xzr, x22
   a2f90:	52802002 	movz	w2, #0x100
   a2f94:	aa0003fa 	orr	x26, xzr, x0
   a2f98:	94001492 	bl	a81e0 <strncmp@plt>
   a2f9c:	f9400268 	ldr	x8, [x19]
   a2fa0:	7100001f 	subs	wzr, w0, #0x0
   a2fa4:	aa1303e0 	orr	x0, xzr, x19
   a2fa8:	1a9f17e9 	csinc	w9, wzr, wzr, ne	// ne = any
   a2fac:	aa1903e1 	orr	x1, xzr, x25
   a2fb0:	aa1a03e2 	orr	x2, xzr, x26
   a2fb4:	f942a908 	ldr	x8, [x8, #1360]
   a2fb8:	4b0902f7 	sub	w23, w23, w9
   a2fbc:	d63f0100 	blr	x8
   a2fc0:	17ffffd3 	b	a2f0c <JNI_OnUnload@@Base+0x6df2c>
   a2fc4:	2a1503f7 	orr	w23, wzr, w21
   a2fc8:	d0fffba1 	adrp	x1, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a2fcc:	9129ac21 	add	x1, x1, #0xa6b
   a2fd0:	aa1303e0 	orr	x0, xzr, x19
   a2fd4:	f9000ff6 	str	x22, [sp, #24]
   a2fd8:	97fe79d4 	bl	41728 <JNI_OnUnload@@Base+0xc748>
   a2fdc:	f9400268 	ldr	x8, [x19]
   a2fe0:	aa0003e2 	orr	x2, xzr, x0
   a2fe4:	aa1303e0 	orr	x0, xzr, x19
   a2fe8:	2a1703e1 	orr	w1, wzr, w23
   a2fec:	aa1f03e3 	orr	x3, xzr, xzr
   a2ff0:	f942b108 	ldr	x8, [x8, #1376]
   a2ff4:	d63f0100 	blr	x8
   a2ff8:	710006bf 	subs	wzr, w21, #0x1
   a2ffc:	b90027f5 	str	w21, [sp, #36]
   a3000:	f9000be0 	str	x0, [sp, #16]
   a3004:	5400084b 	b.lt	a310c <JNI_OnUnload@@Base+0x6e12c>  // b.tstop
   a3008:	2a1f03f9 	orr	w25, wzr, wzr
   a300c:	2a1f03f8 	orr	w24, wzr, wzr
   a3010:	14000008 	b	a3030 <JNI_OnUnload@@Base+0x6e050>
   a3014:	aa1603fc 	orr	x28, xzr, x22
   a3018:	b94027e8 	ldr	w8, [sp, #36]
   a301c:	11000718 	add	w24, w24, #0x1
   a3020:	aa1403fb 	orr	x27, xzr, x20
   a3024:	aa1503f4 	orr	x20, xzr, x21
   a3028:	6b18011f 	subs	wzr, w8, w24
   a302c:	54000700 	b.eq	a310c <JNI_OnUnload@@Base+0x6e12c>  // b.none
   a3030:	f9400268 	ldr	x8, [x19]
   a3034:	aa1303e0 	orr	x0, xzr, x19
   a3038:	aa1403e1 	orr	x1, xzr, x20
   a303c:	2a1803e2 	orr	w2, wzr, w24
   a3040:	aa1403f5 	orr	x21, xzr, x20
   a3044:	f942b508 	ldr	x8, [x8, #1384]
   a3048:	d63f0100 	blr	x8
   a304c:	aa0003fa 	orr	x26, xzr, x0
   a3050:	d0fffb88 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a3054:	913edd08 	add	x8, x8, #0xfb7
   a3058:	f9002fe0 	str	x0, [sp, #88]
   a305c:	910123e0 	add	x0, sp, #0x48
   a3060:	aa1b03f4 	orr	x20, xzr, x27
   a3064:	a90623fb 	stp	x27, x8, [sp, #96]
   a3068:	a904cffc 	stp	x28, x19, [sp, #72]
   a306c:	97fe8bd2 	bl	45fb4 <JNI_OnUnload@@Base+0x10fd4>
   a3070:	b0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a3074:	912aa108 	add	x8, x8, #0xaa8
   a3078:	a902cfe8 	stp	x8, x19, [sp, #40]
   a307c:	90fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3080:	91247908 	add	x8, x8, #0x91e
   a3084:	a903a3e0 	stp	x0, x8, [sp, #56]
   a3088:	9100a3e0 	add	x0, sp, #0x28
   a308c:	97fe8bce 	bl	45fc4 <JNI_OnUnload@@Base+0x10fe4>
   a3090:	b4fffc40 	cbz	x0, a3018 <JNI_OnUnload@@Base+0x6e038>
   a3094:	f9400268 	ldr	x8, [x19]
   a3098:	aa0003fb 	orr	x27, xzr, x0
   a309c:	aa1303e0 	orr	x0, xzr, x19
   a30a0:	aa1b03e1 	orr	x1, xzr, x27
   a30a4:	aa1f03e2 	orr	x2, xzr, xzr
   a30a8:	aa1c03f6 	orr	x22, xzr, x28
   a30ac:	f942a508 	ldr	x8, [x8, #1352]
   a30b0:	d63f0100 	blr	x8
   a30b4:	f9400fe1 	ldr	x1, [sp, #24]
   a30b8:	52802002 	movz	w2, #0x100
   a30bc:	aa0003fc 	orr	x28, xzr, x0
   a30c0:	94001448 	bl	a81e0 <strncmp@plt>
   a30c4:	f9400268 	ldr	x8, [x19]
   a30c8:	2a0003f7 	orr	w23, wzr, w0
   a30cc:	aa1303e0 	orr	x0, xzr, x19
   a30d0:	aa1b03e1 	orr	x1, xzr, x27
   a30d4:	aa1c03e2 	orr	x2, xzr, x28
   a30d8:	f942a908 	ldr	x8, [x8, #1360]
   a30dc:	d63f0100 	blr	x8
   a30e0:	34fff9b7 	cbz	w23, a3014 <JNI_OnUnload@@Base+0x6e034>
   a30e4:	f9400268 	ldr	x8, [x19]
   a30e8:	f9400be1 	ldr	x1, [sp, #16]
   a30ec:	aa1303e0 	orr	x0, xzr, x19
   a30f0:	2a1903e2 	orr	w2, wzr, w25
   a30f4:	aa1a03e3 	orr	x3, xzr, x26
   a30f8:	11000737 	add	w23, w25, #0x1
   a30fc:	f942b908 	ldr	x8, [x8, #1392]
   a3100:	d63f0100 	blr	x8
   a3104:	2a1703f9 	orr	w25, wzr, w23
   a3108:	17ffffc3 	b	a3014 <JNI_OnUnload@@Base+0x6e034>
   a310c:	f9400be1 	ldr	x1, [sp, #16]
   a3110:	d10183a0 	sub	x0, x29, #0x60
   a3114:	97fe8ba3 	bl	45fa0 <JNI_OnUnload@@Base+0x10fc0>
   a3118:	f94007e8 	ldr	x8, [sp, #8]
   a311c:	f9401508 	ldr	x8, [x8, #40]
   a3120:	f85f03a9 	ldur	x9, [x29, #-16]
   a3124:	eb09011f 	subs	xzr, x8, x9
   a3128:	54000121 	b.ne	a314c <JNI_OnUnload@@Base+0x6e16c>  // b.any
   a312c:	a9524ff4 	ldp	x20, x19, [sp, #288]
   a3130:	a95157f6 	ldp	x22, x21, [sp, #272]
   a3134:	a9505ff8 	ldp	x24, x23, [sp, #256]
   a3138:	a94f67fa 	ldp	x26, x25, [sp, #240]
   a313c:	a94e6ffc 	ldp	x28, x27, [sp, #224]
   a3140:	a94d7bfd 	ldp	x29, x30, [sp, #208]
   a3144:	9104c3ff 	add	sp, sp, #0x130
   a3148:	d65f03c0 	ret
   a314c:	94001421 	bl	a81d0 <__stack_chk_fail@plt>
   a3150:	900000a8 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a3154:	910f6108 	add	x8, x8, #0x3d8
   a3158:	f940000b 	ldr	x11, [x0]
   a315c:	39400109 	ldrb	w9, [x8]
   a3160:	f940090a 	ldr	x10, [x8, #16]
   a3164:	f9429d62 	ldr	x2, [x11, #1336]
   a3168:	7200013f 	ands	wzr, w9, #0x1
   a316c:	9a881541 	csinc	x1, x10, x8, ne	// ne = any
   a3170:	d61f0040 	br	x2
   a3174:	d503201f 	hint	#0x0
   a3178:	100a1248 	adr	x8, b73c0 <DPT_UNKNOWN_DATA@@Base+0xf37>
   a317c:	f940000b 	ldr	x11, [x0]
   a3180:	39400109 	ldrb	w9, [x8]
   a3184:	f940090a 	ldr	x10, [x8, #16]
   a3188:	f9429d62 	ldr	x2, [x11, #1336]
   a318c:	7200013f 	ands	wzr, w9, #0x1
   a3190:	9a881541 	csinc	x1, x10, x8, ne	// ne = any
   a3194:	d61f0040 	br	x2
   a3198:	900000a8 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a319c:	528000e9 	movz	w9, #0x7
   a31a0:	b9447108 	ldr	w8, [x8, #1136]
   a31a4:	6a28013f 	bics	wzr, w9, w8
   a31a8:	54000041 	b.ne	a31b0 <JNI_OnUnload@@Base+0x6e1d0>  // b.any
   a31ac:	d65f03c0 	ret
   a31b0:	1400116e 	b	a7768 <JNI_OnLoad@@Base+0x2f30>
   a31b4:	a9bc7bfd 	stp	x29, x30, [sp, #-64]!
   a31b8:	f9000bf7 	str	x23, [sp, #16]
   a31bc:	a90257f6 	stp	x22, x21, [sp, #32]
   a31c0:	a9034ff4 	stp	x20, x19, [sp, #48]
   a31c4:	910003fd 	add	x29, sp, #0x0
   a31c8:	900000b6 	adrp	x22, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a31cc:	f94242d5 	ldr	x21, [x22, #1152]
   a31d0:	b5000855 	cbnz	x21, a32d8 <JNI_OnUnload@@Base+0x6e2f8>
   a31d4:	f9400008 	ldr	x8, [x0]
   a31d8:	aa1f03e2 	orr	x2, xzr, xzr
   a31dc:	aa0003f3 	orr	x19, xzr, x0
   a31e0:	f942a508 	ldr	x8, [x8, #1352]
   a31e4:	d63f0100 	blr	x8
   a31e8:	52801001 	movz	w1, #0x80
   a31ec:	aa0003f5 	orr	x21, xzr, x0
   a31f0:	9400141c 	bl	a8260 <strnlen@plt>
   a31f4:	91000400 	add	x0, x0, #0x1
   a31f8:	52800021 	movz	w1, #0x1
   a31fc:	9400141d 	bl	a8270 <calloc@plt>
   a3200:	aa0003f4 	orr	x20, xzr, x0
   a3204:	aa1503e0 	orr	x0, xzr, x21
   a3208:	aa1403e1 	orr	x1, xzr, x20
   a320c:	97fe7f97 	bl	43068 <JNI_OnUnload@@Base+0xe088>
   a3210:	900000b7 	adrp	x23, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a3214:	f9423ef5 	ldr	x21, [x23, #1144]
   a3218:	b50001b5 	cbnz	x21, a324c <JNI_OnUnload@@Base+0x6e26c>
   a321c:	f9400268 	ldr	x8, [x19]
   a3220:	aa1303e0 	orr	x0, xzr, x19
   a3224:	aa1403e1 	orr	x1, xzr, x20
   a3228:	f9401908 	ldr	x8, [x8, #48]
   a322c:	d63f0100 	blr	x8
   a3230:	f9400268 	ldr	x8, [x19]
   a3234:	aa0003e1 	orr	x1, xzr, x0
   a3238:	aa1303e0 	orr	x0, xzr, x19
   a323c:	f9405508 	ldr	x8, [x8, #168]
   a3240:	d63f0100 	blr	x8
   a3244:	aa0003f5 	orr	x21, xzr, x0
   a3248:	f9023ee0 	str	x0, [x23, #1144]
   a324c:	f9400268 	ldr	x8, [x19]
   a3250:	90fffba2 	adrp	x2, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3254:	91245c42 	add	x2, x2, #0x917
   a3258:	d0fffb83 	adrp	x3, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a325c:	9126bc63 	add	x3, x3, #0x9af
   a3260:	aa1303e0 	orr	x0, xzr, x19
   a3264:	f9408508 	ldr	x8, [x8, #264]
   a3268:	aa1503e1 	orr	x1, xzr, x21
   a326c:	d63f0100 	blr	x8
   a3270:	aa0003e2 	orr	x2, xzr, x0
   a3274:	aa1303e0 	orr	x0, xzr, x19
   a3278:	aa1503e1 	orr	x1, xzr, x21
   a327c:	97fe435d 	bl	33ff0 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b106>
   a3280:	f9400268 	ldr	x8, [x19]
   a3284:	aa0003f5 	orr	x21, xzr, x0
   a3288:	aa1303e0 	orr	x0, xzr, x19
   a328c:	f9439108 	ldr	x8, [x8, #1824]
   a3290:	d63f0100 	blr	x8
   a3294:	f9400268 	ldr	x8, [x19]
   a3298:	b4000195 	cbz	x21, a32c8 <JNI_OnUnload@@Base+0x6e2e8>
   a329c:	72001c1f 	ands	wzr, w0, #0xff
   a32a0:	54000141 	b.ne	a32c8 <JNI_OnUnload@@Base+0x6e2e8>  // b.any
   a32a4:	f9405508 	ldr	x8, [x8, #168]
   a32a8:	aa1303e0 	orr	x0, xzr, x19
   a32ac:	aa1503e1 	orr	x1, xzr, x21
   a32b0:	d63f0100 	blr	x8
   a32b4:	aa0003f5 	orr	x21, xzr, x0
   a32b8:	f90242c0 	str	x0, [x22, #1152]
   a32bc:	aa1403e0 	orr	x0, xzr, x20
   a32c0:	940013e0 	bl	a8240 <free@plt>
   a32c4:	14000005 	b	a32d8 <JNI_OnUnload@@Base+0x6e2f8>
   a32c8:	f9404508 	ldr	x8, [x8, #136]
   a32cc:	aa1303e0 	orr	x0, xzr, x19
   a32d0:	d63f0100 	blr	x8
   a32d4:	aa1f03f5 	orr	x21, xzr, xzr
   a32d8:	aa1503e0 	orr	x0, xzr, x21
   a32dc:	a9434ff4 	ldp	x20, x19, [sp, #48]
   a32e0:	f9400bf7 	ldr	x23, [sp, #16]
   a32e4:	a94257f6 	ldp	x22, x21, [sp, #32]
   a32e8:	a8c47bfd 	ldp	x29, x30, [sp], #64
   a32ec:	d65f03c0 	ret
   a32f0:	a9be7bfd 	stp	x29, x30, [sp, #-32]!
   a32f4:	a9014ff4 	stp	x20, x19, [sp, #16]
   a32f8:	910003fd 	add	x29, sp, #0x0
   a32fc:	aa0003f3 	orr	x19, xzr, x0
   a3300:	52800020 	movz	w0, #0x1
   a3304:	52800c81 	movz	w1, #0x64
   a3308:	97fe4361 	bl	3408c <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b1a2>
   a330c:	37000280 	tbnz	w0, #0, a335c <JNI_OnUnload@@Base+0x6e37c>
   a3310:	900000b4 	adrp	x20, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a3314:	91102294 	add	x20, x20, #0x408
   a3318:	39400288 	ldrb	w8, [x20]
   a331c:	f9400689 	ldr	x9, [x20, #8]
   a3320:	d341fd0a 	ubfm	x10, x8, #1, #63
   a3324:	7200011f 	ands	wzr, w8, #0x1
   a3328:	9a890148 	csel	x8, x10, x9, eq	// eq = none
   a332c:	b4000188 	cbz	x8, a335c <JNI_OnUnload@@Base+0x6e37c>
   a3330:	aa1303e0 	orr	x0, xzr, x19
   a3334:	97fe8b63 	bl	460c0 <JNI_OnUnload@@Base+0x110e0>
   a3338:	39400288 	ldrb	w8, [x20]
   a333c:	f9400a89 	ldr	x9, [x20, #16]
   a3340:	aa0003e1 	orr	x1, xzr, x0
   a3344:	7200011f 	ands	wzr, w8, #0x1
   a3348:	9a941522 	csinc	x2, x9, x20, ne	// ne = any
   a334c:	aa1303e0 	orr	x0, xzr, x19
   a3350:	a9414ff4 	ldp	x20, x19, [sp, #16]
   a3354:	a8c27bfd 	ldp	x29, x30, [sp], #32
   a3358:	1400111a 	b	a77c0 <JNI_OnLoad@@Base+0x2f88>
   a335c:	a9414ff4 	ldp	x20, x19, [sp, #16]
   a3360:	a8c27bfd 	ldp	x29, x30, [sp], #32
   a3364:	d65f03c0 	ret
   a3368:	d10143ff 	sub	sp, sp, #0x50
   a336c:	a9037bfd 	stp	x29, x30, [sp, #48]
   a3370:	a9044ff4 	stp	x20, x19, [sp, #64]
   a3374:	9100c3fd 	add	x29, sp, #0x30
   a3378:	d53bd054 	mrs	x20, tpidr_el0
   a337c:	aa0203e1 	orr	x1, xzr, x2
   a3380:	aa0003f3 	orr	x19, xzr, x0
   a3384:	f9401688 	ldr	x8, [x20, #40]
   a3388:	f81f83a8 	stur	x8, [x29, #-8]
   a338c:	97ffff8a 	bl	a31b4 <JNI_OnUnload@@Base+0x6e1d4>
   a3390:	b0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a3394:	912ea108 	add	x8, x8, #0xba8
   a3398:	f9000fe0 	str	x0, [sp, #24]
   a339c:	910023e0 	add	x0, sp, #0x8
   a33a0:	a900cfe8 	stp	x8, x19, [sp, #8]
   a33a4:	97fe8b79 	bl	46188 <JNI_OnUnload@@Base+0x111a8>
   a33a8:	f9401688 	ldr	x8, [x20, #40]
   a33ac:	f85f83a9 	ldur	x9, [x29, #-8]
   a33b0:	eb09011f 	subs	xzr, x8, x9
   a33b4:	540000a1 	b.ne	a33c8 <JNI_OnUnload@@Base+0x6e3e8>  // b.any
   a33b8:	a9444ff4 	ldp	x20, x19, [sp, #64]
   a33bc:	a9437bfd 	ldp	x29, x30, [sp, #48]
   a33c0:	910143ff 	add	sp, sp, #0x50
   a33c4:	d65f03c0 	ret
   a33c8:	94001382 	bl	a81d0 <__stack_chk_fail@plt>
   a33cc:	d10203ff 	sub	sp, sp, #0x80
   a33d0:	a9057bfd 	stp	x29, x30, [sp, #80]
   a33d4:	a90657f6 	stp	x22, x21, [sp, #96]
   a33d8:	a9074ff4 	stp	x20, x19, [sp, #112]
   a33dc:	910143fd 	add	x29, sp, #0x50
   a33e0:	d53bd055 	mrs	x21, tpidr_el0
   a33e4:	aa0003f4 	orr	x20, xzr, x0
   a33e8:	f94016a8 	ldr	x8, [x21, #40]
   a33ec:	f81f83a8 	stur	x8, [x29, #-8]
   a33f0:	94000033 	bl	a34bc <JNI_OnUnload@@Base+0x6e4dc>
   a33f4:	aa0003f3 	orr	x19, xzr, x0
   a33f8:	b40004c0 	cbz	x0, a3490 <JNI_OnUnload@@Base+0x6e4b0>
   a33fc:	900000b6 	adrp	x22, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a3400:	f94242c1 	ldr	x1, [x22, #1152]
   a3404:	b40000a1 	cbz	x1, a3418 <JNI_OnUnload@@Base+0x6e438>
   a3408:	f9400288 	ldr	x8, [x20]
   a340c:	aa1403e0 	orr	x0, xzr, x20
   a3410:	f9405908 	ldr	x8, [x8, #176]
   a3414:	d63f0100 	blr	x8
   a3418:	f9400288 	ldr	x8, [x20]
   a341c:	aa1403e0 	orr	x0, xzr, x20
   a3420:	aa1303e1 	orr	x1, xzr, x19
   a3424:	f9405508 	ldr	x8, [x8, #168]
   a3428:	d63f0100 	blr	x8
   a342c:	d0fffb88 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a3430:	9126cd08 	add	x8, x8, #0x9b3
   a3434:	90fffba9 	adrp	x9, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3438:	913b1129 	add	x9, x9, #0xec4
   a343c:	f90242c0 	str	x0, [x22, #1152]
   a3440:	aa1403e0 	orr	x0, xzr, x20
   a3444:	a901a7e8 	stp	x8, x9, [sp, #24]
   a3448:	90fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a344c:	91030908 	add	x8, x8, #0xc2
   a3450:	d0fffb89 	adrp	x9, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a3454:	9117b529 	add	x9, x9, #0x5ed
   a3458:	a902a7e8 	stp	x8, x9, [sp, #40]
   a345c:	90fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3460:	910a8508 	add	x8, x8, #0x2a1
   a3464:	90fffba9 	adrp	x9, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3468:	9124ad29 	add	x9, x9, #0x92b
   a346c:	a903a7e8 	stp	x8, x9, [sp, #56]
   a3470:	b0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a3474:	912f6108 	add	x8, x8, #0xbd8
   a3478:	a90053e8 	stp	x8, x20, [sp]
   a347c:	97fe8aeb 	bl	46028 <JNI_OnUnload@@Base+0x11048>
   a3480:	f9000be0 	str	x0, [sp, #16]
   a3484:	910003e0 	add	x0, sp, #0x0
   a3488:	aa1303e1 	orr	x1, xzr, x19
   a348c:	97fe8b08 	bl	460ac <JNI_OnUnload@@Base+0x110cc>
   a3490:	f94016a8 	ldr	x8, [x21, #40]
   a3494:	f85f83a9 	ldur	x9, [x29, #-8]
   a3498:	eb09011f 	subs	xzr, x8, x9
   a349c:	540000e1 	b.ne	a34b8 <JNI_OnUnload@@Base+0x6e4d8>  // b.any
   a34a0:	aa1303e0 	orr	x0, xzr, x19
   a34a4:	a9474ff4 	ldp	x20, x19, [sp, #112]
   a34a8:	a94657f6 	ldp	x22, x21, [sp, #96]
   a34ac:	a9457bfd 	ldp	x29, x30, [sp, #80]
   a34b0:	910203ff 	add	sp, sp, #0x80
   a34b4:	d65f03c0 	ret
   a34b8:	94001346 	bl	a81d0 <__stack_chk_fail@plt>
   a34bc:	a9ba7bfd 	stp	x29, x30, [sp, #-96]!
   a34c0:	a9016ffc 	stp	x28, x27, [sp, #16]
   a34c4:	a90267fa 	stp	x26, x25, [sp, #32]
   a34c8:	a9035ff8 	stp	x24, x23, [sp, #48]
   a34cc:	a90457f6 	stp	x22, x21, [sp, #64]
   a34d0:	a9054ff4 	stp	x20, x19, [sp, #80]
   a34d4:	910003fd 	add	x29, sp, #0x0
   a34d8:	d108c3ff 	sub	sp, sp, #0x230
   a34dc:	d53bd048 	mrs	x8, tpidr_el0
   a34e0:	d0fffb89 	adrp	x9, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a34e4:	9126cd29 	add	x9, x9, #0x9b3
   a34e8:	f90003e8 	str	x8, [sp]
   a34ec:	90fffbb5 	adrp	x21, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a34f0:	9124aeb5 	add	x21, x21, #0x92b
   a34f4:	f9401508 	ldr	x8, [x8, #40]
   a34f8:	aa0203f4 	orr	x20, xzr, x2
   a34fc:	aa0003f3 	orr	x19, xzr, x0
   a3500:	d10283b7 	sub	x23, x29, #0xa0
   a3504:	f81e83a8 	stur	x8, [x29, #-24]
   a3508:	90fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a350c:	913b1108 	add	x8, x8, #0xec4
   a3510:	a93323a9 	stp	x9, x8, [x29, #-208]
   a3514:	90fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3518:	91030908 	add	x8, x8, #0xc2
   a351c:	d0fffb89 	adrp	x9, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a3520:	9117b529 	add	x9, x9, #0x5ed
   a3524:	a93427a8 	stp	x8, x9, [x29, #-192]
   a3528:	90fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a352c:	910a8508 	add	x8, x8, #0x2a1
   a3530:	a93557a8 	stp	x8, x21, [x29, #-176]
   a3534:	b0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a3538:	912f6108 	add	x8, x8, #0xbd8
   a353c:	a93183a8 	stp	x8, x0, [x29, #-232]
   a3540:	97fe8aba 	bl	46028 <JNI_OnUnload@@Base+0x11048>
   a3544:	f81283a0 	stur	x0, [x29, #-216]
   a3548:	d103a3a0 	sub	x0, x29, #0xe8
   a354c:	97fe8ad0 	bl	4608c <JNI_OnUnload@@Base+0x110ac>
   a3550:	f0fffb96 	adrp	x22, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a3554:	91227ed6 	add	x22, x22, #0x89f
   a3558:	d0fffb88 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a355c:	91123108 	add	x8, x8, #0x48c
   a3560:	a91323f6 	stp	x22, x8, [sp, #304]
   a3564:	b0fffba8 	adrp	x8, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a3568:	911fc908 	add	x8, x8, #0x7f2
   a356c:	f900a3e8 	str	x8, [sp, #320]
   a3570:	b0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a3574:	91302108 	add	x8, x8, #0xc08
   a3578:	a9114fe8 	stp	x8, x19, [sp, #272]
   a357c:	f0fffb88 	adrp	x8, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a3580:	9106f908 	add	x8, x8, #0x1be
   a3584:	a91223e0 	stp	x0, x8, [sp, #288]
   a3588:	910443e0 	add	x0, sp, #0x110
   a358c:	97fe8aea 	bl	46134 <JNI_OnUnload@@Base+0x11154>
   a3590:	f0fffb88 	adrp	x8, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a3594:	911c7508 	add	x8, x8, #0x71d
   a3598:	aa1f03e1 	orr	x1, xzr, xzr
   a359c:	a90fa3f5 	stp	x21, x8, [sp, #248]
   a35a0:	b0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a35a4:	9130e108 	add	x8, x8, #0xc38
   a35a8:	a90dcfe8 	stp	x8, x19, [sp, #216]
   a35ac:	d0fffb88 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a35b0:	913f1908 	add	x8, x8, #0xfc6
   a35b4:	a90ea3e0 	stp	x0, x8, [sp, #232]
   a35b8:	910363e0 	add	x0, sp, #0xd8
   a35bc:	f90087f6 	str	x22, [sp, #264]
   a35c0:	97fe8af9 	bl	461a4 <JNI_OnUnload@@Base+0x111c4>
   a35c4:	d103a3a0 	sub	x0, x29, #0xe8
   a35c8:	97fe8ab5 	bl	4609c <JNI_OnUnload@@Base+0x110bc>
   a35cc:	b0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a35d0:	912ba108 	add	x8, x8, #0xae8
   a35d4:	2a1f03e1 	orr	w1, wzr, wzr
   a35d8:	a90bcfe8 	stp	x8, x19, [sp, #184]
   a35dc:	d0fffb88 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a35e0:	913e8d08 	add	x8, x8, #0xfa3
   a35e4:	a90ca3e0 	stp	x0, x8, [sp, #200]
   a35e8:	9102e3e0 	add	x0, sp, #0xb8
   a35ec:	97fe8a7d 	bl	45fe0 <JNI_OnUnload@@Base+0x11000>
   a35f0:	910363e0 	add	x0, sp, #0xd8
   a35f4:	97fe8af1 	bl	461b8 <JNI_OnUnload@@Base+0x111d8>
   a35f8:	f9400268 	ldr	x8, [x19]
   a35fc:	90fffbbb 	adrp	x27, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3600:	913bb77b 	add	x27, x27, #0xeed
   a3604:	d0fffb98 	adrp	x24, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a3608:	91124718 	add	x24, x24, #0x491
   a360c:	f0fffb99 	adrp	x25, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a3610:	91231339 	add	x25, x25, #0x8c4
   a3614:	f942a508 	ldr	x8, [x8, #1352]
   a3618:	90fffbba 	adrp	x26, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a361c:	913bdf5a 	add	x26, x26, #0xef7
   a3620:	f0fffb9c 	adrp	x28, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a3624:	9112379c 	add	x28, x28, #0x48d
   a3628:	a9076fe0 	stp	x0, x27, [sp, #112]
   a362c:	b0000056 	adrp	x22, ac000 <pthread_key_delete@plt+0x3580>
   a3630:	9131e2d6 	add	x22, x22, #0xc78
   a3634:	aa1303e0 	orr	x0, xzr, x19
   a3638:	aa1403e1 	orr	x1, xzr, x20
   a363c:	aa1f03e2 	orr	x2, xzr, xzr
   a3640:	a90867f8 	stp	x24, x25, [sp, #128]
   a3644:	a9096bf8 	stp	x24, x26, [sp, #144]
   a3648:	a90a73f8 	stp	x24, x28, [sp, #160]
   a364c:	f9005bf8 	str	x24, [sp, #176]
   a3650:	a9064ff6 	stp	x22, x19, [sp, #96]
   a3654:	d63f0100 	blr	x8
   a3658:	6f00e400 	movi	v0.2d, #0x0
   a365c:	d10283a1 	sub	x1, x29, #0xa0
   a3660:	aa0003f5 	orr	x21, xzr, x0
   a3664:	ad0002e0 	stp	q0, q0, [x23]
   a3668:	ad0102e0 	stp	q0, q0, [x23, #32]
   a366c:	ad0202e0 	stp	q0, q0, [x23, #64]
   a3670:	ad0302e0 	stp	q0, q0, [x23, #96]
   a3674:	97fe7e7d 	bl	43068 <JNI_OnUnload@@Base+0xe088>
   a3678:	f9400268 	ldr	x8, [x19]
   a367c:	aa1303e0 	orr	x0, xzr, x19
   a3680:	aa1403e1 	orr	x1, xzr, x20
   a3684:	aa1503e2 	orr	x2, xzr, x21
   a3688:	f942a908 	ldr	x8, [x8, #1360]
   a368c:	d63f0100 	blr	x8
   a3690:	f9400268 	ldr	x8, [x19]
   a3694:	d10283a1 	sub	x1, x29, #0xa0
   a3698:	aa1303e0 	orr	x0, xzr, x19
   a369c:	f9429d08 	ldr	x8, [x8, #1336]
   a36a0:	d63f0100 	blr	x8
   a36a4:	f9400268 	ldr	x8, [x19]
   a36a8:	aa0003e1 	orr	x1, xzr, x0
   a36ac:	aa1303e0 	orr	x0, xzr, x19
   a36b0:	f9405508 	ldr	x8, [x8, #168]
   a36b4:	d63f0100 	blr	x8
   a36b8:	aa0003f4 	orr	x20, xzr, x0
   a36bc:	910443e0 	add	x0, sp, #0x110
   a36c0:	97fe8a99 	bl	46124 <JNI_OnUnload@@Base+0x11144>
   a36c4:	a901efe0 	stp	x0, x27, [sp, #24]
   a36c8:	910183e0 	add	x0, sp, #0x60
   a36cc:	aa1403e1 	orr	x1, xzr, x20
   a36d0:	a902e7f8 	stp	x24, x25, [sp, #40]
   a36d4:	a903ebf8 	stp	x24, x26, [sp, #56]
   a36d8:	a904f3f8 	stp	x24, x28, [sp, #72]
   a36dc:	f9002ff8 	str	x24, [sp, #88]
   a36e0:	a900cff6 	stp	x22, x19, [sp, #8]
   a36e4:	97fe8aa4 	bl	46174 <JNI_OnUnload@@Base+0x11194>
   a36e8:	910023e0 	add	x0, sp, #0x8
   a36ec:	aa1403e1 	orr	x1, xzr, x20
   a36f0:	97fe8aa1 	bl	46174 <JNI_OnUnload@@Base+0x11194>
   a36f4:	910363e0 	add	x0, sp, #0xd8
   a36f8:	2a1f03e1 	orr	w1, wzr, wzr
   a36fc:	aa1f03e2 	orr	x2, xzr, xzr
   a3700:	97fe8ab2 	bl	461c8 <JNI_OnUnload@@Base+0x111e8>
   a3704:	f94003e8 	ldr	x8, [sp]
   a3708:	f9401508 	ldr	x8, [x8, #40]
   a370c:	f85e83a9 	ldur	x9, [x29, #-24]
   a3710:	eb09011f 	subs	xzr, x8, x9
   a3714:	54000121 	b.ne	a3738 <JNI_OnUnload@@Base+0x6e758>  // b.any
   a3718:	9108c3ff 	add	sp, sp, #0x230
   a371c:	a9454ff4 	ldp	x20, x19, [sp, #80]
   a3720:	a94457f6 	ldp	x22, x21, [sp, #64]
   a3724:	a9435ff8 	ldp	x24, x23, [sp, #48]
   a3728:	a94267fa 	ldp	x26, x25, [sp, #32]
   a372c:	a9416ffc 	ldp	x28, x27, [sp, #16]
   a3730:	a8c67bfd 	ldp	x29, x30, [sp], #96
   a3734:	d65f03c0 	ret
   a3738:	940012a6 	bl	a81d0 <__stack_chk_fail@plt>
   a373c:	d101c3ff 	sub	sp, sp, #0x70
   a3740:	a9057bfd 	stp	x29, x30, [sp, #80]
   a3744:	a9064ff4 	stp	x20, x19, [sp, #96]
   a3748:	910143fd 	add	x29, sp, #0x50
   a374c:	d53bd054 	mrs	x20, tpidr_el0
   a3750:	d0fffb89 	adrp	x9, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a3754:	9126cd29 	add	x9, x9, #0x9b3
   a3758:	f9401688 	ldr	x8, [x20, #40]
   a375c:	aa0203f3 	orr	x19, xzr, x2
   a3760:	f81f83a8 	stur	x8, [x29, #-8]
   a3764:	90fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3768:	913b1108 	add	x8, x8, #0xec4
   a376c:	a901a3e9 	stp	x9, x8, [sp, #24]
   a3770:	90fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3774:	91030908 	add	x8, x8, #0xc2
   a3778:	d0fffb89 	adrp	x9, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a377c:	9117b529 	add	x9, x9, #0x5ed
   a3780:	a902a7e8 	stp	x8, x9, [sp, #40]
   a3784:	90fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3788:	910a8508 	add	x8, x8, #0x2a1
   a378c:	90fffba9 	adrp	x9, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3790:	9124ad29 	add	x9, x9, #0x92b
   a3794:	a903a7e8 	stp	x8, x9, [sp, #56]
   a3798:	b0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a379c:	912f6108 	add	x8, x8, #0xbd8
   a37a0:	a90003e8 	stp	x8, x0, [sp]
   a37a4:	97fe8a21 	bl	46028 <JNI_OnUnload@@Base+0x11048>
   a37a8:	f9000be0 	str	x0, [sp, #16]
   a37ac:	910003e0 	add	x0, sp, #0x0
   a37b0:	aa1303e1 	orr	x1, xzr, x19
   a37b4:	97fe8a3e 	bl	460ac <JNI_OnUnload@@Base+0x110cc>
   a37b8:	f9401688 	ldr	x8, [x20, #40]
   a37bc:	f85f83a9 	ldur	x9, [x29, #-8]
   a37c0:	eb09011f 	subs	xzr, x8, x9
   a37c4:	540000a1 	b.ne	a37d8 <JNI_OnUnload@@Base+0x6e7f8>  // b.any
   a37c8:	a9464ff4 	ldp	x20, x19, [sp, #96]
   a37cc:	a9457bfd 	ldp	x29, x30, [sp, #80]
   a37d0:	9101c3ff 	add	sp, sp, #0x70
   a37d4:	d65f03c0 	ret
   a37d8:	9400127e 	bl	a81d0 <__stack_chk_fail@plt>
   a37dc:	d10203ff 	sub	sp, sp, #0x80
   a37e0:	a9037bfd 	stp	x29, x30, [sp, #48]
   a37e4:	a90467fa 	stp	x26, x25, [sp, #64]
   a37e8:	a9055ff8 	stp	x24, x23, [sp, #80]
   a37ec:	a90657f6 	stp	x22, x21, [sp, #96]
   a37f0:	a9074ff4 	stp	x20, x19, [sp, #112]
   a37f4:	9100c3fd 	add	x29, sp, #0x30
   a37f8:	d53bd058 	mrs	x24, tpidr_el0
   a37fc:	aa0003f4 	orr	x20, xzr, x0
   a3800:	f9401708 	ldr	x8, [x24, #40]
   a3804:	f81f83a8 	stur	x8, [x29, #-8]
   a3808:	94001266 	bl	a81a0 <clock@plt>
   a380c:	aa0003f3 	orr	x19, xzr, x0
   a3810:	910023e1 	add	x1, sp, #0x8
   a3814:	910003e2 	add	x2, sp, #0x0
   a3818:	aa1403e0 	orr	x0, xzr, x20
   a381c:	a9007fff 	stp	xzr, xzr, [sp]
   a3820:	97fe7fda 	bl	43788 <JNI_OnUnload@@Base+0xe7a8>
   a3824:	900000ba 	adrp	x26, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a3828:	d503201f 	hint	#0x0
   a382c:	1009dab9 	adr	x25, b7380 <DPT_UNKNOWN_DATA@@Base+0xef7>
   a3830:	394e4348 	ldrb	w8, [x26, #912]
   a3834:	35000588 	cbnz	w8, a38e4 <JNI_OnUnload@@Base+0x6e904>
   a3838:	d503201f 	hint	#0x0
   a383c:	30bab0a8 	adr	x8, 18e51 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x99>
   a3840:	d503201f 	hint	#0x0
   a3844:	10096ba0 	adr	x0, b65b8 <DPT_UNKNOWN_DATA@@Base+0x12f>
   a3848:	3dc00100 	ldr	q0, [x8]
   a384c:	5289bdc8 	movz	w8, #0x4dee
   a3850:	a94057f6 	ldp	x22, x21, [sp]
   a3854:	790043e8 	strh	w8, [sp, #32]
   a3858:	3d8007e0 	str	q0, [sp, #16]
   a385c:	97ffe960 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a3860:	39400008 	ldrb	w8, [x0]
   a3864:	36000788 	tbz	w8, #0, a3954 <JNI_OnUnload@@Base+0x6e974>
   a3868:	d503201f 	hint	#0x0
   a386c:	10096960 	adr	x0, b6598 <DPT_UNKNOWN_DATA@@Base+0x10f>
   a3870:	97ffe95b 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a3874:	aa0003f7 	orr	x23, xzr, x0
   a3878:	97fe4ab7 	bl	36354 <JNI_OnUnload@@Base+0x1374>
   a387c:	910043e8 	add	x8, sp, #0x10
   a3880:	aa1503e0 	orr	x0, xzr, x21
   a3884:	aa1603e1 	orr	x1, xzr, x22
   a3888:	aa1703e2 	orr	x2, xzr, x23
   a388c:	940009b0 	bl	a5f4c <JNI_OnLoad@@Base+0x1714>
   a3890:	394083e9 	ldrb	w9, [sp, #32]
   a3894:	340001c9 	cbz	w9, a38cc <JNI_OnUnload@@Base+0x6e8ec>
   a3898:	394e4348 	ldrb	w8, [x26, #912]
   a389c:	6b09011f 	subs	wzr, w8, w9
   a38a0:	54000081 	b.ne	a38b0 <JNI_OnUnload@@Base+0x6e8d0>  // b.any
   a38a4:	a94127e8 	ldp	x8, x9, [sp, #16]
   a38a8:	a9002728 	stp	x8, x9, [x25]
   a38ac:	14000008 	b	a38cc <JNI_OnUnload@@Base+0x6e8ec>
   a38b0:	34000068 	cbz	w8, a38bc <JNI_OnUnload@@Base+0x6e8dc>
   a38b4:	390e435f 	strb	wzr, [x26, #912]
   a38b8:	14000005 	b	a38cc <JNI_OnUnload@@Base+0x6e8ec>
   a38bc:	3dc007e0 	ldr	q0, [sp, #16]
   a38c0:	52800028 	movz	w8, #0x1
   a38c4:	39004328 	strb	w8, [x25, #16]
   a38c8:	3d800320 	str	q0, [x25]
   a38cc:	d0fffb80 	adrp	x0, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a38d0:	911fb400 	add	x0, x0, #0x7ed
   a38d4:	aa1303e1 	orr	x1, xzr, x19
   a38d8:	97fe8052 	bl	43a20 <JNI_OnUnload@@Base+0xea40>
   a38dc:	394e4348 	ldrb	w8, [x26, #912]
   a38e0:	340005e8 	cbz	w8, a399c <JNI_OnUnload@@Base+0x6e9bc>
   a38e4:	a9400720 	ldp	x0, x1, [x25]
   a38e8:	94000033 	bl	a39b4 <JNI_OnUnload@@Base+0x6e9d4>
   a38ec:	d503201f 	hint	#0x0
   a38f0:	100a7955 	adr	x21, b8818 <DPT_UNKNOWN_DATA@@Base+0x238f>
   a38f4:	aa1503e0 	orr	x0, xzr, x21
   a38f8:	94001262 	bl	a8280 <pthread_mutex_lock@plt>
   a38fc:	a94007e2 	ldp	x2, x1, [sp]
   a3900:	aa1403e0 	orr	x0, xzr, x20
   a3904:	940008e0 	bl	a5c84 <JNI_OnLoad@@Base+0x144c>
   a3908:	aa1503e0 	orr	x0, xzr, x21
   a390c:	94001261 	bl	a8290 <pthread_mutex_unlock@plt>
   a3910:	a94003e1 	ldp	x1, x0, [sp]
   a3914:	97fe7fe9 	bl	438b8 <JNI_OnUnload@@Base+0xe8d8>
   a3918:	f0fffb80 	adrp	x0, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a391c:	91181c00 	add	x0, x0, #0x607
   a3920:	aa1303e1 	orr	x1, xzr, x19
   a3924:	97fe803f 	bl	43a20 <JNI_OnUnload@@Base+0xea40>
   a3928:	f9401708 	ldr	x8, [x24, #40]
   a392c:	f85f83a9 	ldur	x9, [x29, #-8]
   a3930:	eb09011f 	subs	xzr, x8, x9
   a3934:	540003e1 	b.ne	a39b0 <JNI_OnUnload@@Base+0x6e9d0>  // b.any
   a3938:	a9474ff4 	ldp	x20, x19, [sp, #112]
   a393c:	a94657f6 	ldp	x22, x21, [sp, #96]
   a3940:	a9455ff8 	ldp	x24, x23, [sp, #80]
   a3944:	a94467fa 	ldp	x26, x25, [sp, #64]
   a3948:	a9437bfd 	ldp	x29, x30, [sp, #48]
   a394c:	910203ff 	add	sp, sp, #0x80
   a3950:	d65f03c0 	ret
   a3954:	d503201f 	hint	#0x0
   a3958:	10096200 	adr	x0, b6598 <DPT_UNKNOWN_DATA@@Base+0x10f>
   a395c:	97ffe920 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a3960:	910043e1 	add	x1, sp, #0x10
   a3964:	aa0003f7 	orr	x23, xzr, x0
   a3968:	97fe46a9 	bl	3540c <JNI_OnUnload@@Base+0x42c>
   a396c:	d503201f 	hint	#0x0
   a3970:	10c8d9c0 	adr	x0, 354a8 <JNI_OnUnload@@Base+0x4c8>
   a3974:	d503201f 	hint	#0x0
   a3978:	100488c2 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a397c:	aa1703e1 	orr	x1, xzr, x23
   a3980:	97ffe87d 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a3984:	d503201f 	hint	#0x0
   a3988:	10096180 	adr	x0, b65b8 <DPT_UNKNOWN_DATA@@Base+0x12f>
   a398c:	97ffe914 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a3990:	52800028 	movz	w8, #0x1
   a3994:	39000008 	strb	w8, [x0]
   a3998:	17ffffb4 	b	a3868 <JNI_OnUnload@@Base+0x6e888>
   a399c:	f9401708 	ldr	x8, [x24, #40]
   a39a0:	f85f83a9 	ldur	x9, [x29, #-8]
   a39a4:	eb09011f 	subs	xzr, x8, x9
   a39a8:	54000041 	b.ne	a39b0 <JNI_OnUnload@@Base+0x6e9d0>  // b.any
   a39ac:	97fe46c2 	bl	354b4 <JNI_OnUnload@@Base+0x4d4>
   a39b0:	94001208 	bl	a81d0 <__stack_chk_fail@plt>
   a39b4:	d101c3ff 	sub	sp, sp, #0x70
   a39b8:	a9027bfd 	stp	x29, x30, [sp, #32]
   a39bc:	a90367fa 	stp	x26, x25, [sp, #48]
   a39c0:	a9045ff8 	stp	x24, x23, [sp, #64]
   a39c4:	a90557f6 	stp	x22, x21, [sp, #80]
   a39c8:	a9064ff4 	stp	x20, x19, [sp, #96]
   a39cc:	910083fd 	add	x29, sp, #0x20
   a39d0:	d53bd059 	mrs	x25, tpidr_el0
   a39d4:	f9401728 	ldr	x8, [x25, #40]
   a39d8:	f81f83a8 	stur	x8, [x29, #-8]
   a39dc:	b4000980 	cbz	x0, a3b0c <JNI_OnUnload@@Base+0x6eb2c>
   a39e0:	aa0103f4 	orr	x20, xzr, x1
   a39e4:	aa0003f5 	orr	x21, xzr, x0
   a39e8:	97fe88e2 	bl	45d70 <JNI_OnUnload@@Base+0x10d90>
   a39ec:	aa1503e1 	orr	x1, xzr, x21
   a39f0:	aa1403e2 	orr	x2, xzr, x20
   a39f4:	aa0003f3 	orr	x19, xzr, x0
   a39f8:	97fe88fc 	bl	45de8 <JNI_OnUnload@@Base+0x10e08>
   a39fc:	d10033a1 	sub	x1, x29, #0xc
   a3a00:	aa1303e0 	orr	x0, xzr, x19
   a3a04:	b81f43bf 	stur	wzr, [x29, #-12]
   a3a08:	97fe88fd 	bl	45dfc <JNI_OnUnload@@Base+0x10e1c>
   a3a0c:	b89f43a1 	ldursw	x1, [x29, #-12]
   a3a10:	aa0003f4 	orr	x20, xzr, x0
   a3a14:	d503201f 	hint	#0x0
   a3a18:	1009cc00 	adr	x0, b7398 <DPT_UNKNOWN_DATA@@Base+0xf0f>
   a3a1c:	97fe4a5e 	bl	36394 <JNI_OnUnload@@Base+0x13b4>
   a3a20:	b85f43a8 	ldur	w8, [x29, #-12]
   a3a24:	b90013ff 	str	wzr, [sp, #16]
   a3a28:	7100051f 	subs	wzr, w8, #0x1
   a3a2c:	5400070b 	b.lt	a3b0c <JNI_OnUnload@@Base+0x6eb2c>  // b.tstop
   a3a30:	2a1f03e8 	orr	w8, wzr, wzr
   a3a34:	d503201f 	hint	#0x0
   a3a38:	1009cb15 	adr	x21, b7398 <DPT_UNKNOWN_DATA@@Base+0xf0f>
   a3a3c:	1400000c 	b	a3a6c <JNI_OnUnload@@Base+0x6ea8c>
   a3a40:	910043e1 	add	x1, sp, #0x10
   a3a44:	910043e2 	add	x2, sp, #0x10
   a3a48:	910023e3 	add	x3, sp, #0x8
   a3a4c:	aa1503e0 	orr	x0, xzr, x21
   a3a50:	97fe4ae7 	bl	365ec <JNI_OnUnload@@Base+0x160c>
   a3a54:	b94013e8 	ldr	w8, [sp, #16]
   a3a58:	b85f43a9 	ldur	w9, [x29, #-12]
   a3a5c:	11000508 	add	w8, w8, #0x1
   a3a60:	6b09011f 	subs	wzr, w8, w9
   a3a64:	b90013e8 	str	w8, [sp, #16]
   a3a68:	5400052a 	b.ge	a3b0c <JNI_OnUnload@@Base+0x6eb2c>  // b.tcont
   a3a6c:	b868da96 	ldr	w22, [x20, w8, sxtw #2]
   a3a70:	aa1303e0 	orr	x0, xzr, x19
   a3a74:	2a1603e1 	orr	w1, wzr, w22
   a3a78:	97fe88de 	bl	45df0 <JNI_OnUnload@@Base+0x10e10>
   a3a7c:	2a0003f7 	orr	w23, wzr, w0
   a3a80:	52800300 	movz	w0, #0x18
   a3a84:	97ffe386 	bl	9c89c <JNI_OnUnload@@Base+0x678bc>
   a3a88:	aa0003f8 	orr	x24, xzr, x0
   a3a8c:	a900fc1f 	stp	xzr, xzr, [x0, #8]
   a3a90:	f900001f 	str	xzr, [x0]
   a3a94:	52a00100 	movz	w0, #0x8, lsl #16
   a3a98:	97ffe381 	bl	9c89c <JNI_OnUnload@@Base+0x678bc>
   a3a9c:	9142001a 	add	x26, x0, #0x80, lsl #12
   a3aa0:	2a1f03e1 	orr	w1, wzr, wzr
   a3aa4:	52a00102 	movz	w2, #0x8, lsl #16
   a3aa8:	f9000300 	str	x0, [x24]
   a3aac:	f9000b1a 	str	x26, [x24, #16]
   a3ab0:	940011fc 	bl	a82a0 <memset@plt>
   a3ab4:	11000ac8 	add	w8, w22, #0x2
   a3ab8:	72003ef7 	ands	w23, w23, #0xffff
   a3abc:	f900071a 	str	x26, [x24, #8]
   a3ac0:	f90007f8 	str	x24, [sp, #8]
   a3ac4:	b90007e8 	str	w8, [sp, #4]
   a3ac8:	54fffbc0 	b.eq	a3a40 <JNI_OnUnload@@Base+0x6ea60>  // b.none
   a3acc:	910013e1 	add	x1, sp, #0x4
   a3ad0:	aa1303e0 	orr	x0, xzr, x19
   a3ad4:	97fe88cf 	bl	45e10 <JNI_OnUnload@@Base+0x10e30>
   a3ad8:	aa0003f6 	orr	x22, xzr, x0
   a3adc:	97fe88eb 	bl	45e88 <JNI_OnUnload@@Base+0x10ea8>
   a3ae0:	2a0003e8 	orr	w8, wzr, w0
   a3ae4:	f94007e0 	ldr	x0, [sp, #8]
   a3ae8:	2a0803e8 	orr	w8, wzr, w8
   a3aec:	a9402809 	ldp	x9, x10, [x0]
   a3af0:	cb09014a 	sub	x10, x10, x9
   a3af4:	eb8a0d1f 	subs	xzr, x8, x10, asr #3
   a3af8:	54000202 	b.cs	a3b38 <JNI_OnUnload@@Base+0x6eb58>  // b.hs, b.nlast
   a3afc:	710006f7 	subs	w23, w23, #0x1
   a3b00:	f8287936 	str	x22, [x9, x8, lsl #3]
   a3b04:	54fffe41 	b.ne	a3acc <JNI_OnUnload@@Base+0x6eaec>  // b.any
   a3b08:	17ffffce 	b	a3a40 <JNI_OnUnload@@Base+0x6ea60>
   a3b0c:	f9401728 	ldr	x8, [x25, #40]
   a3b10:	f85f83a9 	ldur	x9, [x29, #-8]
   a3b14:	eb09011f 	subs	xzr, x8, x9
   a3b18:	540002c1 	b.ne	a3b70 <JNI_OnUnload@@Base+0x6eb90>  // b.any
   a3b1c:	a9464ff4 	ldp	x20, x19, [sp, #96]
   a3b20:	a94557f6 	ldp	x22, x21, [sp, #80]
   a3b24:	a9445ff8 	ldp	x24, x23, [sp, #64]
   a3b28:	a94367fa 	ldp	x26, x25, [sp, #48]
   a3b2c:	a9427bfd 	ldp	x29, x30, [sp, #32]
   a3b30:	9101c3ff 	add	sp, sp, #0x70
   a3b34:	d65f03c0 	ret
   a3b38:	f9401728 	ldr	x8, [x25, #40]
   a3b3c:	f85f83a9 	ldur	x9, [x29, #-8]
   a3b40:	eb09011f 	subs	xzr, x8, x9
   a3b44:	54000161 	b.ne	a3b70 <JNI_OnUnload@@Base+0x6eb90>  // b.any
   a3b48:	97fe4668 	bl	354e8 <JNI_OnUnload@@Base+0x508>
   a3b4c:	aa0003f3 	orr	x19, xzr, x0
   a3b50:	aa1803e0 	orr	x0, xzr, x24
   a3b54:	97ffe370 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a3b58:	f9401728 	ldr	x8, [x25, #40]
   a3b5c:	f85f83a9 	ldur	x9, [x29, #-8]
   a3b60:	eb09011f 	subs	xzr, x8, x9
   a3b64:	54000061 	b.ne	a3b70 <JNI_OnUnload@@Base+0x6eb90>  // b.any
   a3b68:	aa1303e0 	orr	x0, xzr, x19
   a3b6c:	97ffec08 	bl	9eb8c <JNI_OnUnload@@Base+0x69bac>
   a3b70:	94001198 	bl	a81d0 <__stack_chk_fail@plt>
   a3b74:	a9ba7bfd 	stp	x29, x30, [sp, #-96]!
   a3b78:	a9016ffc 	stp	x28, x27, [sp, #16]
   a3b7c:	a90267fa 	stp	x26, x25, [sp, #32]
   a3b80:	a9035ff8 	stp	x24, x23, [sp, #48]
   a3b84:	a90457f6 	stp	x22, x21, [sp, #64]
   a3b88:	a9054ff4 	stp	x20, x19, [sp, #80]
   a3b8c:	910003fd 	add	x29, sp, #0x0
   a3b90:	d10903ff 	sub	sp, sp, #0x240
   a3b94:	d53bd05b 	mrs	x27, tpidr_el0
   a3b98:	d103c3a1 	sub	x1, x29, #0xf0
   a3b9c:	d103e3a2 	sub	x2, x29, #0xf8
   a3ba0:	f9401768 	ldr	x8, [x27, #40]
   a3ba4:	aa0003f5 	orr	x21, xzr, x0
   a3ba8:	f81f03a8 	stur	x8, [x29, #-16]
   a3bac:	a930ffbf 	stp	xzr, xzr, [x29, #-248]
   a3bb0:	97fe7ef6 	bl	43788 <JNI_OnUnload@@Base+0xe7a8>
   a3bb4:	d503201f 	hint	#0x0
   a3bb8:	70ba9548 	adr	x8, 18e63 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x87>
   a3bbc:	d503201f 	hint	#0x0
   a3bc0:	100951c0 	adr	x0, b65f8 <DPT_UNKNOWN_DATA@@Base+0x16f>
   a3bc4:	3dc00100 	ldr	q0, [x8]
   a3bc8:	f9400908 	ldr	x8, [x8, #16]
   a3bcc:	a970cfb4 	ldp	x20, x19, [x29, #-248]
   a3bd0:	3c9903a0 	stur	q0, [x29, #-112]
   a3bd4:	f81a03a8 	stur	x8, [x29, #-96]
   a3bd8:	97ffe881 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a3bdc:	39400008 	ldrb	w8, [x0]
   a3be0:	360048a8 	tbz	w8, #0, a44f4 <JNI_OnUnload@@Base+0x6f514>
   a3be4:	d503201f 	hint	#0x0
   a3be8:	10094f80 	adr	x0, b65d8 <DPT_UNKNOWN_DATA@@Base+0x14f>
   a3bec:	97ffe87c 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a3bf0:	39406008 	ldrb	w8, [x0, #24]
   a3bf4:	aa0003e2 	orr	x2, xzr, x0
   a3bf8:	34000168 	cbz	w8, a3c24 <JNI_OnUnload@@Base+0x6ec44>
   a3bfc:	b0fffb88 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a3c00:	6d400841 	ldp	d1, d2, [x2]
   a3c04:	fd458500 	ldr	d0, [x8, #2824]
   a3c08:	fd400843 	ldr	d3, [x2, #16]
   a3c0c:	3900605f 	strb	wzr, [x2, #24]
   a3c10:	2e201c21 	eor	v1.8b, v1.8b, v0.8b
   a3c14:	2e201c42 	eor	v2.8b, v2.8b, v0.8b
   a3c18:	2e201c60 	eor	v0.8b, v3.8b, v0.8b
   a3c1c:	6d000841 	stp	d1, d2, [x2]
   a3c20:	fd000840 	str	d0, [x2, #16]
   a3c24:	d103a3a8 	sub	x8, x29, #0xe8
   a3c28:	aa1303e0 	orr	x0, xzr, x19
   a3c2c:	aa1403e1 	orr	x1, xzr, x20
   a3c30:	940008c7 	bl	a5f4c <JNI_OnLoad@@Base+0x1714>
   a3c34:	385283a8 	ldurb	w8, [x29, #-216]
   a3c38:	34004428 	cbz	w8, a44bc <JNI_OnUnload@@Base+0x6f4dc>
   a3c3c:	a971d3b3 	ldp	x19, x20, [x29, #-232]
   a3c40:	b4004394 	cbz	x20, a44b0 <JNI_OnUnload@@Base+0x6f4d0>
   a3c44:	d0fffb88 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a3c48:	9126cd08 	add	x8, x8, #0x9b3
   a3c4c:	90fffba9 	adrp	x9, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3c50:	91030929 	add	x9, x9, #0xc2
   a3c54:	a91123ff 	stp	xzr, x8, [sp, #272]
   a3c58:	90fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3c5c:	913b1108 	add	x8, x8, #0xec4
   a3c60:	a91227e8 	stp	x8, x9, [sp, #288]
   a3c64:	d0fffb88 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a3c68:	9117b508 	add	x8, x8, #0x5ed
   a3c6c:	90fffba9 	adrp	x9, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3c70:	910a8529 	add	x9, x9, #0x2a1
   a3c74:	a91327e8 	stp	x8, x9, [sp, #304]
   a3c78:	90fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3c7c:	9124ad08 	add	x8, x8, #0x92b
   a3c80:	f900a3e8 	str	x8, [sp, #320]
   a3c84:	b0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a3c88:	912f6108 	add	x8, x8, #0xbd8
   a3c8c:	a91057e8 	stp	x8, x21, [sp, #256]
   a3c90:	aa1503e0 	orr	x0, xzr, x21
   a3c94:	97fe88e5 	bl	46028 <JNI_OnUnload@@Base+0x11048>
   a3c98:	f9008be0 	str	x0, [sp, #272]
   a3c9c:	910403e0 	add	x0, sp, #0x100
   a3ca0:	97fe88fb 	bl	4608c <JNI_OnUnload@@Base+0x110ac>
   a3ca4:	b40007e0 	cbz	x0, a3da0 <JNI_OnUnload@@Base+0x6edc0>
   a3ca8:	f0fffb88 	adrp	x8, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a3cac:	91227d08 	add	x8, x8, #0x89f
   a3cb0:	d0fffb89 	adrp	x9, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a3cb4:	91123129 	add	x9, x9, #0x48c
   a3cb8:	a90ea7e8 	stp	x8, x9, [sp, #232]
   a3cbc:	b0fffba8 	adrp	x8, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a3cc0:	911fc908 	add	x8, x8, #0x7f2
   a3cc4:	f9007fe8 	str	x8, [sp, #248]
   a3cc8:	b0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a3ccc:	91302108 	add	x8, x8, #0xc08
   a3cd0:	a90cd7e8 	stp	x8, x21, [sp, #200]
   a3cd4:	f0fffb88 	adrp	x8, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a3cd8:	9106f908 	add	x8, x8, #0x1be
   a3cdc:	a90da3e0 	stp	x0, x8, [sp, #216]
   a3ce0:	910323e0 	add	x0, sp, #0xc8
   a3ce4:	97fe8910 	bl	46124 <JNI_OnUnload@@Base+0x11144>
   a3ce8:	b4000620 	cbz	x0, a3dac <JNI_OnUnload@@Base+0x6edcc>
   a3cec:	d0fffb88 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a3cf0:	91124508 	add	x8, x8, #0x491
   a3cf4:	f0fffb89 	adrp	x9, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a3cf8:	91231129 	add	x9, x9, #0x8c4
   a3cfc:	f81e03a8 	stur	x8, [x29, #-32]
   a3d00:	a93b27a8 	stp	x8, x9, [x29, #-80]
   a3d04:	90fffba9 	adrp	x9, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3d08:	913bdd29 	add	x9, x9, #0xef7
   a3d0c:	a93c27a8 	stp	x8, x9, [x29, #-64]
   a3d10:	f0fffb89 	adrp	x9, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a3d14:	91123529 	add	x9, x9, #0x48d
   a3d18:	a93d27a8 	stp	x8, x9, [x29, #-48]
   a3d1c:	b0000048 	adrp	x8, ac000 <pthread_key_delete@plt+0x3580>
   a3d20:	9131e108 	add	x8, x8, #0xc78
   a3d24:	a93957a8 	stp	x8, x21, [x29, #-112]
   a3d28:	90fffba8 	adrp	x8, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a3d2c:	913bb508 	add	x8, x8, #0xeed
   a3d30:	a93a23a0 	stp	x0, x8, [x29, #-96]
   a3d34:	d101c3a0 	sub	x0, x29, #0x70
   a3d38:	97fe890b 	bl	46164 <JNI_OnUnload@@Base+0x11184>
   a3d3c:	aa0003f6 	orr	x22, xzr, x0
   a3d40:	b40003c0 	cbz	x0, a3db8 <JNI_OnUnload@@Base+0x6edd8>
   a3d44:	f94002a8 	ldr	x8, [x21]
   a3d48:	f942a508 	ldr	x8, [x8, #1352]
   a3d4c:	aa1503e0 	orr	x0, xzr, x21
   a3d50:	aa1603e1 	orr	x1, xzr, x22
   a3d54:	aa1f03e2 	orr	x2, xzr, xzr
   a3d58:	d63f0100 	blr	x8
   a3d5c:	aa0003f7 	orr	x23, xzr, x0
   a3d60:	b40003e0 	cbz	x0, a3ddc <JNI_OnUnload@@Base+0x6edfc>
   a3d64:	394002e8 	ldrb	w8, [x23]
   a3d68:	340002e8 	cbz	w8, a3dc4 <JNI_OnUnload@@Base+0x6ede4>
   a3d6c:	aa1703e0 	orr	x0, xzr, x23
   a3d70:	94001124 	bl	a8200 <strlen@plt>
   a3d74:	b100401f 	adds	xzr, x0, #0x10
   a3d78:	540042e2 	b.cs	a45d4 <JNI_OnUnload@@Base+0x6f5f4>  // b.hs, b.nlast
   a3d7c:	aa0003f8 	orr	x24, xzr, x0
   a3d80:	f1005c1f 	subs	xzr, x0, #0x17
   a3d84:	54000382 	b.cs	a3df4 <JNI_OnUnload@@Base+0x6ee14>  // b.hs, b.nlast
   a3d88:	531f7b08 	ubfm	w8, w24, #31, #30
   a3d8c:	9102c3e9 	add	x9, sp, #0xb0
   a3d90:	b2400139 	orr	x25, x9, #0x1
   a3d94:	3902c3e8 	strb	w8, [sp, #176]
   a3d98:	b50003f8 	cbnz	x24, a3e14 <JNI_OnUnload@@Base+0x6ee34>
   a3d9c:	14000022 	b	a3e24 <JNI_OnUnload@@Base+0x6ee44>
   a3da0:	a97083a1 	ldp	x1, x0, [x29, #-248]
   a3da4:	97fe7ec5 	bl	438b8 <JNI_OnUnload@@Base+0xe8d8>
   a3da8:	1400000f 	b	a3de4 <JNI_OnUnload@@Base+0x6ee04>
   a3dac:	a97083a1 	ldp	x1, x0, [x29, #-248]
   a3db0:	97fe7ec2 	bl	438b8 <JNI_OnUnload@@Base+0xe8d8>
   a3db4:	1400000c 	b	a3de4 <JNI_OnUnload@@Base+0x6ee04>
   a3db8:	a97083a1 	ldp	x1, x0, [x29, #-248]
   a3dbc:	97fe7ebf 	bl	438b8 <JNI_OnUnload@@Base+0xe8d8>
   a3dc0:	14000009 	b	a3de4 <JNI_OnUnload@@Base+0x6ee04>
   a3dc4:	f94002a8 	ldr	x8, [x21]
   a3dc8:	f942a908 	ldr	x8, [x8, #1360]
   a3dcc:	aa1503e0 	orr	x0, xzr, x21
   a3dd0:	aa1603e1 	orr	x1, xzr, x22
   a3dd4:	aa1703e2 	orr	x2, xzr, x23
   a3dd8:	d63f0100 	blr	x8
   a3ddc:	a97083a1 	ldp	x1, x0, [x29, #-248]
   a3de0:	97fe7eb6 	bl	438b8 <JNI_OnUnload@@Base+0xe8d8>
   a3de4:	b4003713 	cbz	x19, a44c4 <JNI_OnUnload@@Base+0x6f4e4>
   a3de8:	aa1303e0 	orr	x0, xzr, x19
   a3dec:	97ffe2cc 	bl	9c91c <JNI_OnUnload@@Base+0x6793c>
   a3df0:	140001b5 	b	a44c4 <JNI_OnUnload@@Base+0x6f4e4>
   a3df4:	b2400f08 	orr	x8, x24, #0xf
   a3df8:	9100051a 	add	x26, x8, #0x1
   a3dfc:	aa1a03e0 	orr	x0, xzr, x26
   a3e00:	97ffe2a7 	bl	9c89c <JNI_OnUnload@@Base+0x678bc>
   a3e04:	b2400348 	orr	x8, x26, #0x1
   a3e08:	aa0003f9 	orr	x25, xzr, x0
   a3e0c:	a90b83f8 	stp	x24, x0, [sp, #184]
   a3e10:	f9005be8 	str	x8, [sp, #176]
   a3e14:	aa1903e0 	orr	x0, xzr, x25
   a3e18:	aa1703e1 	orr	x1, xzr, x23
   a3e1c:	aa1803e2 	orr	x2, xzr, x24
   a3e20:	940010fc 	bl	a8210 <memmove@plt>
   a3e24:	38386b3f 	strb	wzr, [x25, x24]
   a3e28:	f94002a8 	ldr	x8, [x21]
   a3e2c:	f942a908 	ldr	x8, [x8, #1360]
   a3e30:	aa1503e0 	orr	x0, xzr, x21
   a3e34:	aa1603e1 	orr	x1, xzr, x22
   a3e38:	aa1703e2 	orr	x2, xzr, x23
   a3e3c:	d63f0100 	blr	x8
   a3e40:	d0fffb88 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a3e44:	91008108 	add	x8, x8, #0x20
   a3e48:	d503201f 	hint	#0x0
   a3e4c:	10093f60 	adr	x0, b6638 <DPT_UNKNOWN_DATA@@Base+0x1af>
   a3e50:	3dc00100 	ldr	q0, [x8]
   a3e54:	3c9703a0 	stur	q0, [x29, #-144]
   a3e58:	97ffe7e1 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a3e5c:	39400008 	ldrb	w8, [x0]
   a3e60:	360036e8 	tbz	w8, #0, a453c <JNI_OnUnload@@Base+0x6f55c>
   a3e64:	d503201f 	hint	#0x0
   a3e68:	10093d80 	adr	x0, b6618 <DPT_UNKNOWN_DATA@@Base+0x18f>
   a3e6c:	97ffe7dc 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a3e70:	aa0003f5 	orr	x21, xzr, x0
   a3e74:	97fe4a93 	bl	368c0 <JNI_OnUnload@@Base+0x18e0>
   a3e78:	d503201f 	hint	#0x0
   a3e7c:	10093fe0 	adr	x0, b6678 <DPT_UNKNOWN_DATA@@Base+0x1ef>
   a3e80:	97ffe7d7 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a3e84:	39400008 	ldrb	w8, [x0]
   a3e88:	36003828 	tbz	w8, #0, a458c <JNI_OnUnload@@Base+0x6f5ac>
   a3e8c:	d503201f 	hint	#0x0
   a3e90:	10093e40 	adr	x0, b6658 <DPT_UNKNOWN_DATA@@Base+0x1cf>
   a3e94:	97ffe7d2 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a3e98:	39400808 	ldrb	w8, [x0, #2]
   a3e9c:	aa0003f6 	orr	x22, xzr, x0
   a3ea0:	34000148 	cbz	w8, a3ec8 <JNI_OnUnload@@Base+0x6eee8>
   a3ea4:	394002c8 	ldrb	w8, [x22]
   a3ea8:	394006ca 	ldrb	w10, [x22, #1]
   a3eac:	528009a9 	movz	w9, #0x4d
   a3eb0:	52800d2b 	movz	w11, #0x69
   a3eb4:	39000adf 	strb	wzr, [x22, #2]
   a3eb8:	4a090108 	eor	w8, w8, w9
   a3ebc:	4a0b0149 	eor	w9, w10, w11
   a3ec0:	390002c8 	strb	w8, [x22]
   a3ec4:	390006c9 	strb	w9, [x22, #1]
   a3ec8:	3942c3fa 	ldrb	w26, [sp, #176]
   a3ecc:	f9405fe8 	ldr	x8, [sp, #184]
   a3ed0:	aa1603e0 	orr	x0, xzr, x22
   a3ed4:	d341ff49 	ubfm	x9, x26, #1, #63
   a3ed8:	7200035f 	ands	wzr, w26, #0x1
   a3edc:	9a880138 	csel	x24, x9, x8, eq	// eq = none
   a3ee0:	940010c8 	bl	a8200 <strlen@plt>
   a3ee4:	8b00031c 	add	x28, x24, x0
   a3ee8:	b100439f 	adds	xzr, x28, #0x10
   a3eec:	54003802 	b.cs	a45ec <JNI_OnUnload@@Base+0x6f60c>  // b.hs, b.nlast
   a3ef0:	aa0003f7 	orr	x23, xzr, x0
   a3ef4:	f1005b9f 	subs	xzr, x28, #0x16
   a3ef8:	54000108 	b.hi	a3f18 <JNI_OnUnload@@Base+0x6ef38>  // b.pmore
   a3efc:	12001f88 	and	w8, w28, #0xff
   a3f00:	aa1f03e0 	orr	x0, xzr, xzr
   a3f04:	a909ffff 	stp	xzr, xzr, [sp, #152]
   a3f08:	531f7908 	ubfm	w8, w8, #31, #30
   a3f0c:	f90057ff 	str	xzr, [sp, #168]
   a3f10:	390263e8 	strb	w8, [sp, #152]
   a3f14:	14000009 	b	a3f38 <JNI_OnUnload@@Base+0x6ef58>
   a3f18:	b2400f88 	orr	x8, x28, #0xf
   a3f1c:	91000519 	add	x25, x8, #0x1
   a3f20:	aa1903e0 	orr	x0, xzr, x25
   a3f24:	97ffe25e 	bl	9c89c <JNI_OnUnload@@Base+0x678bc>
   a3f28:	b2400328 	orr	x8, x25, #0x1
   a3f2c:	a90a03fc 	stp	x28, x0, [sp, #160]
   a3f30:	f9004fe8 	str	x8, [sp, #152]
   a3f34:	12001d08 	and	w8, w8, #0xff
   a3f38:	910263e9 	add	x9, sp, #0x98
   a3f3c:	7200011f 	ands	wzr, w8, #0x1
   a3f40:	b2400129 	orr	x9, x9, #0x1
   a3f44:	9a800139 	csel	x25, x9, x0, eq	// eq = none
   a3f48:	b4000138 	cbz	x24, a3f6c <JNI_OnUnload@@Base+0x6ef8c>
   a3f4c:	9102c3e8 	add	x8, sp, #0xb0
   a3f50:	f94063e9 	ldr	x9, [sp, #192]
   a3f54:	7200035f 	ands	wzr, w26, #0x1
   a3f58:	b2400108 	orr	x8, x8, #0x1
   a3f5c:	aa1903e0 	orr	x0, xzr, x25
   a3f60:	aa1803e2 	orr	x2, xzr, x24
   a3f64:	9a890101 	csel	x1, x8, x9, eq	// eq = none
   a3f68:	940010aa 	bl	a8210 <memmove@plt>
   a3f6c:	8b180338 	add	x24, x25, x24
   a3f70:	b40000b7 	cbz	x23, a3f84 <JNI_OnUnload@@Base+0x6efa4>
   a3f74:	aa1803e0 	orr	x0, xzr, x24
   a3f78:	aa1603e1 	orr	x1, xzr, x22
   a3f7c:	aa1703e2 	orr	x2, xzr, x23
   a3f80:	940010a4 	bl	a8210 <memmove@plt>
   a3f84:	aa1503e0 	orr	x0, xzr, x21
   a3f88:	38376b1f 	strb	wzr, [x24, x23]
   a3f8c:	9400109d 	bl	a8200 <strlen@plt>
   a3f90:	aa0003e2 	orr	x2, xzr, x0
   a3f94:	910263e0 	add	x0, sp, #0x98
   a3f98:	aa1503e1 	orr	x1, xzr, x21
   a3f9c:	97feed42 	bl	5f4a4 <JNI_OnUnload@@Base+0x2a4c4>
   a3fa0:	f9400808 	ldr	x8, [x0, #16]
   a3fa4:	3dc00000 	ldr	q0, [x0]
   a3fa8:	f81803a8 	stur	x8, [x29, #-128]
   a3fac:	3c9703a0 	stur	q0, [x29, #-144]
   a3fb0:	a900fc1f 	stp	xzr, xzr, [x0, #8]
   a3fb4:	f900001f 	str	xzr, [x0]
   a3fb8:	394263e8 	ldrb	w8, [sp, #152]
   a3fbc:	36000068 	tbz	w8, #0, a3fc8 <JNI_OnUnload@@Base+0x6efe8>
   a3fc0:	f94057e0 	ldr	x0, [sp, #168]
   a3fc4:	97ffe254 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a3fc8:	385703a8 	ldurb	w8, [x29, #-144]
   a3fcc:	a977abab 	ldp	x11, x10, [x29, #-136]
   a3fd0:	d10243a9 	sub	x9, x29, #0x90
   a3fd4:	d341fd0c 	ubfm	x12, x8, #1, #63
   a3fd8:	b2400129 	orr	x9, x9, #0x1
   a3fdc:	7200011f 	ands	wzr, w8, #0x1
   a3fe0:	9a8a0122 	csel	x2, x9, x10, eq	// eq = none
   a3fe4:	9a8b0183 	csel	x3, x12, x11, eq	// eq = none
   a3fe8:	d0000075 	adrp	x21, b1000 <_ZTVNSt6__ndk117bad_function_callE@@Base+0x42b8>
   a3fec:	910263e8 	add	x8, sp, #0x98
   a3ff0:	52800201 	movz	w1, #0x10
   a3ff4:	f945b6b5 	ldr	x21, [x21, #2920]
   a3ff8:	aa1503e0 	orr	x0, xzr, x21
   a3ffc:	97fe84d8 	bl	4535c <JNI_OnUnload@@Base+0x1037c>
   a4000:	a949a7e8 	ldp	x8, x9, [sp, #152]
   a4004:	cb080129 	sub	x9, x9, x8
   a4008:	f100813f 	subs	xzr, x9, #0x20
   a400c:	540005c1 	b.ne	a40c4 <JNI_OnUnload@@Base+0x6f0e4>  // b.any
   a4010:	ad400101 	ldp	q1, q0, [x8]
   a4014:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a4018:	91114108 	add	x8, x8, #0x450
   a401c:	ad000101 	stp	q1, q0, [x8]
   a4020:	910203e0 	add	x0, sp, #0x80
   a4024:	8b140262 	add	x2, x19, x20
   a4028:	aa1303e1 	orr	x1, xzr, x19
   a402c:	97fe40c8 	bl	3434c <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b462>
   a4030:	3dc002a0 	ldr	q0, [x21]
   a4034:	f9404fe0 	ldr	x0, [sp, #152]
   a4038:	528005e8 	movz	w8, #0x2f
   a403c:	52800ec9 	movz	w9, #0x76
   a4040:	f94043e3 	ldr	x3, [sp, #128]
   a4044:	3c9603a0 	stur	q0, [x29, #-160]
   a4048:	381633a8 	sturb	w8, [x29, #-157]
   a404c:	381693a9 	sturb	w9, [x29, #-151]
   a4050:	9101a3e8 	add	x8, sp, #0x68
   a4054:	d10283a2 	sub	x2, x29, #0xa0
   a4058:	52802001 	movz	w1, #0x100
   a405c:	aa1403e4 	orr	x4, xzr, x20
   a4060:	97fe84f9 	bl	45444 <JNI_OnUnload@@Base+0x10464>
   a4064:	a946d7fc 	ldp	x28, x21, [sp, #104]
   a4068:	eb15039f 	subs	xzr, x28, x21
   a406c:	54000480 	b.eq	a40fc <JNI_OnUnload@@Base+0x6f11c>  // b.none
   a4070:	910143e0 	add	x0, sp, #0x50
   a4074:	aa1c03e1 	orr	x1, xzr, x28
   a4078:	aa1503e2 	orr	x2, xzr, x21
   a407c:	f90007fc 	str	x28, [sp, #8]
   a4080:	97fe40d5 	bl	343d4 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b4ea>
   a4084:	9100a3e0 	add	x0, sp, #0x28
   a4088:	910143e1 	add	x1, sp, #0x50
   a408c:	97fe419f 	bl	34708 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b81e>
   a4090:	f81503bf 	stur	xzr, [x29, #-176]
   a4094:	910103e8 	add	x8, sp, #0x40
   a4098:	9100a3e0 	add	x0, sp, #0x28
   a409c:	d10343a1 	sub	x1, x29, #0xd0
   a40a0:	52800022 	movz	w2, #0x1
   a40a4:	d10343b4 	sub	x20, x29, #0xd0
   a40a8:	97fe4109 	bl	344cc <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b5e2>
   a40ac:	f85503a0 	ldur	x0, [x29, #-176]
   a40b0:	eb14001f 	subs	xzr, x0, x20
   a40b4:	540002a0 	b.eq	a4108 <JNI_OnUnload@@Base+0x6f128>  // b.none
   a40b8:	b4000320 	cbz	x0, a411c <JNI_OnUnload@@Base+0x6f13c>
   a40bc:	528000a8 	movz	w8, #0x5
   a40c0:	14000014 	b	a4110 <JNI_OnUnload@@Base+0x6f130>
   a40c4:	a97083a1 	ldp	x1, x0, [x29, #-248]
   a40c8:	97fe7dfc 	bl	438b8 <JNI_OnUnload@@Base+0xe8d8>
   a40cc:	f9404fe0 	ldr	x0, [sp, #152]
   a40d0:	b4000060 	cbz	x0, a40dc <JNI_OnUnload@@Base+0x6f0fc>
   a40d4:	f90053e0 	str	x0, [sp, #160]
   a40d8:	97ffe20f 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a40dc:	385703a8 	ldurb	w8, [x29, #-144]
   a40e0:	36000068 	tbz	w8, #0, a40ec <JNI_OnUnload@@Base+0x6f10c>
   a40e4:	f85803a0 	ldur	x0, [x29, #-128]
   a40e8:	97ffe20b 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a40ec:	3607e7da 	tbz	w26, #0, a3de4 <JNI_OnUnload@@Base+0x6ee04>
   a40f0:	f94063e0 	ldr	x0, [sp, #192]
   a40f4:	97ffe208 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a40f8:	17ffff3b 	b	a3de4 <JNI_OnUnload@@Base+0x6ee04>
   a40fc:	a97083a1 	ldp	x1, x0, [x29, #-248]
   a4100:	97fe7dee 	bl	438b8 <JNI_OnUnload@@Base+0xe8d8>
   a4104:	140000d0 	b	a4444 <JNI_OnUnload@@Base+0x6f464>
   a4108:	52800088 	movz	w8, #0x4
   a410c:	d10343a0 	sub	x0, x29, #0xd0
   a4110:	f9400009 	ldr	x9, [x0]
   a4114:	f8687928 	ldr	x8, [x9, x8, lsl #3]
   a4118:	d63f0100 	blr	x8
   a411c:	9100a3e0 	add	x0, sp, #0x28
   a4120:	97fe4182 	bl	34728 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b83e>
   a4124:	97fe4195 	bl	34778 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b88e>
   a4128:	97fe41b4 	bl	347f8 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b90e>
   a412c:	aa0003fc 	orr	x28, xzr, x0
   a4130:	97fe41bf 	bl	3482c <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b942>
   a4134:	97fe41de 	bl	348ac <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b9c2>
   a4138:	aa0003f4 	orr	x20, xzr, x0
   a413c:	97fe41e9 	bl	348e0 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b9f6>
   a4140:	97fe420b 	bl	3496c <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1ba82>
   a4144:	aa0003fa 	orr	x26, xzr, x0
   a4148:	97fe421d 	bl	349bc <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1bad2>
   a414c:	aa0003f9 	orr	x25, xzr, x0
   a4150:	97fe715b 	bl	406bc <JNI_OnUnload@@Base+0xb6dc>
   a4154:	97fe4248 	bl	34a74 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1bb8a>
   a4158:	97fe4267 	bl	34af4 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1bc0a>
   a415c:	aa0003f8 	orr	x24, xzr, x0
   a4160:	97fe4271 	bl	34b24 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1bc3a>
   a4164:	97fe4296 	bl	34bbc <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1bcd2>
   a4168:	aa0003f7 	orr	x23, xzr, x0
   a416c:	97fe42ac 	bl	34c1c <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1bd32>
   a4170:	aa0003f6 	orr	x22, xzr, x0
   a4174:	97fe715b 	bl	406e0 <JNI_OnUnload@@Base+0xb700>
   a4178:	910043e0 	add	x0, sp, #0x10
   a417c:	aa1c03e1 	orr	x1, xzr, x28
   a4180:	97fe4039 	bl	34264 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b37a>
   a4184:	b0fffb82 	adrp	x2, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a4188:	913e8842 	add	x2, x2, #0xfa2
   a418c:	9100a3e8 	add	x8, sp, #0x28
   a4190:	910103e0 	add	x0, sp, #0x40
   a4194:	910043e1 	add	x1, sp, #0x10
   a4198:	f94007fc 	ldr	x28, [sp, #8]
   a419c:	97fe42d0 	bl	34cdc <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1bdf2>
   a41a0:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a41a4:	394f0108 	ldrb	w8, [x8, #960]
   a41a8:	36000088 	tbz	w8, #0, a41b8 <JNI_OnUnload@@Base+0x6f1d8>
   a41ac:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a41b0:	f941e900 	ldr	x0, [x8, #976]
   a41b4:	97ffe1d8 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a41b8:	3cc283e0 	ldur	q0, [sp, #40]
   a41bc:	f9401fe9 	ldr	x9, [sp, #56]
   a41c0:	d503201f 	hint	#0x0
   a41c4:	10098fe8 	adr	x8, b73c0 <DPT_UNKNOWN_DATA@@Base+0xf37>
   a41c8:	394043ea 	ldrb	w10, [sp, #16]
   a41cc:	790053ff 	strh	wzr, [sp, #40]
   a41d0:	3d800100 	str	q0, [x8]
   a41d4:	f9000909 	str	x9, [x8, #16]
   a41d8:	3600006a 	tbz	w10, #0, a41e4 <JNI_OnUnload@@Base+0x6f204>
   a41dc:	f94013e0 	ldr	x0, [sp, #32]
   a41e0:	97ffe1cd 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a41e4:	910043e0 	add	x0, sp, #0x10
   a41e8:	aa1403e1 	orr	x1, xzr, x20
   a41ec:	97fe401e 	bl	34264 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b37a>
   a41f0:	b0fffb82 	adrp	x2, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a41f4:	913e8842 	add	x2, x2, #0xfa2
   a41f8:	9100a3e8 	add	x8, sp, #0x28
   a41fc:	910103e0 	add	x0, sp, #0x40
   a4200:	910043e1 	add	x1, sp, #0x10
   a4204:	97fe42b6 	bl	34cdc <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1bdf2>
   a4208:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a420c:	394f6108 	ldrb	w8, [x8, #984]
   a4210:	36000088 	tbz	w8, #0, a4220 <JNI_OnUnload@@Base+0x6f240>
   a4214:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a4218:	f941f500 	ldr	x0, [x8, #1000]
   a421c:	97ffe1be 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a4220:	3cc283e0 	ldur	q0, [sp, #40]
   a4224:	f9401fe9 	ldr	x9, [sp, #56]
   a4228:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a422c:	910f6108 	add	x8, x8, #0x3d8
   a4230:	394043ea 	ldrb	w10, [sp, #16]
   a4234:	790053ff 	strh	wzr, [sp, #40]
   a4238:	3d800100 	str	q0, [x8]
   a423c:	f9000909 	str	x9, [x8, #16]
   a4240:	3600006a 	tbz	w10, #0, a424c <JNI_OnUnload@@Base+0x6f26c>
   a4244:	f94013e0 	ldr	x0, [sp, #32]
   a4248:	97ffe1b3 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a424c:	910043e0 	add	x0, sp, #0x10
   a4250:	aa1a03e1 	orr	x1, xzr, x26
   a4254:	97fe4004 	bl	34264 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b37a>
   a4258:	b0fffb82 	adrp	x2, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a425c:	913e8842 	add	x2, x2, #0xfa2
   a4260:	9100a3e8 	add	x8, sp, #0x28
   a4264:	910103e0 	add	x0, sp, #0x40
   a4268:	910043e1 	add	x1, sp, #0x10
   a426c:	97fe429c 	bl	34cdc <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1bdf2>
   a4270:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a4274:	394fc108 	ldrb	w8, [x8, #1008]
   a4278:	36000088 	tbz	w8, #0, a4288 <JNI_OnUnload@@Base+0x6f2a8>
   a427c:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a4280:	f9420100 	ldr	x0, [x8, #1024]
   a4284:	97ffe1a4 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a4288:	3cc283e0 	ldur	q0, [sp, #40]
   a428c:	f9401fe9 	ldr	x9, [sp, #56]
   a4290:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a4294:	910fc108 	add	x8, x8, #0x3f0
   a4298:	394043ea 	ldrb	w10, [sp, #16]
   a429c:	790053ff 	strh	wzr, [sp, #40]
   a42a0:	3d800100 	str	q0, [x8]
   a42a4:	f9000909 	str	x9, [x8, #16]
   a42a8:	3600006a 	tbz	w10, #0, a42b4 <JNI_OnUnload@@Base+0x6f2d4>
   a42ac:	f94013e0 	ldr	x0, [sp, #32]
   a42b0:	97ffe199 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a42b4:	910043e0 	add	x0, sp, #0x10
   a42b8:	aa1903e1 	orr	x1, xzr, x25
   a42bc:	97fe3fea 	bl	34264 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b37a>
   a42c0:	b0fffb82 	adrp	x2, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a42c4:	913e8842 	add	x2, x2, #0xfa2
   a42c8:	9100a3e8 	add	x8, sp, #0x28
   a42cc:	910103e0 	add	x0, sp, #0x40
   a42d0:	910043e1 	add	x1, sp, #0x10
   a42d4:	97fe4282 	bl	34cdc <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1bdf2>
   a42d8:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a42dc:	39502108 	ldrb	w8, [x8, #1032]
   a42e0:	36000088 	tbz	w8, #0, a42f0 <JNI_OnUnload@@Base+0x6f310>
   a42e4:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a42e8:	f9420d00 	ldr	x0, [x8, #1048]
   a42ec:	97ffe18a 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a42f0:	3cc283e0 	ldur	q0, [sp, #40]
   a42f4:	f9401fe9 	ldr	x9, [sp, #56]
   a42f8:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a42fc:	91102108 	add	x8, x8, #0x408
   a4300:	394043ea 	ldrb	w10, [sp, #16]
   a4304:	790053ff 	strh	wzr, [sp, #40]
   a4308:	3d800100 	str	q0, [x8]
   a430c:	f9000909 	str	x9, [x8, #16]
   a4310:	3600006a 	tbz	w10, #0, a431c <JNI_OnUnload@@Base+0x6f33c>
   a4314:	f94013e0 	ldr	x0, [sp, #32]
   a4318:	97ffe17f 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a431c:	910043e0 	add	x0, sp, #0x10
   a4320:	aa1803e1 	orr	x1, xzr, x24
   a4324:	97fe3fd0 	bl	34264 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b37a>
   a4328:	b0fffb82 	adrp	x2, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a432c:	913e8842 	add	x2, x2, #0xfa2
   a4330:	9100a3e8 	add	x8, sp, #0x28
   a4334:	910103e0 	add	x0, sp, #0x40
   a4338:	910043e1 	add	x1, sp, #0x10
   a433c:	97fe4268 	bl	34cdc <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1bdf2>
   a4340:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a4344:	39508108 	ldrb	w8, [x8, #1056]
   a4348:	36000088 	tbz	w8, #0, a4358 <JNI_OnUnload@@Base+0x6f378>
   a434c:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a4350:	f9421900 	ldr	x0, [x8, #1072]
   a4354:	97ffe170 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a4358:	3cc283e0 	ldur	q0, [sp, #40]
   a435c:	f9401fe9 	ldr	x9, [sp, #56]
   a4360:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a4364:	91108108 	add	x8, x8, #0x420
   a4368:	394043ea 	ldrb	w10, [sp, #16]
   a436c:	790053ff 	strh	wzr, [sp, #40]
   a4370:	3d800100 	str	q0, [x8]
   a4374:	f9000909 	str	x9, [x8, #16]
   a4378:	3600006a 	tbz	w10, #0, a4384 <JNI_OnUnload@@Base+0x6f3a4>
   a437c:	f94013e0 	ldr	x0, [sp, #32]
   a4380:	97ffe165 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a4384:	910043e0 	add	x0, sp, #0x10
   a4388:	aa1703e1 	orr	x1, xzr, x23
   a438c:	97fe3fb6 	bl	34264 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b37a>
   a4390:	b0fffb82 	adrp	x2, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a4394:	913e8842 	add	x2, x2, #0xfa2
   a4398:	9100a3e8 	add	x8, sp, #0x28
   a439c:	910103e0 	add	x0, sp, #0x40
   a43a0:	910043e1 	add	x1, sp, #0x10
   a43a4:	97fe424e 	bl	34cdc <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1bdf2>
   a43a8:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a43ac:	3950e108 	ldrb	w8, [x8, #1080]
   a43b0:	36000088 	tbz	w8, #0, a43c0 <JNI_OnUnload@@Base+0x6f3e0>
   a43b4:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a43b8:	f9422500 	ldr	x0, [x8, #1096]
   a43bc:	97ffe156 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a43c0:	3cc283e0 	ldur	q0, [sp, #40]
   a43c4:	f9401fe9 	ldr	x9, [sp, #56]
   a43c8:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a43cc:	9110e108 	add	x8, x8, #0x438
   a43d0:	394043ea 	ldrb	w10, [sp, #16]
   a43d4:	790053ff 	strh	wzr, [sp, #40]
   a43d8:	3d800100 	str	q0, [x8]
   a43dc:	f9000909 	str	x9, [x8, #16]
   a43e0:	3600006a 	tbz	w10, #0, a43ec <JNI_OnUnload@@Base+0x6f40c>
   a43e4:	f94013e0 	ldr	x0, [sp, #32]
   a43e8:	97ffe14b 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a43ec:	9100a3e0 	add	x0, sp, #0x28
   a43f0:	aa1603e1 	orr	x1, xzr, x22
   a43f4:	97fe3f9c 	bl	34264 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b37a>
   a43f8:	b90013ff 	str	wzr, [sp, #16]
   a43fc:	910103e0 	add	x0, sp, #0x40
   a4400:	9100a3e1 	add	x1, sp, #0x28
   a4404:	910043e2 	add	x2, sp, #0x10
   a4408:	97fe4282 	bl	34e10 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1bf26>
   a440c:	3940a3e8 	ldrb	w8, [sp, #40]
   a4410:	f0000089 	adrp	x9, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a4414:	b9047120 	str	w0, [x9, #1136]
   a4418:	36000068 	tbz	w8, #0, a4424 <JNI_OnUnload@@Base+0x6f444>
   a441c:	f9401fe0 	ldr	x0, [sp, #56]
   a4420:	97ffe13d 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a4424:	394103e1 	ldrb	w1, [sp, #64]
   a4428:	910103e8 	add	x8, sp, #0x40
   a442c:	91002100 	add	x0, x8, #0x8
   a4430:	97fe4525 	bl	358c4 <JNI_OnUnload@@Base+0x8e4>
   a4434:	394143e8 	ldrb	w8, [sp, #80]
   a4438:	36000068 	tbz	w8, #0, a4444 <JNI_OnUnload@@Base+0x6f464>
   a443c:	f94033e0 	ldr	x0, [sp, #96]
   a4440:	97ffe135 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a4444:	f94037e0 	ldr	x0, [sp, #104]
   a4448:	b4000060 	cbz	x0, a4454 <JNI_OnUnload@@Base+0x6f474>
   a444c:	f9003be0 	str	x0, [sp, #112]
   a4450:	97ffe131 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a4454:	f94043e0 	ldr	x0, [sp, #128]
   a4458:	b4000060 	cbz	x0, a4464 <JNI_OnUnload@@Base+0x6f484>
   a445c:	f90047e0 	str	x0, [sp, #136]
   a4460:	97ffe12d 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a4464:	f9404fe0 	ldr	x0, [sp, #152]
   a4468:	b4000060 	cbz	x0, a4474 <JNI_OnUnload@@Base+0x6f494>
   a446c:	f90053e0 	str	x0, [sp, #160]
   a4470:	97ffe129 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a4474:	385703a8 	ldurb	w8, [x29, #-144]
   a4478:	370000c8 	tbnz	w8, #0, a4490 <JNI_OnUnload@@Base+0x6f4b0>
   a447c:	3942c3e8 	ldrb	w8, [sp, #176]
   a4480:	37000108 	tbnz	w8, #0, a44a0 <JNI_OnUnload@@Base+0x6f4c0>
   a4484:	eb15039f 	subs	xzr, x28, x21
   a4488:	54000141 	b.ne	a44b0 <JNI_OnUnload@@Base+0x6f4d0>  // b.any
   a448c:	17fffe56 	b	a3de4 <JNI_OnUnload@@Base+0x6ee04>
   a4490:	f85803a0 	ldur	x0, [x29, #-128]
   a4494:	97ffe120 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a4498:	3942c3e8 	ldrb	w8, [sp, #176]
   a449c:	3607ff48 	tbz	w8, #0, a4484 <JNI_OnUnload@@Base+0x6f4a4>
   a44a0:	f94063e0 	ldr	x0, [sp, #192]
   a44a4:	97ffe11c 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a44a8:	eb15039f 	subs	xzr, x28, x21
   a44ac:	54ffc9c0 	b.eq	a3de4 <JNI_OnUnload@@Base+0x6ee04>  // b.none
   a44b0:	b4000073 	cbz	x19, a44bc <JNI_OnUnload@@Base+0x6f4dc>
   a44b4:	aa1303e0 	orr	x0, xzr, x19
   a44b8:	97ffe119 	bl	9c91c <JNI_OnUnload@@Base+0x6793c>
   a44bc:	a97083a1 	ldp	x1, x0, [x29, #-248]
   a44c0:	97fe7cfe 	bl	438b8 <JNI_OnUnload@@Base+0xe8d8>
   a44c4:	f9401768 	ldr	x8, [x27, #40]
   a44c8:	f85f03a9 	ldur	x9, [x29, #-16]
   a44cc:	eb09011f 	subs	xzr, x8, x9
   a44d0:	54001b21 	b.ne	a4834 <JNI_OnUnload@@Base+0x6f854>  // b.any
   a44d4:	910903ff 	add	sp, sp, #0x240
   a44d8:	a9454ff4 	ldp	x20, x19, [sp, #80]
   a44dc:	a94457f6 	ldp	x22, x21, [sp, #64]
   a44e0:	a9435ff8 	ldp	x24, x23, [sp, #48]
   a44e4:	a94267fa 	ldp	x26, x25, [sp, #32]
   a44e8:	a9416ffc 	ldp	x28, x27, [sp, #16]
   a44ec:	a8c67bfd 	ldp	x29, x30, [sp], #96
   a44f0:	d65f03c0 	ret
   a44f4:	d503201f 	hint	#0x0
   a44f8:	10090700 	adr	x0, b65d8 <DPT_UNKNOWN_DATA@@Base+0x14f>
   a44fc:	97ffe638 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a4500:	d101c3a1 	sub	x1, x29, #0x70
   a4504:	aa0003f6 	orr	x22, xzr, x0
   a4508:	97fe441d 	bl	3557c <JNI_OnUnload@@Base+0x59c>
   a450c:	d503201f 	hint	#0x0
   a4510:	10c889c0 	adr	x0, 35648 <JNI_OnUnload@@Base+0x668>
   a4514:	d503201f 	hint	#0x0
   a4518:	10042bc2 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a451c:	aa1603e1 	orr	x1, xzr, x22
   a4520:	97ffe595 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a4524:	d503201f 	hint	#0x0
   a4528:	10090680 	adr	x0, b65f8 <DPT_UNKNOWN_DATA@@Base+0x16f>
   a452c:	97ffe62c 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a4530:	52800028 	movz	w8, #0x1
   a4534:	39000008 	strb	w8, [x0]
   a4538:	17fffdab 	b	a3be4 <JNI_OnUnload@@Base+0x6ec04>
   a453c:	d503201f 	hint	#0x0
   a4540:	100906c0 	adr	x0, b6618 <DPT_UNKNOWN_DATA@@Base+0x18f>
   a4544:	97ffe626 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a4548:	d10243a1 	sub	x1, x29, #0x90
   a454c:	97fe4442 	bl	35654 <JNI_OnUnload@@Base+0x674>
   a4550:	d503201f 	hint	#0x0
   a4554:	10090620 	adr	x0, b6618 <DPT_UNKNOWN_DATA@@Base+0x18f>
   a4558:	97ffe621 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a455c:	aa0003e1 	orr	x1, xzr, x0
   a4560:	d503201f 	hint	#0x0
   a4564:	10c88be0 	adr	x0, 356e0 <JNI_OnUnload@@Base+0x700>
   a4568:	d503201f 	hint	#0x0
   a456c:	10042922 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a4570:	97ffe581 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a4574:	d503201f 	hint	#0x0
   a4578:	10090600 	adr	x0, b6638 <DPT_UNKNOWN_DATA@@Base+0x1af>
   a457c:	97ffe618 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a4580:	52800028 	movz	w8, #0x1
   a4584:	39000008 	strb	w8, [x0]
   a4588:	17fffe37 	b	a3e64 <JNI_OnUnload@@Base+0x6ee84>
   a458c:	d503201f 	hint	#0x0
   a4590:	10090640 	adr	x0, b6658 <DPT_UNKNOWN_DATA@@Base+0x1cf>
   a4594:	97ffe612 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a4598:	52800036 	movz	w22, #0x1
   a459c:	528d2248 	movz	w8, #0x6912
   a45a0:	aa0003e1 	orr	x1, xzr, x0
   a45a4:	39000816 	strb	w22, [x0, #2]
   a45a8:	d503201f 	hint	#0x0
   a45ac:	10042722 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a45b0:	79000008 	strh	w8, [x0]
   a45b4:	d503201f 	hint	#0x0
   a45b8:	10c88980 	adr	x0, 356e8 <JNI_OnUnload@@Base+0x708>
   a45bc:	97ffe56e 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a45c0:	d503201f 	hint	#0x0
   a45c4:	100905a0 	adr	x0, b6678 <DPT_UNKNOWN_DATA@@Base+0x1ef>
   a45c8:	97ffe605 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a45cc:	39000016 	strb	w22, [x0]
   a45d0:	17fffe2f 	b	a3e8c <JNI_OnUnload@@Base+0x6eeac>
   a45d4:	f9401768 	ldr	x8, [x27, #40]
   a45d8:	f85f03a9 	ldur	x9, [x29, #-16]
   a45dc:	eb09011f 	subs	xzr, x8, x9
   a45e0:	540012a1 	b.ne	a4834 <JNI_OnUnload@@Base+0x6f854>  // b.any
   a45e4:	9102c3e0 	add	x0, sp, #0xb0
   a45e8:	97fe42da 	bl	35150 <JNI_OnUnload@@Base+0x170>
   a45ec:	f9401768 	ldr	x8, [x27, #40]
   a45f0:	f85f03a9 	ldur	x9, [x29, #-16]
   a45f4:	eb09011f 	subs	xzr, x8, x9
   a45f8:	540011e1 	b.ne	a4834 <JNI_OnUnload@@Base+0x6f854>  // b.any
   a45fc:	910263e0 	add	x0, sp, #0x98
   a4600:	97fe42d4 	bl	35150 <JNI_OnUnload@@Base+0x170>
   a4604:	14000077 	b	a47e0 <JNI_OnUnload@@Base+0x6f800>
   a4608:	aa0003f6 	orr	x22, xzr, x0
   a460c:	f94037e0 	ldr	x0, [sp, #104]
   a4610:	b5000100 	cbnz	x0, a4630 <JNI_OnUnload@@Base+0x6f650>
   a4614:	f94043e0 	ldr	x0, [sp, #128]
   a4618:	b5000b00 	cbnz	x0, a4778 <JNI_OnUnload@@Base+0x6f798>
   a461c:	f9404fe0 	ldr	x0, [sp, #152]
   a4620:	b5000be0 	cbnz	x0, a479c <JNI_OnUnload@@Base+0x6f7bc>
   a4624:	385703a8 	ldurb	w8, [x29, #-144]
   a4628:	37000ca8 	tbnz	w8, #0, a47bc <JNI_OnUnload@@Base+0x6f7dc>
   a462c:	1400006e 	b	a47e4 <JNI_OnUnload@@Base+0x6f804>
   a4630:	f9003be0 	str	x0, [sp, #112]
   a4634:	97ffe0b8 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a4638:	f94043e0 	ldr	x0, [sp, #128]
   a463c:	b4ffff00 	cbz	x0, a461c <JNI_OnUnload@@Base+0x6f63c>
   a4640:	1400004e 	b	a4778 <JNI_OnUnload@@Base+0x6f798>
   a4644:	3940a3e8 	ldrb	w8, [sp, #40]
   a4648:	aa0103f7 	orr	x23, xzr, x1
   a464c:	aa0003f6 	orr	x22, xzr, x0
   a4650:	360003c8 	tbz	w8, #0, a46c8 <JNI_OnUnload@@Base+0x6f6e8>
   a4654:	f9401fe0 	ldr	x0, [sp, #56]
   a4658:	14000011 	b	a469c <JNI_OnUnload@@Base+0x6f6bc>
   a465c:	14000019 	b	a46c0 <JNI_OnUnload@@Base+0x6f6e0>
   a4660:	1400000a 	b	a4688 <JNI_OnUnload@@Base+0x6f6a8>
   a4664:	14000017 	b	a46c0 <JNI_OnUnload@@Base+0x6f6e0>
   a4668:	14000008 	b	a4688 <JNI_OnUnload@@Base+0x6f6a8>
   a466c:	14000015 	b	a46c0 <JNI_OnUnload@@Base+0x6f6e0>
   a4670:	14000006 	b	a4688 <JNI_OnUnload@@Base+0x6f6a8>
   a4674:	14000013 	b	a46c0 <JNI_OnUnload@@Base+0x6f6e0>
   a4678:	14000004 	b	a4688 <JNI_OnUnload@@Base+0x6f6a8>
   a467c:	14000011 	b	a46c0 <JNI_OnUnload@@Base+0x6f6e0>
   a4680:	14000002 	b	a4688 <JNI_OnUnload@@Base+0x6f6a8>
   a4684:	1400000f 	b	a46c0 <JNI_OnUnload@@Base+0x6f6e0>
   a4688:	394043e8 	ldrb	w8, [sp, #16]
   a468c:	aa0103f7 	orr	x23, xzr, x1
   a4690:	aa0003f6 	orr	x22, xzr, x0
   a4694:	360001a8 	tbz	w8, #0, a46c8 <JNI_OnUnload@@Base+0x6f6e8>
   a4698:	f94013e0 	ldr	x0, [sp, #32]
   a469c:	97ffe09e 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a46a0:	1400000a 	b	a46c8 <JNI_OnUnload@@Base+0x6f6e8>
   a46a4:	14000007 	b	a46c0 <JNI_OnUnload@@Base+0x6f6e0>
   a46a8:	14000006 	b	a46c0 <JNI_OnUnload@@Base+0x6f6e0>
   a46ac:	14000005 	b	a46c0 <JNI_OnUnload@@Base+0x6f6e0>
   a46b0:	14000004 	b	a46c0 <JNI_OnUnload@@Base+0x6f6e0>
   a46b4:	14000003 	b	a46c0 <JNI_OnUnload@@Base+0x6f6e0>
   a46b8:	14000002 	b	a46c0 <JNI_OnUnload@@Base+0x6f6e0>
   a46bc:	14000001 	b	a46c0 <JNI_OnUnload@@Base+0x6f6e0>
   a46c0:	aa0103f7 	orr	x23, xzr, x1
   a46c4:	aa0003f6 	orr	x22, xzr, x0
   a46c8:	394103e1 	ldrb	w1, [sp, #64]
   a46cc:	910103e8 	add	x8, sp, #0x40
   a46d0:	91002100 	add	x0, x8, #0x8
   a46d4:	97fe447c 	bl	358c4 <JNI_OnUnload@@Base+0x8e4>
   a46d8:	394143e8 	ldrb	w8, [sp, #80]
   a46dc:	36000068 	tbz	w8, #0, a46e8 <JNI_OnUnload@@Base+0x6f708>
   a46e0:	f94033e0 	ldr	x0, [sp, #96]
   a46e4:	97ffe08c 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a46e8:	710006ff 	subs	wzr, w23, #0x1
   a46ec:	54fff901 	b.ne	a460c <JNI_OnUnload@@Base+0x6f62c>  // b.any
   a46f0:	aa1603e0 	orr	x0, xzr, x22
   a46f4:	97ffe0ac 	bl	9c9a4 <JNI_OnUnload@@Base+0x679c4>
   a46f8:	97ffe123 	bl	9cb84 <JNI_OnUnload@@Base+0x67ba4>
   a46fc:	f94007fc 	ldr	x28, [sp, #8]
   a4700:	17ffff51 	b	a4444 <JNI_OnUnload@@Base+0x6f464>
   a4704:	17ffffc1 	b	a4608 <JNI_OnUnload@@Base+0x6f628>
   a4708:	f85503a8 	ldur	x8, [x29, #-176]
   a470c:	aa0103f7 	orr	x23, xzr, x1
   a4710:	aa0003f6 	orr	x22, xzr, x0
   a4714:	eb14011f 	subs	xzr, x8, x20
   a4718:	54000080 	b.eq	a4728 <JNI_OnUnload@@Base+0x6f748>  // b.none
   a471c:	b4000128 	cbz	x8, a4740 <JNI_OnUnload@@Base+0x6f760>
   a4720:	528000a9 	movz	w9, #0x5
   a4724:	14000003 	b	a4730 <JNI_OnUnload@@Base+0x6f750>
   a4728:	52800089 	movz	w9, #0x4
   a472c:	d10343a8 	sub	x8, x29, #0xd0
   a4730:	f940010a 	ldr	x10, [x8]
   a4734:	aa0803e0 	orr	x0, xzr, x8
   a4738:	f8697949 	ldr	x9, [x10, x9, lsl #3]
   a473c:	d63f0120 	blr	x9
   a4740:	9100a3e0 	add	x0, sp, #0x28
   a4744:	97fe3ff9 	bl	34728 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1b83e>
   a4748:	17ffffe4 	b	a46d8 <JNI_OnUnload@@Base+0x6f6f8>
   a474c:	aa0103f7 	orr	x23, xzr, x1
   a4750:	aa0003f6 	orr	x22, xzr, x0
   a4754:	17ffffe1 	b	a46d8 <JNI_OnUnload@@Base+0x6f6f8>
   a4758:	aa0103f7 	orr	x23, xzr, x1
   a475c:	aa0003f6 	orr	x22, xzr, x0
   a4760:	710006ff 	subs	wzr, w23, #0x1
   a4764:	54fffc60 	b.eq	a46f0 <JNI_OnUnload@@Base+0x6f710>  // b.none
   a4768:	17ffffa9 	b	a460c <JNI_OnUnload@@Base+0x6f62c>
   a476c:	aa0003f6 	orr	x22, xzr, x0
   a4770:	f94043e0 	ldr	x0, [sp, #128]
   a4774:	b4fff540 	cbz	x0, a461c <JNI_OnUnload@@Base+0x6f63c>
   a4778:	f90047e0 	str	x0, [sp, #136]
   a477c:	97ffe066 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a4780:	f9404fe0 	ldr	x0, [sp, #152]
   a4784:	b4fff500 	cbz	x0, a4624 <JNI_OnUnload@@Base+0x6f644>
   a4788:	14000005 	b	a479c <JNI_OnUnload@@Base+0x6f7bc>
   a478c:	14000001 	b	a4790 <JNI_OnUnload@@Base+0x6f7b0>
   a4790:	aa0003f6 	orr	x22, xzr, x0
   a4794:	f9404fe0 	ldr	x0, [sp, #152]
   a4798:	b4fff460 	cbz	x0, a4624 <JNI_OnUnload@@Base+0x6f644>
   a479c:	f90053e0 	str	x0, [sp, #160]
   a47a0:	97ffe05d 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a47a4:	385703a8 	ldurb	w8, [x29, #-144]
   a47a8:	370000a8 	tbnz	w8, #0, a47bc <JNI_OnUnload@@Base+0x6f7dc>
   a47ac:	1400000e 	b	a47e4 <JNI_OnUnload@@Base+0x6f804>
   a47b0:	aa0003f6 	orr	x22, xzr, x0
   a47b4:	385703a8 	ldurb	w8, [x29, #-144]
   a47b8:	36000168 	tbz	w8, #0, a47e4 <JNI_OnUnload@@Base+0x6f804>
   a47bc:	f85803a0 	ldur	x0, [x29, #-128]
   a47c0:	14000005 	b	a47d4 <JNI_OnUnload@@Base+0x6f7f4>
   a47c4:	394263e8 	ldrb	w8, [sp, #152]
   a47c8:	aa0003f6 	orr	x22, xzr, x0
   a47cc:	360000c8 	tbz	w8, #0, a47e4 <JNI_OnUnload@@Base+0x6f804>
   a47d0:	f94057e0 	ldr	x0, [sp, #168]
   a47d4:	97ffe050 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a47d8:	14000003 	b	a47e4 <JNI_OnUnload@@Base+0x6f804>
   a47dc:	14000001 	b	a47e0 <JNI_OnUnload@@Base+0x6f800>
   a47e0:	aa0003f6 	orr	x22, xzr, x0
   a47e4:	3942c3e8 	ldrb	w8, [sp, #176]
   a47e8:	36000148 	tbz	w8, #0, a4810 <JNI_OnUnload@@Base+0x6f830>
   a47ec:	f94063e0 	ldr	x0, [sp, #192]
   a47f0:	97ffe049 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a47f4:	14000007 	b	a4810 <JNI_OnUnload@@Base+0x6f830>
   a47f8:	14000005 	b	a480c <JNI_OnUnload@@Base+0x6f82c>
   a47fc:	14000004 	b	a480c <JNI_OnUnload@@Base+0x6f82c>
   a4800:	14000003 	b	a480c <JNI_OnUnload@@Base+0x6f82c>
   a4804:	14000002 	b	a480c <JNI_OnUnload@@Base+0x6f82c>
   a4808:	14000001 	b	a480c <JNI_OnUnload@@Base+0x6f82c>
   a480c:	aa0003f6 	orr	x22, xzr, x0
   a4810:	b4000073 	cbz	x19, a481c <JNI_OnUnload@@Base+0x6f83c>
   a4814:	aa1303e0 	orr	x0, xzr, x19
   a4818:	97ffe041 	bl	9c91c <JNI_OnUnload@@Base+0x6793c>
   a481c:	f9401768 	ldr	x8, [x27, #40]
   a4820:	f85f03a9 	ldur	x9, [x29, #-16]
   a4824:	eb09011f 	subs	xzr, x8, x9
   a4828:	54000061 	b.ne	a4834 <JNI_OnUnload@@Base+0x6f854>  // b.any
   a482c:	aa1603e0 	orr	x0, xzr, x22
   a4830:	97ffe8d7 	bl	9eb8c <JNI_OnUnload@@Base+0x69bac>
   a4834:	94000e67 	bl	a81d0 <__stack_chk_fail@plt>

00000000000a4838 <JNI_OnLoad@@Base>:
   a4838:	d10103ff 	sub	sp, sp, #0x40
   a483c:	a9017bfd 	stp	x29, x30, [sp, #16]
   a4840:	f90013f5 	str	x21, [sp, #32]
   a4844:	a9034ff4 	stp	x20, x19, [sp, #48]
   a4848:	910043fd 	add	x29, sp, #0x10
   a484c:	d53bd054 	mrs	x20, tpidr_el0
   a4850:	528000c2 	movz	w2, #0x6
   a4854:	528000d5 	movz	w21, #0x6
   a4858:	f9401688 	ldr	x8, [x20, #40]
   a485c:	910003e1 	add	x1, sp, #0x0
   a4860:	72a00022 	movk	w2, #0x1, lsl #16
   a4864:	72a00035 	movk	w21, #0x1, lsl #16
   a4868:	f90007e8 	str	x8, [sp, #8]
   a486c:	f9400008 	ldr	x8, [x0]
   a4870:	f90003ff 	str	xzr, [sp]
   a4874:	f9401908 	ldr	x8, [x8, #48]
   a4878:	d63f0100 	blr	x8
   a487c:	34000060 	cbz	w0, a4888 <JNI_OnLoad@@Base+0x50>
   a4880:	12800000 	movn	w0, #0x0
   a4884:	1400001e 	b	a48fc <JNI_OnLoad@@Base+0xc4>
   a4888:	f94003e0 	ldr	x0, [sp]
   a488c:	97fffcba 	bl	a3b74 <JNI_OnUnload@@Base+0x6eb94>
   a4890:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a4894:	528000e9 	movz	w9, #0x7
   a4898:	b9447108 	ldr	w8, [x8, #1136]
   a489c:	6a28013f 	bics	wzr, w9, w8
   a48a0:	54000040 	b.eq	a48a8 <JNI_OnLoad@@Base+0x70>  // b.none
   a48a4:	94000bb1 	bl	a7768 <JNI_OnLoad@@Base+0x2f30>
   a48a8:	f94003f3 	ldr	x19, [sp]
   a48ac:	f0000088 	adrp	x8, b7000 <DPT_UNKNOWN_DATA@@Base+0xb77>
   a48b0:	910fc108 	add	x8, x8, #0x3f0
   a48b4:	39400109 	ldrb	w9, [x8]
   a48b8:	f940090b 	ldr	x11, [x8, #16]
   a48bc:	f940026a 	ldr	x10, [x19]
   a48c0:	aa1303e0 	orr	x0, xzr, x19
   a48c4:	7200013f 	ands	wzr, w9, #0x1
   a48c8:	f9401949 	ldr	x9, [x10, #48]
   a48cc:	9a881561 	csinc	x1, x11, x8, ne	// ne = any
   a48d0:	d63f0120 	blr	x9
   a48d4:	f9400268 	ldr	x8, [x19]
   a48d8:	aa0003e1 	orr	x1, xzr, x0
   a48dc:	d503201f 	hint	#0x0
   a48e0:	1008de42 	adr	x2, b64a8 <DPT_UNKNOWN_DATA@@Base+0x1f>
   a48e4:	aa1303e0 	orr	x0, xzr, x19
   a48e8:	52800143 	movz	w3, #0xa
   a48ec:	f9435d08 	ldr	x8, [x8, #1720]
   a48f0:	d63f0100 	blr	x8
   a48f4:	7100001f 	subs	wzr, w0, #0x0
   a48f8:	5a9f02a0 	csinv	w0, w21, wzr, eq	// eq = none
   a48fc:	f9401688 	ldr	x8, [x20, #40]
   a4900:	f94007e9 	ldr	x9, [sp, #8]
   a4904:	eb09011f 	subs	xzr, x8, x9
   a4908:	540000c1 	b.ne	a4920 <JNI_OnLoad@@Base+0xe8>  // b.any
   a490c:	a9434ff4 	ldp	x20, x19, [sp, #48]
   a4910:	f94013f5 	ldr	x21, [sp, #32]
   a4914:	a9417bfd 	ldp	x29, x30, [sp, #16]
   a4918:	910103ff 	add	sp, sp, #0x40
   a491c:	d65f03c0 	ret
   a4920:	94000e2c 	bl	a81d0 <__stack_chk_fail@plt>
   a4924:	900000a8 	adrp	x8, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a4928:	90fffba9 	adrp	x9, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a492c:	912cd129 	add	x9, x9, #0xb34
   a4930:	b9485908 	ldr	w8, [x8, #2136]
   a4934:	b0fffb8a 	adrp	x10, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a4938:	9122514a 	add	x10, x10, #0x894
   a493c:	90fffba1 	adrp	x1, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a4940:	9123f821 	add	x1, x1, #0x8fe
   a4944:	f0fffb82 	adrp	x2, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a4948:	91233c42 	add	x2, x2, #0x8cf
   a494c:	7100711f 	subs	wzr, w8, #0x1c
   a4950:	d503201f 	hint	#0x0
   a4954:	10008643 	adr	x3, a5a1c <JNI_OnLoad@@Base+0x11e4>
   a4958:	9a89c140 	csel	x0, x10, x9, gt
   a495c:	aa1f03e4 	orr	x4, xzr, xzr
   a4960:	aa1f03e5 	orr	x5, xzr, xzr
   a4964:	17feab4e 	b	4f69c <JNI_OnUnload@@Base+0x1a6bc>
   a4968:	900000a8 	adrp	x8, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a496c:	90fffba9 	adrp	x9, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a4970:	912cd129 	add	x9, x9, #0xb34
   a4974:	b9485908 	ldr	w8, [x8, #2136]
   a4978:	b0fffb8a 	adrp	x10, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a497c:	9122514a 	add	x10, x10, #0x894
   a4980:	90fffba1 	adrp	x1, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a4984:	9123f821 	add	x1, x1, #0x8fe
   a4988:	d0fffb82 	adrp	x2, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a498c:	9118e042 	add	x2, x2, #0x638
   a4990:	7100711f 	subs	wzr, w8, #0x1c
   a4994:	d503201f 	hint	#0x0
   a4998:	10007363 	adr	x3, a5804 <JNI_OnLoad@@Base+0xfcc>
   a499c:	9a89c140 	csel	x0, x10, x9, gt
   a49a0:	aa1f03e4 	orr	x4, xzr, xzr
   a49a4:	aa1f03e5 	orr	x5, xzr, xzr
   a49a8:	17feab3d 	b	4f69c <JNI_OnUnload@@Base+0x1a6bc>
   a49ac:	90fffba0 	adrp	x0, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a49b0:	9123f800 	add	x0, x0, #0x8fe
   a49b4:	b0fffb81 	adrp	x1, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a49b8:	913bc021 	add	x1, x1, #0xef0
   a49bc:	d503201f 	hint	#0x0
   a49c0:	10008a42 	adr	x2, a5b08 <JNI_OnLoad@@Base+0x12d0>
   a49c4:	aa1f03e3 	orr	x3, xzr, xzr
   a49c8:	aa1f03e4 	orr	x4, xzr, xzr
   a49cc:	17feab39 	b	4f6b0 <JNI_OnUnload@@Base+0x1a6d0>
   a49d0:	d10503ff 	sub	sp, sp, #0x140
   a49d4:	a9117bfd 	stp	x29, x30, [sp, #272]
   a49d8:	f90093fc 	str	x28, [sp, #288]
   a49dc:	a9134ff4 	stp	x20, x19, [sp, #304]
   a49e0:	910443fd 	add	x29, sp, #0x110
   a49e4:	d53bd054 	mrs	x20, tpidr_el0
   a49e8:	f9401688 	ldr	x8, [x20, #40]
   a49ec:	f81f83a8 	stur	x8, [x29, #-8]
   a49f0:	97fe7204 	bl	41200 <JNI_OnUnload@@Base+0xc220>
   a49f4:	6f00e400 	movi	v0.2d, #0x0
   a49f8:	d0fffb84 	adrp	x4, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a49fc:	912f4884 	add	x4, x4, #0xbd2
   a4a00:	b0fffb85 	adrp	x5, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a4a04:	913b90a5 	add	x5, x5, #0xee4
   a4a08:	910003e1 	add	x1, sp, #0x0
   a4a0c:	52802002 	movz	w2, #0x100
   a4a10:	52800043 	movz	w3, #0x2
   a4a14:	aa0003f3 	orr	x19, xzr, x0
   a4a18:	ad0003e0 	stp	q0, q0, [sp]
   a4a1c:	ad0103e0 	stp	q0, q0, [sp, #32]
   a4a20:	ad0203e0 	stp	q0, q0, [sp, #64]
   a4a24:	ad0303e0 	stp	q0, q0, [sp, #96]
   a4a28:	ad0403e0 	stp	q0, q0, [sp, #128]
   a4a2c:	ad0503e0 	stp	q0, q0, [sp, #160]
   a4a30:	ad0603e0 	stp	q0, q0, [sp, #192]
   a4a34:	ad0703e0 	stp	q0, q0, [sp, #224]
   a4a38:	940005a8 	bl	a60d8 <JNI_OnLoad@@Base+0x18a0>
   a4a3c:	910003e0 	add	x0, sp, #0x0
   a4a40:	52802001 	movz	w1, #0x100
   a4a44:	94000e37 	bl	a8320 <__strlen_chk@plt>
   a4a48:	b40002a0 	cbz	x0, a4a9c <JNI_OnLoad@@Base+0x264>
   a4a4c:	910003e1 	add	x1, sp, #0x0
   a4a50:	aa1303e0 	orr	x0, xzr, x19
   a4a54:	97fea9af 	bl	4f110 <JNI_OnUnload@@Base+0x1a130>
   a4a58:	b4000220 	cbz	x0, a4a9c <JNI_OnLoad@@Base+0x264>
   a4a5c:	900000a8 	adrp	x8, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a4a60:	b9485908 	ldr	w8, [x8, #2136]
   a4a64:	7100591f 	subs	wzr, w8, #0x16
   a4a68:	540000cb 	b.lt	a4a80 <JNI_OnLoad@@Base+0x248>  // b.tstop
   a4a6c:	d503201f 	hint	#0x0
   a4a70:	10006341 	adr	x1, a56d8 <JNI_OnLoad@@Base+0xea0>
   a4a74:	d503201f 	hint	#0x0
   a4a78:	1009f0c2 	adr	x2, b8890 <DPT_UNKNOWN_DATA@@Base+0x2407>
   a4a7c:	14000005 	b	a4a90 <JNI_OnLoad@@Base+0x258>
   a4a80:	d503201f 	hint	#0x0
   a4a84:	100067c1 	adr	x1, a577c <JNI_OnLoad@@Base+0xf44>
   a4a88:	d503201f 	hint	#0x0
   a4a8c:	1009f062 	adr	x2, b8898 <DPT_UNKNOWN_DATA@@Base+0x240f>
   a4a90:	97fe9c1e 	bl	4bb08 <JNI_OnUnload@@Base+0x16b28>
   a4a94:	7100001f 	subs	wzr, w0, #0x0
   a4a98:	1a9f17e0 	csinc	w0, wzr, wzr, ne	// ne = any
   a4a9c:	f9401688 	ldr	x8, [x20, #40]
   a4aa0:	f85f83a9 	ldur	x9, [x29, #-8]
   a4aa4:	eb09011f 	subs	xzr, x8, x9
   a4aa8:	540000c1 	b.ne	a4ac0 <JNI_OnLoad@@Base+0x288>  // b.any
   a4aac:	a9534ff4 	ldp	x20, x19, [sp, #304]
   a4ab0:	f94093fc 	ldr	x28, [sp, #288]
   a4ab4:	a9517bfd 	ldp	x29, x30, [sp, #272]
   a4ab8:	910503ff 	add	sp, sp, #0x140
   a4abc:	d65f03c0 	ret
   a4ac0:	94000dc4 	bl	a81d0 <__stack_chk_fail@plt>
   a4ac4:	d10503ff 	sub	sp, sp, #0x140
   a4ac8:	a9117bfd 	stp	x29, x30, [sp, #272]
   a4acc:	f90093fc 	str	x28, [sp, #288]
   a4ad0:	a9134ff4 	stp	x20, x19, [sp, #304]
   a4ad4:	910443fd 	add	x29, sp, #0x110
   a4ad8:	d53bd054 	mrs	x20, tpidr_el0
   a4adc:	f9401688 	ldr	x8, [x20, #40]
   a4ae0:	f81f83a8 	stur	x8, [x29, #-8]
   a4ae4:	900000a8 	adrp	x8, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a4ae8:	b9485908 	ldr	w8, [x8, #2136]
   a4aec:	71005d1f 	subs	wzr, w8, #0x17
   a4af0:	5400006a 	b.ge	a4afc <JNI_OnLoad@@Base+0x2c4>  // b.tcont
   a4af4:	2a1f03e0 	orr	w0, wzr, wzr
   a4af8:	14000023 	b	a4b84 <JNI_OnLoad@@Base+0x34c>
   a4afc:	97fe71c1 	bl	41200 <JNI_OnUnload@@Base+0xc220>
   a4b00:	6f00e400 	movi	v0.2d, #0x0
   a4b04:	d0fffb84 	adrp	x4, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a4b08:	912f4884 	add	x4, x4, #0xbd2
   a4b0c:	f0fffb85 	adrp	x5, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a4b10:	912c9ca5 	add	x5, x5, #0xb27
   a4b14:	910003e1 	add	x1, sp, #0x0
   a4b18:	52802002 	movz	w2, #0x100
   a4b1c:	52800043 	movz	w3, #0x2
   a4b20:	aa0003f3 	orr	x19, xzr, x0
   a4b24:	ad0003e0 	stp	q0, q0, [sp]
   a4b28:	ad0103e0 	stp	q0, q0, [sp, #32]
   a4b2c:	ad0203e0 	stp	q0, q0, [sp, #64]
   a4b30:	ad0303e0 	stp	q0, q0, [sp, #96]
   a4b34:	ad0403e0 	stp	q0, q0, [sp, #128]
   a4b38:	ad0503e0 	stp	q0, q0, [sp, #160]
   a4b3c:	ad0603e0 	stp	q0, q0, [sp, #192]
   a4b40:	ad0703e0 	stp	q0, q0, [sp, #224]
   a4b44:	94000565 	bl	a60d8 <JNI_OnLoad@@Base+0x18a0>
   a4b48:	910003e0 	add	x0, sp, #0x0
   a4b4c:	52802001 	movz	w1, #0x100
   a4b50:	94000df4 	bl	a8320 <__strlen_chk@plt>
   a4b54:	b4000180 	cbz	x0, a4b84 <JNI_OnLoad@@Base+0x34c>
   a4b58:	910003e1 	add	x1, sp, #0x0
   a4b5c:	aa1303e0 	orr	x0, xzr, x19
   a4b60:	97fea96c 	bl	4f110 <JNI_OnUnload@@Base+0x1a130>
   a4b64:	b4000100 	cbz	x0, a4b84 <JNI_OnLoad@@Base+0x34c>
   a4b68:	d503201f 	hint	#0x0
   a4b6c:	10005261 	adr	x1, a55b8 <JNI_OnLoad@@Base+0xd80>
   a4b70:	d503201f 	hint	#0x0
   a4b74:	1009e8a2 	adr	x2, b8888 <DPT_UNKNOWN_DATA@@Base+0x23ff>
   a4b78:	97fe9be4 	bl	4bb08 <JNI_OnUnload@@Base+0x16b28>
   a4b7c:	7100001f 	subs	wzr, w0, #0x0
   a4b80:	1a9f17e0 	csinc	w0, wzr, wzr, ne	// ne = any
   a4b84:	f9401688 	ldr	x8, [x20, #40]
   a4b88:	f85f83a9 	ldur	x9, [x29, #-8]
   a4b8c:	eb09011f 	subs	xzr, x8, x9
   a4b90:	540000c1 	b.ne	a4ba8 <JNI_OnLoad@@Base+0x370>  // b.any
   a4b94:	a9534ff4 	ldp	x20, x19, [sp, #304]
   a4b98:	f94093fc 	ldr	x28, [sp, #288]
   a4b9c:	a9517bfd 	ldp	x29, x30, [sp, #272]
   a4ba0:	910503ff 	add	sp, sp, #0x140
   a4ba4:	d65f03c0 	ret
   a4ba8:	94000d8a 	bl	a81d0 <__stack_chk_fail@plt>
   a4bac:	a9ba7bfd 	stp	x29, x30, [sp, #-96]!
   a4bb0:	a9016ffc 	stp	x28, x27, [sp, #16]
   a4bb4:	a90267fa 	stp	x26, x25, [sp, #32]
   a4bb8:	a9035ff8 	stp	x24, x23, [sp, #48]
   a4bbc:	a90457f6 	stp	x22, x21, [sp, #64]
   a4bc0:	a9054ff4 	stp	x20, x19, [sp, #80]
   a4bc4:	910003fd 	add	x29, sp, #0x0
   a4bc8:	d106c3ff 	sub	sp, sp, #0x1b0
   a4bcc:	d53bd05b 	mrs	x27, tpidr_el0
   a4bd0:	b000006b 	adrp	x11, b1000 <_ZTVNSt6__ndk117bad_function_callE@@Base+0x42b8>
   a4bd4:	aa0203f6 	orr	x22, xzr, x2
   a4bd8:	f9401768 	ldr	x8, [x27, #40]
   a4bdc:	f945b16b 	ldr	x11, [x11, #2912]
   a4be0:	aa0103f5 	orr	x21, xzr, x1
   a4be4:	aa0003f3 	orr	x19, xzr, x0
   a4be8:	f81f03a8 	stur	x8, [x29, #-16]
   a4bec:	3941e168 	ldrb	w8, [x11, #120]
   a4bf0:	f9404169 	ldr	x9, [x11, #128]
   a4bf4:	d341fd0a 	ubfm	x10, x8, #1, #63
   a4bf8:	7200011f 	ands	wzr, w8, #0x1
   a4bfc:	9a890149 	csel	x9, x10, x9, eq	// eq = none
   a4c00:	b40000c9 	cbz	x9, a4c18 <JNI_OnLoad@@Base+0x3e0>
   a4c04:	f9404569 	ldr	x9, [x11, #136]
   a4c08:	9101e56a 	add	x10, x11, #0x79
   a4c0c:	7200011f 	ands	wzr, w8, #0x1
   a4c10:	9a890141 	csel	x1, x10, x9, eq	// eq = none
   a4c14:	1400001d 	b	a4c88 <JNI_OnLoad@@Base+0x450>
   a4c18:	d503201f 	hint	#0x0
   a4c1c:	10ba2669 	adr	x9, 190e8 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x1fe>
   a4c20:	52800d68 	movz	w8, #0x6b
   a4c24:	ad400520 	ldp	q0, q1, [x9]
   a4c28:	d503201f 	hint	#0x0
   a4c2c:	1008e2a0 	adr	x0, b6880 <DPT_UNKNOWN_DATA@@Base+0x3f7>
   a4c30:	390203e8 	strb	w8, [sp, #128]
   a4c34:	ad0307e0 	stp	q0, q1, [sp, #96]
   a4c38:	97ffe469 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a4c3c:	39400008 	ldrb	w8, [x0]
   a4c40:	36004348 	tbz	w8, #0, a54a8 <JNI_OnLoad@@Base+0xc70>
   a4c44:	d503201f 	hint	#0x0
   a4c48:	1008e0c0 	adr	x0, b6860 <DPT_UNKNOWN_DATA@@Base+0x3d7>
   a4c4c:	97ffe464 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a4c50:	39408408 	ldrb	w8, [x0, #33]
   a4c54:	aa0003e1 	orr	x1, xzr, x0
   a4c58:	34000188 	cbz	w8, a4c88 <JNI_OnLoad@@Base+0x450>
   a4c5c:	90fffb88 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a4c60:	ad400821 	ldp	q1, q2, [x1]
   a4c64:	3dc35d00 	ldr	q0, [x8, #3440]
   a4c68:	39408028 	ldrb	w8, [x1, #32]
   a4c6c:	52800d69 	movz	w9, #0x6b
   a4c70:	3900843f 	strb	wzr, [x1, #33]
   a4c74:	6e201c21 	eor	v1.16b, v1.16b, v0.16b
   a4c78:	6e201c40 	eor	v0.16b, v2.16b, v0.16b
   a4c7c:	4a090108 	eor	w8, w8, w9
   a4c80:	39008028 	strb	w8, [x1, #32]
   a4c84:	ad000021 	stp	q1, q0, [x1]
   a4c88:	b4000093 	cbz	x19, a4c98 <JNI_OnLoad@@Base+0x460>
   a4c8c:	aa1303e0 	orr	x0, xzr, x19
   a4c90:	97fe835c 	bl	45a00 <JNI_OnUnload@@Base+0x10a20>
   a4c94:	b50042e0 	cbnz	x0, a54f0 <JNI_OnLoad@@Base+0xcb8>
   a4c98:	b4003d75 	cbz	x21, a5444 <JNI_OnLoad@@Base+0xc0c>
   a4c9c:	900000a8 	adrp	x8, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a4ca0:	a9047fff 	stp	xzr, xzr, [sp, #64]
   a4ca4:	b9485908 	ldr	w8, [x8, #2136]
   a4ca8:	f9002bff 	str	xzr, [sp, #80]
   a4cac:	71008d1f 	subs	wzr, w8, #0x23
   a4cb0:	5400018b 	b.lt	a4ce0 <JNI_OnLoad@@Base+0x4a8>  // b.tstop
   a4cb4:	9100a2a8 	add	x8, x21, #0x28
   a4cb8:	910103e9 	add	x9, sp, #0x40
   a4cbc:	eb08013f 	subs	xzr, x9, x8
   a4cc0:	54000460 	b.eq	a4d4c <JNI_OnLoad@@Base+0x514>  // b.none
   a4cc4:	39400109 	ldrb	w9, [x8]
   a4cc8:	370003c9 	tbnz	w9, #0, a4d40 <JNI_OnLoad@@Base+0x508>
   a4ccc:	3dc00100 	ldr	q0, [x8]
   a4cd0:	f9400908 	ldr	x8, [x8, #16]
   a4cd4:	3d8013e0 	str	q0, [sp, #64]
   a4cd8:	f9002be8 	str	x8, [sp, #80]
   a4cdc:	1400001c 	b	a4d4c <JNI_OnLoad@@Base+0x514>
   a4ce0:	7100711f 	subs	wzr, w8, #0x1c
   a4ce4:	5400018b 	b.lt	a4d14 <JNI_OnLoad@@Base+0x4dc>  // b.tstop
   a4ce8:	9100a2a8 	add	x8, x21, #0x28
   a4cec:	910103e9 	add	x9, sp, #0x40
   a4cf0:	eb08013f 	subs	xzr, x9, x8
   a4cf4:	54003c60 	b.eq	a5480 <JNI_OnLoad@@Base+0xc48>  // b.none
   a4cf8:	39400109 	ldrb	w9, [x8]
   a4cfc:	37003bc9 	tbnz	w9, #0, a5474 <JNI_OnLoad@@Base+0xc3c>
   a4d00:	3dc00100 	ldr	q0, [x8]
   a4d04:	f9400908 	ldr	x8, [x8, #16]
   a4d08:	3d8013e0 	str	q0, [sp, #64]
   a4d0c:	f9002be8 	str	x8, [sp, #80]
   a4d10:	140001dc 	b	a5480 <JNI_OnLoad@@Base+0xc48>
   a4d14:	910062a8 	add	x8, x21, #0x18
   a4d18:	910103e9 	add	x9, sp, #0x40
   a4d1c:	eb08013f 	subs	xzr, x9, x8
   a4d20:	54003bc0 	b.eq	a5498 <JNI_OnLoad@@Base+0xc60>  // b.none
   a4d24:	39400109 	ldrb	w9, [x8]
   a4d28:	37003b29 	tbnz	w9, #0, a548c <JNI_OnLoad@@Base+0xc54>
   a4d2c:	3dc00100 	ldr	q0, [x8]
   a4d30:	f9400908 	ldr	x8, [x8, #16]
   a4d34:	3d8013e0 	str	q0, [sp, #64]
   a4d38:	f9002be8 	str	x8, [sp, #80]
   a4d3c:	140001d7 	b	a5498 <JNI_OnLoad@@Base+0xc60>
   a4d40:	a94306a2 	ldp	x2, x1, [x21, #48]
   a4d44:	910103e0 	add	x0, sp, #0x40
   a4d48:	97fe4134 	bl	35218 <JNI_OnUnload@@Base+0x238>
   a4d4c:	f94006bc 	ldr	x28, [x21, #8]
   a4d50:	910122a8 	add	x8, x21, #0x48
   a4d54:	f9400108 	ldr	x8, [x8]
   a4d58:	b9402114 	ldr	w20, [x8, #32]
   a4d5c:	394103e8 	ldrb	w8, [sp, #64]
   a4d60:	a944afe9 	ldp	x9, x11, [sp, #72]
   a4d64:	910103ec 	add	x12, sp, #0x40
   a4d68:	d341fd0a 	ubfm	x10, x8, #1, #63
   a4d6c:	7200011f 	ands	wzr, w8, #0x1
   a4d70:	b2400188 	orr	x8, x12, #0x1
   a4d74:	9a8b0108 	csel	x8, x8, x11, eq	// eq = none
   a4d78:	9a890149 	csel	x9, x10, x9, eq	// eq = none
   a4d7c:	b40035c9 	cbz	x9, a5434 <JNI_OnLoad@@Base+0xbfc>
   a4d80:	8b09010a 	add	x10, x8, x9
   a4d84:	aa0803ec 	orr	x12, xzr, x8
   a4d88:	aa0a03e9 	orr	x9, xzr, x10
   a4d8c:	9100358b 	add	x11, x12, #0xd
   a4d90:	385f316c 	ldurb	w12, [x11, #-13]
   a4d94:	7101a59f 	subs	wzr, w12, #0x69
   a4d98:	d100316c 	sub	x12, x11, #0xc
   a4d9c:	540000a0 	b.eq	a4db0 <JNI_OnLoad@@Base+0x578>  // b.none
   a4da0:	eb0a019f 	subs	xzr, x12, x10
   a4da4:	9100056b 	add	x11, x11, #0x1
   a4da8:	54ffff41 	b.ne	a4d90 <JNI_OnLoad@@Base+0x558>  // b.any
   a4dac:	1400004e 	b	a4ee4 <JNI_OnLoad@@Base+0x6ac>
   a4db0:	eb0a019f 	subs	xzr, x12, x10
   a4db4:	54000980 	b.eq	a4ee4 <JNI_OnLoad@@Base+0x6ac>  // b.none
   a4db8:	3940018d 	ldrb	w13, [x12]
   a4dbc:	7100c5bf 	subs	wzr, w13, #0x31
   a4dc0:	54fffe61 	b.ne	a4d8c <JNI_OnLoad@@Base+0x554>  // b.any
   a4dc4:	d1002d6d 	sub	x13, x11, #0xb
   a4dc8:	eb0a01bf 	subs	xzr, x13, x10
   a4dcc:	540008c0 	b.eq	a4ee4 <JNI_OnLoad@@Base+0x6ac>  // b.none
   a4dd0:	394001ad 	ldrb	w13, [x13]
   a4dd4:	7100c5bf 	subs	wzr, w13, #0x31
   a4dd8:	54fffda1 	b.ne	a4d8c <JNI_OnLoad@@Base+0x554>  // b.any
   a4ddc:	d100296d 	sub	x13, x11, #0xa
   a4de0:	eb0a01bf 	subs	xzr, x13, x10
   a4de4:	54000800 	b.eq	a4ee4 <JNI_OnLoad@@Base+0x6ac>  // b.none
   a4de8:	394001ad 	ldrb	w13, [x13]
   a4dec:	7100c5bf 	subs	wzr, w13, #0x31
   a4df0:	54fffce1 	b.ne	a4d8c <JNI_OnLoad@@Base+0x554>  // b.any
   a4df4:	d100256d 	sub	x13, x11, #0x9
   a4df8:	eb0a01bf 	subs	xzr, x13, x10
   a4dfc:	54000740 	b.eq	a4ee4 <JNI_OnLoad@@Base+0x6ac>  // b.none
   a4e00:	394001ad 	ldrb	w13, [x13]
   a4e04:	7100c5bf 	subs	wzr, w13, #0x31
   a4e08:	54fffc21 	b.ne	a4d8c <JNI_OnLoad@@Base+0x554>  // b.any
   a4e0c:	d100216d 	sub	x13, x11, #0x8
   a4e10:	eb0a01bf 	subs	xzr, x13, x10
   a4e14:	54000680 	b.eq	a4ee4 <JNI_OnLoad@@Base+0x6ac>  // b.none
   a4e18:	394001ad 	ldrb	w13, [x13]
   a4e1c:	7100c5bf 	subs	wzr, w13, #0x31
   a4e20:	54fffb61 	b.ne	a4d8c <JNI_OnLoad@@Base+0x554>  // b.any
   a4e24:	d1001d6d 	sub	x13, x11, #0x7
   a4e28:	eb0a01bf 	subs	xzr, x13, x10
   a4e2c:	540005c0 	b.eq	a4ee4 <JNI_OnLoad@@Base+0x6ac>  // b.none
   a4e30:	394001ad 	ldrb	w13, [x13]
   a4e34:	7101a5bf 	subs	wzr, w13, #0x69
   a4e38:	54fffaa1 	b.ne	a4d8c <JNI_OnLoad@@Base+0x554>  // b.any
   a4e3c:	d100196d 	sub	x13, x11, #0x6
   a4e40:	eb0a01bf 	subs	xzr, x13, x10
   a4e44:	54000500 	b.eq	a4ee4 <JNI_OnLoad@@Base+0x6ac>  // b.none
   a4e48:	394001ad 	ldrb	w13, [x13]
   a4e4c:	7100c5bf 	subs	wzr, w13, #0x31
   a4e50:	54fff9e1 	b.ne	a4d8c <JNI_OnLoad@@Base+0x554>  // b.any
   a4e54:	d100156d 	sub	x13, x11, #0x5
   a4e58:	eb0a01bf 	subs	xzr, x13, x10
   a4e5c:	54000440 	b.eq	a4ee4 <JNI_OnLoad@@Base+0x6ac>  // b.none
   a4e60:	394001ad 	ldrb	w13, [x13]
   a4e64:	7100c5bf 	subs	wzr, w13, #0x31
   a4e68:	54fff921 	b.ne	a4d8c <JNI_OnLoad@@Base+0x554>  // b.any
   a4e6c:	d100116d 	sub	x13, x11, #0x4
   a4e70:	eb0a01bf 	subs	xzr, x13, x10
   a4e74:	54000380 	b.eq	a4ee4 <JNI_OnLoad@@Base+0x6ac>  // b.none
   a4e78:	394001ad 	ldrb	w13, [x13]
   a4e7c:	7100c5bf 	subs	wzr, w13, #0x31
   a4e80:	54fff861 	b.ne	a4d8c <JNI_OnLoad@@Base+0x554>  // b.any
   a4e84:	d1000d6d 	sub	x13, x11, #0x3
   a4e88:	eb0a01bf 	subs	xzr, x13, x10
   a4e8c:	540002c0 	b.eq	a4ee4 <JNI_OnLoad@@Base+0x6ac>  // b.none
   a4e90:	394001ad 	ldrb	w13, [x13]
   a4e94:	7100b9bf 	subs	wzr, w13, #0x2e
   a4e98:	54fff7a1 	b.ne	a4d8c <JNI_OnLoad@@Base+0x554>  // b.any
   a4e9c:	d100096d 	sub	x13, x11, #0x2
   a4ea0:	eb0a01bf 	subs	xzr, x13, x10
   a4ea4:	54000200 	b.eq	a4ee4 <JNI_OnLoad@@Base+0x6ac>  // b.none
   a4ea8:	394001ad 	ldrb	w13, [x13]
   a4eac:	7101e9bf 	subs	wzr, w13, #0x7a
   a4eb0:	54fff6e1 	b.ne	a4d8c <JNI_OnLoad@@Base+0x554>  // b.any
   a4eb4:	d100056d 	sub	x13, x11, #0x1
   a4eb8:	eb0a01bf 	subs	xzr, x13, x10
   a4ebc:	54000140 	b.eq	a4ee4 <JNI_OnLoad@@Base+0x6ac>  // b.none
   a4ec0:	394001ad 	ldrb	w13, [x13]
   a4ec4:	7101a5bf 	subs	wzr, w13, #0x69
   a4ec8:	54fff621 	b.ne	a4d8c <JNI_OnLoad@@Base+0x554>  // b.any
   a4ecc:	eb0a017f 	subs	xzr, x11, x10
   a4ed0:	540000a0 	b.eq	a4ee4 <JNI_OnLoad@@Base+0x6ac>  // b.none
   a4ed4:	385f356d 	ldrb	w13, [x11], #-13
   a4ed8:	7101c1bf 	subs	wzr, w13, #0x70
   a4edc:	9a890169 	csel	x9, x11, x9, eq	// eq = none
   a4ee0:	17ffffab 	b	a4d8c <JNI_OnLoad@@Base+0x554>
   a4ee4:	b4002a96 	cbz	x22, a5434 <JNI_OnLoad@@Base+0xbfc>
   a4ee8:	eb0a013f 	subs	xzr, x9, x10
   a4eec:	54002a40 	b.eq	a5434 <JNI_OnLoad@@Base+0xbfc>  // b.none
   a4ef0:	cb080128 	sub	x8, x9, x8
   a4ef4:	b100051f 	adds	xzr, x8, #0x1
   a4ef8:	540029e0 	b.eq	a5434 <JNI_OnLoad@@Base+0xbfc>  // b.none
   a4efc:	910103e0 	add	x0, sp, #0x40
   a4f00:	f9000bfb 	str	x27, [sp, #16]
   a4f04:	97fe77c9 	bl	42e28 <JNI_OnUnload@@Base+0xde48>
   a4f08:	b9401ac8 	ldr	w8, [x22, #24]
   a4f0c:	34002948 	cbz	w8, a5434 <JNI_OnLoad@@Base+0xbfc>
   a4f10:	2a0003f5 	orr	w21, wzr, w0
   a4f14:	8b080396 	add	x22, x28, x8
   a4f18:	f9001fff 	str	xzr, [sp, #56]
   a4f1c:	9100e3e1 	add	x1, sp, #0x38
   a4f20:	aa1603e0 	orr	x0, xzr, x22
   a4f24:	97fe82ef 	bl	45ae0 <JNI_OnUnload@@Base+0x10b00>
   a4f28:	aa0003f7 	orr	x23, xzr, x0
   a4f2c:	f9001bff 	str	xzr, [sp, #48]
   a4f30:	8b0002c0 	add	x0, x22, x0
   a4f34:	9100c3e1 	add	x1, sp, #0x30
   a4f38:	97fe82ea 	bl	45ae0 <JNI_OnUnload@@Base+0x10b00>
   a4f3c:	8b170013 	add	x19, x0, x23
   a4f40:	f90017ff 	str	xzr, [sp, #40]
   a4f44:	8b1302c0 	add	x0, x22, x19
   a4f48:	9100a3e1 	add	x1, sp, #0x28
   a4f4c:	97fe82e5 	bl	45ae0 <JNI_OnUnload@@Base+0x10b00>
   a4f50:	8b130013 	add	x19, x0, x19
   a4f54:	f90007f4 	str	x20, [sp, #8]
   a4f58:	f90013ff 	str	xzr, [sp, #32]
   a4f5c:	8b1302c0 	add	x0, x22, x19
   a4f60:	910083e1 	add	x1, sp, #0x20
   a4f64:	97fe82df 	bl	45ae0 <JNI_OnUnload@@Base+0x10b00>
   a4f68:	f9401fe1 	ldr	x1, [sp, #56]
   a4f6c:	8b130013 	add	x19, x0, x19
   a4f70:	8b1302c0 	add	x0, x22, x19
   a4f74:	97fe82fd 	bl	45b68 <JNI_OnUnload@@Base+0x10b88>
   a4f78:	8b0002c8 	add	x8, x22, x0
   a4f7c:	f9401be1 	ldr	x1, [sp, #48]
   a4f80:	8b130117 	add	x23, x8, x19
   a4f84:	aa1703e0 	orr	x0, xzr, x23
   a4f88:	97fe82f8 	bl	45b68 <JNI_OnUnload@@Base+0x10b88>
   a4f8c:	f94017f9 	ldr	x25, [sp, #40]
   a4f90:	52800188 	movz	w8, #0xc
   a4f94:	aa0003f8 	orr	x24, xzr, x0
   a4f98:	9bc87f28 	umulh	x8, x25, x8
   a4f9c:	8b190729 	add	x9, x25, x25, lsl #1
   a4fa0:	d37ef534 	ubfm	x20, x9, #62, #61
   a4fa4:	eb0803ff 	subs	xzr, xzr, x8
   a4fa8:	da9f0280 	csinv	x0, x20, xzr, eq	// eq = none
   a4fac:	97ffde58 	bl	9c90c <JNI_OnUnload@@Base+0x6792c>
   a4fb0:	aa0003f6 	orr	x22, xzr, x0
   a4fb4:	d2800193 	movz	x19, #0xc
   a4fb8:	b4000179 	cbz	x25, a4fe4 <JNI_OnLoad@@Base+0x7ac>
   a4fbc:	b201f3e9 	orr	x9, xzr, #0xaaaaaaaaaaaaaaaa
   a4fc0:	d1003288 	sub	x8, x20, #0xc
   a4fc4:	aa1603e0 	orr	x0, xzr, x22
   a4fc8:	f2955569 	movk	x9, #0xaaab
   a4fcc:	2a1f03e1 	orr	w1, wzr, wzr
   a4fd0:	9bc97d08 	umulh	x8, x8, x9
   a4fd4:	52800189 	movz	w9, #0xc
   a4fd8:	d343fd08 	ubfm	x8, x8, #3, #63
   a4fdc:	9b094d02 	madd	x2, x8, x9, x19
   a4fe0:	94000cb0 	bl	a82a0 <memset@plt>
   a4fe4:	8b1802f8 	add	x24, x23, x24
   a4fe8:	aa1803e0 	orr	x0, xzr, x24
   a4fec:	aa1603e1 	orr	x1, xzr, x22
   a4ff0:	aa1903e2 	orr	x2, xzr, x25
   a4ff4:	97fe8307 	bl	45c10 <JNI_OnUnload@@Base+0x10c30>
   a4ff8:	f94013fa 	ldr	x26, [sp, #32]
   a4ffc:	52800188 	movz	w8, #0xc
   a5000:	aa0003f9 	orr	x25, xzr, x0
   a5004:	9bc87f48 	umulh	x8, x26, x8
   a5008:	8b1a0749 	add	x9, x26, x26, lsl #1
   a500c:	d37ef529 	ubfm	x9, x9, #62, #61
   a5010:	eb0803ff 	subs	xzr, xzr, x8
   a5014:	da9f0120 	csinv	x0, x9, xzr, eq	// eq = none
   a5018:	97ffde3d 	bl	9c90c <JNI_OnUnload@@Base+0x6792c>
   a501c:	aa0003f7 	orr	x23, xzr, x0
   a5020:	b400019a 	cbz	x26, a5050 <JNI_OnLoad@@Base+0x818>
   a5024:	52800188 	movz	w8, #0xc
   a5028:	92800169 	movn	x9, #0xb
   a502c:	b201f3ea 	orr	x10, xzr, #0xaaaaaaaaaaaaaaaa
   a5030:	9b082749 	madd	x9, x26, x8, x9
   a5034:	f295556a 	movk	x10, #0xaaab
   a5038:	aa1703e0 	orr	x0, xzr, x23
   a503c:	2a1f03e1 	orr	w1, wzr, wzr
   a5040:	9bca7d29 	umulh	x9, x9, x10
   a5044:	d343fd29 	ubfm	x9, x9, #3, #63
   a5048:	9b084d22 	madd	x2, x9, x8, x19
   a504c:	94000c95 	bl	a82a0 <memset@plt>
   a5050:	8b190300 	add	x0, x24, x25
   a5054:	aa1703e1 	orr	x1, xzr, x23
   a5058:	aa1a03e2 	orr	x2, xzr, x26
   a505c:	97fe82ed 	bl	45c10 <JNI_OnUnload@@Base+0x10c30>
   a5060:	90000073 	adrp	x19, b1000 <_ZTVNSt6__ndk117bad_function_callE@@Base+0x42b8>
   a5064:	f94017e8 	ldr	x8, [sp, #40]
   a5068:	91004389 	add	x9, x28, #0x10
   a506c:	f945be73 	ldr	x19, [x19, #2936]
   a5070:	f90003fc 	str	x28, [sp]
   a5074:	f9000fe9 	str	x9, [sp, #24]
   a5078:	b4000e88 	cbz	x8, a5248 <JNI_OnLoad@@Base+0xa10>
   a507c:	93407ebb 	sbfm	x27, x21, #0, #31
   a5080:	aa1f03fc 	orr	x28, xzr, xzr
   a5084:	14000005 	b	a5098 <JNI_OnLoad@@Base+0x860>
   a5088:	f94017e8 	ldr	x8, [sp, #40]
   a508c:	9100079c 	add	x28, x28, #0x1
   a5090:	eb08039f 	subs	xzr, x28, x8
   a5094:	54000da2 	b.cs	a5248 <JNI_OnLoad@@Base+0xa10>  // b.hs, b.nlast
   a5098:	f9400668 	ldr	x8, [x19, #8]
   a509c:	b4ffff68 	cbz	x8, a5088 <JNI_OnLoad@@Base+0x850>
   a50a0:	9e670100 	fmov	d0, x8
   a50a4:	0e205800 	cnt	v0.8b, v0.8b
   a50a8:	2e303800 	uaddlv	h0, v0.8b
   a50ac:	1e260009 	fmov	w9, s0
   a50b0:	f100053f 	subs	xzr, x9, #0x1
   a50b4:	54000088 	b.hi	a50c4 <JNI_OnLoad@@Base+0x88c>  // b.pmore
   a50b8:	d100050a 	sub	x10, x8, #0x1
   a50bc:	8a1b014a 	and	x10, x10, x27
   a50c0:	14000006 	b	a50d8 <JNI_OnLoad@@Base+0x8a0>
   a50c4:	eb1b011f 	subs	xzr, x8, x27
   a50c8:	aa1b03ea 	orr	x10, xzr, x27
   a50cc:	54000068 	b.hi	a50d8 <JNI_OnLoad@@Base+0x8a0>  // b.pmore
   a50d0:	9ac80b6a 	udiv	x10, x27, x8
   a50d4:	9b08ed4a 	msub	x10, x10, x8, x27
   a50d8:	f940026b 	ldr	x11, [x19]
   a50dc:	f86a796b 	ldr	x11, [x11, x10, lsl #3]
   a50e0:	b4fffd4b 	cbz	x11, a5088 <JNI_OnLoad@@Base+0x850>
   a50e4:	f9400179 	ldr	x25, [x11]
   a50e8:	b4fffd19 	cbz	x25, a5088 <JNI_OnLoad@@Base+0x850>
   a50ec:	5280018b 	movz	w11, #0xc
   a50f0:	9b0b7f8b 	madd	x11, x28, x11, xzr
   a50f4:	8b0b02cb 	add	x11, x22, x11
   a50f8:	b9400178 	ldr	w24, [x11]
   a50fc:	b9400974 	ldr	w20, [x11, #8]
   a5100:	d100050b 	sub	x11, x8, #0x1
   a5104:	14000006 	b	a511c <JNI_OnLoad@@Base+0x8e4>
   a5108:	b940132c 	ldr	w12, [x25, #16]
   a510c:	6b15019f 	subs	wzr, w12, w21
   a5110:	54000220 	b.eq	a5154 <JNI_OnLoad@@Base+0x91c>  // b.none
   a5114:	f9400339 	ldr	x25, [x25]
   a5118:	b4fffb99 	cbz	x25, a5088 <JNI_OnLoad@@Base+0x850>
   a511c:	f940072c 	ldr	x12, [x25, #8]
   a5120:	eb1b019f 	subs	xzr, x12, x27
   a5124:	54ffff20 	b.eq	a5108 <JNI_OnLoad@@Base+0x8d0>  // b.none
   a5128:	f100053f 	subs	xzr, x9, #0x1
   a512c:	54000068 	b.hi	a5138 <JNI_OnLoad@@Base+0x900>  // b.pmore
   a5130:	8a0b018c 	and	x12, x12, x11
   a5134:	14000005 	b	a5148 <JNI_OnLoad@@Base+0x910>
   a5138:	eb08019f 	subs	xzr, x12, x8
   a513c:	54000063 	b.cc	a5148 <JNI_OnLoad@@Base+0x910>  // b.lo, b.ul, b.last
   a5140:	9ac8098d 	udiv	x13, x12, x8
   a5144:	9b08b1ac 	msub	x12, x13, x8, x12
   a5148:	eb0a019f 	subs	xzr, x12, x10
   a514c:	54fffe40 	b.eq	a5114 <JNI_OnLoad@@Base+0x8dc>  // b.none
   a5150:	17ffffce 	b	a5088 <JNI_OnLoad@@Base+0x850>
   a5154:	f000008a 	adrp	x10, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a5158:	9121214a 	add	x10, x10, #0x848
   a515c:	f9400149 	ldr	x9, [x10]
   a5160:	b4000269 	cbz	x9, a51ac <JNI_OnLoad@@Base+0x974>
   a5164:	aa0a03e8 	orr	x8, xzr, x10
   a5168:	b940212a 	ldr	w10, [x9, #32]
   a516c:	9100212b 	add	x11, x9, #0x8
   a5170:	6b15015f 	subs	wzr, w10, w21
   a5174:	9a89b16a 	csel	x10, x11, x9, lt	// lt = tstop
   a5178:	9a89b108 	csel	x8, x8, x9, lt	// lt = tstop
   a517c:	f9400149 	ldr	x9, [x10]
   a5180:	b5ffff49 	cbnz	x9, a5168 <JNI_OnLoad@@Base+0x930>
   a5184:	f000008a 	adrp	x10, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a5188:	9121214a 	add	x10, x10, #0x848
   a518c:	eb0a011f 	subs	xzr, x8, x10
   a5190:	540000e0 	b.eq	a51ac <JNI_OnLoad@@Base+0x974>  // b.none
   a5194:	b9402109 	ldr	w9, [x8, #32]
   a5198:	6b0902bf 	subs	wzr, w21, w9
   a519c:	9a88b148 	csel	x8, x10, x8, lt	// lt = tstop
   a51a0:	eb0a011f 	subs	xzr, x8, x10
   a51a4:	540000a1 	b.ne	a51b8 <JNI_OnLoad@@Base+0x980>  // b.any
   a51a8:	14000024 	b	a5238 <JNI_OnLoad@@Base+0xa00>
   a51ac:	aa0a03e8 	orr	x8, xzr, x10
   a51b0:	eb0a011f 	subs	xzr, x8, x10
   a51b4:	54000420 	b.eq	a5238 <JNI_OnLoad@@Base+0xa00>  // b.none
   a51b8:	f9400f20 	ldr	x0, [x25, #24]
   a51bc:	a9402408 	ldp	x8, x9, [x0]
   a51c0:	cb080129 	sub	x9, x9, x8
   a51c4:	eb890f1f 	subs	xzr, x24, x9, asr #3
   a51c8:	54001a62 	b.cs	a5514 <JNI_OnLoad@@Base+0xcdc>  // b.hs, b.nlast
   a51cc:	f878791a 	ldr	x26, [x8, x24, lsl #3]
   a51d0:	b4fff5da 	cbz	x26, a5088 <JNI_OnLoad@@Base+0x850>
   a51d4:	34fff5b4 	cbz	w20, a5088 <JNI_OnLoad@@Base+0x850>
   a51d8:	aa1a03e0 	orr	x0, xzr, x26
   a51dc:	97fe832f 	bl	45e98 <JNI_OnUnload@@Base+0x10eb8>
   a51e0:	aa0003f8 	orr	x24, xzr, x0
   a51e4:	aa1a03e0 	orr	x0, xzr, x26
   a51e8:	97fe832a 	bl	45e90 <JNI_OnUnload@@Base+0x10eb0>
   a51ec:	90000068 	adrp	x8, b1000 <_ZTVNSt6__ndk117bad_function_callE@@Base+0x42b8>
   a51f0:	2a0003f9 	orr	w25, wzr, w0
   a51f4:	f945b108 	ldr	x8, [x8, #2912]
   a51f8:	ad448500 	ldp	q0, q1, [x8, #144]
   a51fc:	ad3e07a0 	stp	q0, q1, [x29, #-64]
   a5200:	aa1a03e0 	orr	x0, xzr, x26
   a5204:	97fe8321 	bl	45e88 <JNI_OnUnload@@Base+0x10ea8>
   a5208:	b81e03a0 	stur	w0, [x29, #-32]
   a520c:	910183e0 	add	x0, sp, #0x60
   a5210:	d10103a1 	sub	x1, x29, #0x40
   a5214:	52800482 	movz	w2, #0x24
   a5218:	97fe8196 	bl	45870 <JNI_OnUnload@@Base+0x10890>
   a521c:	f9400fe8 	ldr	x8, [sp, #24]
   a5220:	910183e0 	add	x0, sp, #0x60
   a5224:	aa1803e1 	orr	x1, xzr, x24
   a5228:	2a1903e3 	orr	w3, wzr, w25
   a522c:	8b140102 	add	x2, x8, x20
   a5230:	97fe81c9 	bl	45954 <JNI_OnUnload@@Base+0x10974>
   a5234:	17ffff95 	b	a5088 <JNI_OnLoad@@Base+0x850>
   a5238:	a94007e0 	ldp	x0, x1, [sp]
   a523c:	2a1503e2 	orr	w2, wzr, w21
   a5240:	97fe7074 	bl	41410 <JNI_OnUnload@@Base+0xc430>
   a5244:	17ffffdd 	b	a51b8 <JNI_OnLoad@@Base+0x980>
   a5248:	f94013e8 	ldr	x8, [sp, #32]
   a524c:	b4000ea8 	cbz	x8, a5420 <JNI_OnLoad@@Base+0xbe8>
   a5250:	2a1503e8 	orr	w8, wzr, w21
   a5254:	aa1f03fc 	orr	x28, xzr, xzr
   a5258:	93407d1b 	sbfm	x27, x8, #0, #31
   a525c:	14000005 	b	a5270 <JNI_OnLoad@@Base+0xa38>
   a5260:	f94013e8 	ldr	x8, [sp, #32]
   a5264:	9100079c 	add	x28, x28, #0x1
   a5268:	eb08039f 	subs	xzr, x28, x8
   a526c:	54000da2 	b.cs	a5420 <JNI_OnLoad@@Base+0xbe8>  // b.hs, b.nlast
   a5270:	f9400668 	ldr	x8, [x19, #8]
   a5274:	b4ffff68 	cbz	x8, a5260 <JNI_OnLoad@@Base+0xa28>
   a5278:	9e670100 	fmov	d0, x8
   a527c:	0e205800 	cnt	v0.8b, v0.8b
   a5280:	2e303800 	uaddlv	h0, v0.8b
   a5284:	1e260009 	fmov	w9, s0
   a5288:	f100053f 	subs	xzr, x9, #0x1
   a528c:	54000088 	b.hi	a529c <JNI_OnLoad@@Base+0xa64>  // b.pmore
   a5290:	d100050a 	sub	x10, x8, #0x1
   a5294:	8a1b014a 	and	x10, x10, x27
   a5298:	14000006 	b	a52b0 <JNI_OnLoad@@Base+0xa78>
   a529c:	eb1b011f 	subs	xzr, x8, x27
   a52a0:	aa1b03ea 	orr	x10, xzr, x27
   a52a4:	54000068 	b.hi	a52b0 <JNI_OnLoad@@Base+0xa78>  // b.pmore
   a52a8:	9ac80b6a 	udiv	x10, x27, x8
   a52ac:	9b08ed4a 	msub	x10, x10, x8, x27
   a52b0:	f940026b 	ldr	x11, [x19]
   a52b4:	f86a796b 	ldr	x11, [x11, x10, lsl #3]
   a52b8:	b4fffd4b 	cbz	x11, a5260 <JNI_OnLoad@@Base+0xa28>
   a52bc:	f9400179 	ldr	x25, [x11]
   a52c0:	b4fffd19 	cbz	x25, a5260 <JNI_OnLoad@@Base+0xa28>
   a52c4:	5280018b 	movz	w11, #0xc
   a52c8:	9b0b7f8b 	madd	x11, x28, x11, xzr
   a52cc:	8b0b02eb 	add	x11, x23, x11
   a52d0:	b9400178 	ldr	w24, [x11]
   a52d4:	b9400974 	ldr	w20, [x11, #8]
   a52d8:	d100050b 	sub	x11, x8, #0x1
   a52dc:	14000006 	b	a52f4 <JNI_OnLoad@@Base+0xabc>
   a52e0:	b940132c 	ldr	w12, [x25, #16]
   a52e4:	6b15019f 	subs	wzr, w12, w21
   a52e8:	54000220 	b.eq	a532c <JNI_OnLoad@@Base+0xaf4>  // b.none
   a52ec:	f9400339 	ldr	x25, [x25]
   a52f0:	b4fffb99 	cbz	x25, a5260 <JNI_OnLoad@@Base+0xa28>
   a52f4:	f940072c 	ldr	x12, [x25, #8]
   a52f8:	eb1b019f 	subs	xzr, x12, x27
   a52fc:	54ffff20 	b.eq	a52e0 <JNI_OnLoad@@Base+0xaa8>  // b.none
   a5300:	f100053f 	subs	xzr, x9, #0x1
   a5304:	54000068 	b.hi	a5310 <JNI_OnLoad@@Base+0xad8>  // b.pmore
   a5308:	8a0b018c 	and	x12, x12, x11
   a530c:	14000005 	b	a5320 <JNI_OnLoad@@Base+0xae8>
   a5310:	eb08019f 	subs	xzr, x12, x8
   a5314:	54000063 	b.cc	a5320 <JNI_OnLoad@@Base+0xae8>  // b.lo, b.ul, b.last
   a5318:	9ac8098d 	udiv	x13, x12, x8
   a531c:	9b08b1ac 	msub	x12, x13, x8, x12
   a5320:	eb0a019f 	subs	xzr, x12, x10
   a5324:	54fffe40 	b.eq	a52ec <JNI_OnLoad@@Base+0xab4>  // b.none
   a5328:	17ffffce 	b	a5260 <JNI_OnLoad@@Base+0xa28>
   a532c:	f000008a 	adrp	x10, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a5330:	9121214a 	add	x10, x10, #0x848
   a5334:	f9400149 	ldr	x9, [x10]
   a5338:	b4000269 	cbz	x9, a5384 <JNI_OnLoad@@Base+0xb4c>
   a533c:	aa0a03e8 	orr	x8, xzr, x10
   a5340:	b940212a 	ldr	w10, [x9, #32]
   a5344:	9100212b 	add	x11, x9, #0x8
   a5348:	6b15015f 	subs	wzr, w10, w21
   a534c:	9a89b16a 	csel	x10, x11, x9, lt	// lt = tstop
   a5350:	9a89b108 	csel	x8, x8, x9, lt	// lt = tstop
   a5354:	f9400149 	ldr	x9, [x10]
   a5358:	b5ffff49 	cbnz	x9, a5340 <JNI_OnLoad@@Base+0xb08>
   a535c:	f000008a 	adrp	x10, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a5360:	9121214a 	add	x10, x10, #0x848
   a5364:	eb0a011f 	subs	xzr, x8, x10
   a5368:	540000e0 	b.eq	a5384 <JNI_OnLoad@@Base+0xb4c>  // b.none
   a536c:	b9402109 	ldr	w9, [x8, #32]
   a5370:	6b0902bf 	subs	wzr, w21, w9
   a5374:	9a88b148 	csel	x8, x10, x8, lt	// lt = tstop
   a5378:	eb0a011f 	subs	xzr, x8, x10
   a537c:	540000a1 	b.ne	a5390 <JNI_OnLoad@@Base+0xb58>  // b.any
   a5380:	14000024 	b	a5410 <JNI_OnLoad@@Base+0xbd8>
   a5384:	aa0a03e8 	orr	x8, xzr, x10
   a5388:	eb0a011f 	subs	xzr, x8, x10
   a538c:	54000420 	b.eq	a5410 <JNI_OnLoad@@Base+0xbd8>  // b.none
   a5390:	f9400f20 	ldr	x0, [x25, #24]
   a5394:	a9402408 	ldp	x8, x9, [x0]
   a5398:	cb080129 	sub	x9, x9, x8
   a539c:	eb890f1f 	subs	xzr, x24, x9, asr #3
   a53a0:	54000c62 	b.cs	a552c <JNI_OnLoad@@Base+0xcf4>  // b.hs, b.nlast
   a53a4:	f878791a 	ldr	x26, [x8, x24, lsl #3]
   a53a8:	b4fff5da 	cbz	x26, a5260 <JNI_OnLoad@@Base+0xa28>
   a53ac:	34fff5b4 	cbz	w20, a5260 <JNI_OnLoad@@Base+0xa28>
   a53b0:	aa1a03e0 	orr	x0, xzr, x26
   a53b4:	97fe82b9 	bl	45e98 <JNI_OnUnload@@Base+0x10eb8>
   a53b8:	aa0003f8 	orr	x24, xzr, x0
   a53bc:	aa1a03e0 	orr	x0, xzr, x26
   a53c0:	97fe82b4 	bl	45e90 <JNI_OnUnload@@Base+0x10eb0>
   a53c4:	90000068 	adrp	x8, b1000 <_ZTVNSt6__ndk117bad_function_callE@@Base+0x42b8>
   a53c8:	2a0003f9 	orr	w25, wzr, w0
   a53cc:	f945b108 	ldr	x8, [x8, #2912]
   a53d0:	ad448500 	ldp	q0, q1, [x8, #144]
   a53d4:	ad3e07a0 	stp	q0, q1, [x29, #-64]
   a53d8:	aa1a03e0 	orr	x0, xzr, x26
   a53dc:	97fe82ab 	bl	45e88 <JNI_OnUnload@@Base+0x10ea8>
   a53e0:	b81e03a0 	stur	w0, [x29, #-32]
   a53e4:	910183e0 	add	x0, sp, #0x60
   a53e8:	d10103a1 	sub	x1, x29, #0x40
   a53ec:	52800482 	movz	w2, #0x24
   a53f0:	97fe8120 	bl	45870 <JNI_OnUnload@@Base+0x10890>
   a53f4:	f9400fe8 	ldr	x8, [sp, #24]
   a53f8:	910183e0 	add	x0, sp, #0x60
   a53fc:	aa1803e1 	orr	x1, xzr, x24
   a5400:	2a1903e3 	orr	w3, wzr, w25
   a5404:	8b140102 	add	x2, x8, x20
   a5408:	97fe8153 	bl	45954 <JNI_OnUnload@@Base+0x10974>
   a540c:	17ffff95 	b	a5260 <JNI_OnLoad@@Base+0xa28>
   a5410:	a94007e0 	ldp	x0, x1, [sp]
   a5414:	2a1503e2 	orr	w2, wzr, w21
   a5418:	97fe6ffe 	bl	41410 <JNI_OnUnload@@Base+0xc430>
   a541c:	17ffffdd 	b	a5390 <JNI_OnLoad@@Base+0xb58>
   a5420:	aa1603e0 	orr	x0, xzr, x22
   a5424:	97ffdd3e 	bl	9c91c <JNI_OnUnload@@Base+0x6793c>
   a5428:	aa1703e0 	orr	x0, xzr, x23
   a542c:	97ffdd3c 	bl	9c91c <JNI_OnUnload@@Base+0x6793c>
   a5430:	f9400bfb 	ldr	x27, [sp, #16]
   a5434:	394103e8 	ldrb	w8, [sp, #64]
   a5438:	36000068 	tbz	w8, #0, a5444 <JNI_OnLoad@@Base+0xc0c>
   a543c:	f9402be0 	ldr	x0, [sp, #80]
   a5440:	97ffdd35 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a5444:	f9401768 	ldr	x8, [x27, #40]
   a5448:	f85f03a9 	ldur	x9, [x29, #-16]
   a544c:	eb09011f 	subs	xzr, x8, x9
   a5450:	54000b21 	b.ne	a55b4 <JNI_OnLoad@@Base+0xd7c>  // b.any
   a5454:	9106c3ff 	add	sp, sp, #0x1b0
   a5458:	a9454ff4 	ldp	x20, x19, [sp, #80]
   a545c:	a94457f6 	ldp	x22, x21, [sp, #64]
   a5460:	a9435ff8 	ldp	x24, x23, [sp, #48]
   a5464:	a94267fa 	ldp	x26, x25, [sp, #32]
   a5468:	a9416ffc 	ldp	x28, x27, [sp, #16]
   a546c:	a8c67bfd 	ldp	x29, x30, [sp], #96
   a5470:	d65f03c0 	ret
   a5474:	a94306a2 	ldp	x2, x1, [x21, #48]
   a5478:	910103e0 	add	x0, sp, #0x40
   a547c:	97fe3f67 	bl	35218 <JNI_OnUnload@@Base+0x238>
   a5480:	a940d2bc 	ldp	x28, x20, [x21, #8]
   a5484:	b5ffc6d4 	cbnz	x20, a4d5c <JNI_OnLoad@@Base+0x524>
   a5488:	17fffe32 	b	a4d50 <JNI_OnLoad@@Base+0x518>
   a548c:	a94206a2 	ldp	x2, x1, [x21, #32]
   a5490:	910103e0 	add	x0, sp, #0x40
   a5494:	97fe3f61 	bl	35218 <JNI_OnUnload@@Base+0x238>
   a5498:	a940d2bc 	ldp	x28, x20, [x21, #8]
   a549c:	b5ffc614 	cbnz	x20, a4d5c <JNI_OnLoad@@Base+0x524>
   a54a0:	910102a8 	add	x8, x21, #0x40
   a54a4:	17fffe2c 	b	a4d54 <JNI_OnLoad@@Base+0x51c>
   a54a8:	d503201f 	hint	#0x0
   a54ac:	10089da0 	adr	x0, b6860 <DPT_UNKNOWN_DATA@@Base+0x3d7>
   a54b0:	97ffe24b 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a54b4:	910183e1 	add	x1, sp, #0x60
   a54b8:	aa0003f4 	orr	x20, xzr, x0
   a54bc:	97fe7036 	bl	41594 <JNI_OnUnload@@Base+0xc5b4>
   a54c0:	d503201f 	hint	#0x0
   a54c4:	10ce0f20 	adr	x0, 416a8 <JNI_OnUnload@@Base+0xc6c8>
   a54c8:	d503201f 	hint	#0x0
   a54cc:	1003ae22 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a54d0:	aa1403e1 	orr	x1, xzr, x20
   a54d4:	97ffe1a8 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a54d8:	d503201f 	hint	#0x0
   a54dc:	10089d20 	adr	x0, b6880 <DPT_UNKNOWN_DATA@@Base+0x3f7>
   a54e0:	97ffe23f 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a54e4:	52800028 	movz	w8, #0x1
   a54e8:	39000008 	strb	w8, [x0]
   a54ec:	17fffdd6 	b	a4c44 <JNI_OnLoad@@Base+0x40c>
   a54f0:	aa1303e0 	orr	x0, xzr, x19
   a54f4:	97fe813d 	bl	459e8 <JNI_OnUnload@@Base+0x10a08>
   a54f8:	8b000268 	add	x8, x19, x0
   a54fc:	385fe108 	ldurb	w8, [x8, #-2]
   a5500:	5100e908 	sub	w8, w8, #0x3a
   a5504:	3100291f 	adds	wzr, w8, #0xa
   a5508:	54ffbc83 	b.cc	a4c98 <JNI_OnLoad@@Base+0x460>  // b.lo, b.ul, b.last
   a550c:	94000582 	bl	a6b14 <JNI_OnLoad@@Base+0x22dc>
   a5510:	17ffffcd 	b	a5444 <JNI_OnLoad@@Base+0xc0c>
   a5514:	f9400be8 	ldr	x8, [sp, #16]
   a5518:	f9401508 	ldr	x8, [x8, #40]
   a551c:	f85f03a9 	ldur	x9, [x29, #-16]
   a5520:	eb09011f 	subs	xzr, x8, x9
   a5524:	54000481 	b.ne	a55b4 <JNI_OnLoad@@Base+0xd7c>  // b.any
   a5528:	97fe3ff0 	bl	354e8 <JNI_OnUnload@@Base+0x508>
   a552c:	f9400be8 	ldr	x8, [sp, #16]
   a5530:	f9401508 	ldr	x8, [x8, #40]
   a5534:	f85f03a9 	ldur	x9, [x29, #-16]
   a5538:	eb09011f 	subs	xzr, x8, x9
   a553c:	540003c1 	b.ne	a55b4 <JNI_OnLoad@@Base+0xd7c>  // b.any
   a5540:	97fe3fea 	bl	354e8 <JNI_OnUnload@@Base+0x508>
   a5544:	f9000bfb 	str	x27, [sp, #16]
   a5548:	1400000f 	b	a5584 <JNI_OnLoad@@Base+0xd4c>
   a554c:	f9000bfb 	str	x27, [sp, #16]
   a5550:	1400000d 	b	a5584 <JNI_OnLoad@@Base+0xd4c>
   a5554:	f9000bfb 	str	x27, [sp, #16]
   a5558:	1400000b 	b	a5584 <JNI_OnLoad@@Base+0xd4c>
   a555c:	1400000a 	b	a5584 <JNI_OnLoad@@Base+0xd4c>
   a5560:	14000009 	b	a5584 <JNI_OnLoad@@Base+0xd4c>
   a5564:	14000008 	b	a5584 <JNI_OnLoad@@Base+0xd4c>
   a5568:	14000007 	b	a5584 <JNI_OnLoad@@Base+0xd4c>
   a556c:	14000006 	b	a5584 <JNI_OnLoad@@Base+0xd4c>
   a5570:	14000005 	b	a5584 <JNI_OnLoad@@Base+0xd4c>
   a5574:	14000004 	b	a5584 <JNI_OnLoad@@Base+0xd4c>
   a5578:	14000003 	b	a5584 <JNI_OnLoad@@Base+0xd4c>
   a557c:	14000002 	b	a5584 <JNI_OnLoad@@Base+0xd4c>
   a5580:	14000001 	b	a5584 <JNI_OnLoad@@Base+0xd4c>
   a5584:	394103e8 	ldrb	w8, [sp, #64]
   a5588:	aa0003f3 	orr	x19, xzr, x0
   a558c:	36000068 	tbz	w8, #0, a5598 <JNI_OnLoad@@Base+0xd60>
   a5590:	f9402be0 	ldr	x0, [sp, #80]
   a5594:	97ffdce0 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a5598:	f9400be8 	ldr	x8, [sp, #16]
   a559c:	f9401508 	ldr	x8, [x8, #40]
   a55a0:	f85f03a9 	ldur	x9, [x29, #-16]
   a55a4:	eb09011f 	subs	xzr, x8, x9
   a55a8:	54000061 	b.ne	a55b4 <JNI_OnLoad@@Base+0xd7c>  // b.any
   a55ac:	aa1303e0 	orr	x0, xzr, x19
   a55b0:	97ffe577 	bl	9eb8c <JNI_OnUnload@@Base+0x69bac>
   a55b4:	94000b07 	bl	a81d0 <__stack_chk_fail@plt>
   a55b8:	d10183ff 	sub	sp, sp, #0x60
   a55bc:	a9017bfd 	stp	x29, x30, [sp, #16]
   a55c0:	a90267fa 	stp	x26, x25, [sp, #32]
   a55c4:	a9035ff8 	stp	x24, x23, [sp, #48]
   a55c8:	a90457f6 	stp	x22, x21, [sp, #64]
   a55cc:	a9054ff4 	stp	x20, x19, [sp, #80]
   a55d0:	910043fd 	add	x29, sp, #0x10
   a55d4:	d53bd058 	mrs	x24, tpidr_el0
   a55d8:	f0000099 	adrp	x25, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a55dc:	f9401708 	ldr	x8, [x24, #40]
   a55e0:	f90007e8 	str	x8, [sp, #8]
   a55e4:	f9444728 	ldr	x8, [x25, #2184]
   a55e8:	b4000608 	cbz	x8, a56a8 <JNI_OnLoad@@Base+0xe70>
   a55ec:	aa0403f3 	orr	x19, xzr, x4
   a55f0:	aa0303f7 	orr	x23, xzr, x3
   a55f4:	aa0103f4 	orr	x20, xzr, x1
   a55f8:	aa0003f5 	orr	x21, xzr, x0
   a55fc:	aa1f03e0 	orr	x0, xzr, xzr
   a5600:	b40003e2 	cbz	x2, a567c <JNI_OnLoad@@Base+0xe44>
   a5604:	b40003d7 	cbz	x23, a567c <JNI_OnLoad@@Base+0xe44>
   a5608:	f0000088 	adrp	x8, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a560c:	b9485908 	ldr	w8, [x8, #2136]
   a5610:	71008d1f 	subs	wzr, w8, #0x23
   a5614:	5400006a 	b.ge	a5620 <JNI_OnLoad@@Base+0xde8>  // b.tcont
   a5618:	7100711f 	subs	wzr, w8, #0x1c
   a561c:	5400008b 	b.lt	a562c <JNI_OnLoad@@Base+0xdf4>  // b.tstop
   a5620:	91016048 	add	x8, x2, #0x58
   a5624:	91014049 	add	x9, x2, #0x50
   a5628:	14000003 	b	a5634 <JNI_OnLoad@@Base+0xdfc>
   a562c:	91014048 	add	x8, x2, #0x50
   a5630:	91012049 	add	x9, x2, #0x48
   a5634:	f940044a 	ldr	x10, [x2, #8]
   a5638:	aa1f03e0 	orr	x0, xzr, xzr
   a563c:	b400020a 	cbz	x10, a567c <JNI_OnLoad@@Base+0xe44>
   a5640:	f9400108 	ldr	x8, [x8]
   a5644:	b40001c8 	cbz	x8, a567c <JNI_OnLoad@@Base+0xe44>
   a5648:	f9400129 	ldr	x9, [x9]
   a564c:	b4000189 	cbz	x9, a567c <JNI_OnLoad@@Base+0xe44>
   a5650:	b94002eb 	ldr	w11, [x23]
   a5654:	910003e1 	add	x1, sp, #0x0
   a5658:	f90003ff 	str	xzr, [sp]
   a565c:	aa0203fa 	orr	x26, xzr, x2
   a5660:	b86b7908 	ldr	w8, [x8, x11, lsl #2]
   a5664:	b8687928 	ldr	w8, [x9, x8, lsl #2]
   a5668:	8b080156 	add	x22, x10, x8
   a566c:	aa1603e0 	orr	x0, xzr, x22
   a5670:	97fe811c 	bl	45ae0 <JNI_OnUnload@@Base+0x10b00>
   a5674:	aa1a03e2 	orr	x2, xzr, x26
   a5678:	8b0002c0 	add	x0, x22, x0
   a567c:	aa0203e1 	orr	x1, xzr, x2
   a5680:	aa0203f6 	orr	x22, xzr, x2
   a5684:	aa1703e2 	orr	x2, xzr, x23
   a5688:	97fffd49 	bl	a4bac <JNI_OnLoad@@Base+0x374>
   a568c:	f9444728 	ldr	x8, [x25, #2184]
   a5690:	aa1503e0 	orr	x0, xzr, x21
   a5694:	aa1403e1 	orr	x1, xzr, x20
   a5698:	aa1603e2 	orr	x2, xzr, x22
   a569c:	aa1703e3 	orr	x3, xzr, x23
   a56a0:	aa1303e4 	orr	x4, xzr, x19
   a56a4:	d63f0100 	blr	x8
   a56a8:	f9401708 	ldr	x8, [x24, #40]
   a56ac:	f94007e9 	ldr	x9, [sp, #8]
   a56b0:	eb09011f 	subs	xzr, x8, x9
   a56b4:	54000101 	b.ne	a56d4 <JNI_OnLoad@@Base+0xe9c>  // b.any
   a56b8:	a9454ff4 	ldp	x20, x19, [sp, #80]
   a56bc:	a94457f6 	ldp	x22, x21, [sp, #64]
   a56c0:	a9435ff8 	ldp	x24, x23, [sp, #48]
   a56c4:	a94267fa 	ldp	x26, x25, [sp, #32]
   a56c8:	a9417bfd 	ldp	x29, x30, [sp, #16]
   a56cc:	910183ff 	add	sp, sp, #0x60
   a56d0:	d65f03c0 	ret
   a56d4:	94000abf 	bl	a81d0 <__stack_chk_fail@plt>
   a56d8:	a9bb7bfd 	stp	x29, x30, [sp, #-80]!
   a56dc:	a90167fa 	stp	x26, x25, [sp, #16]
   a56e0:	a9025ff8 	stp	x24, x23, [sp, #32]
   a56e4:	a90357f6 	stp	x22, x21, [sp, #48]
   a56e8:	a9044ff4 	stp	x20, x19, [sp, #64]
   a56ec:	910003fd 	add	x29, sp, #0x0
   a56f0:	f000009a 	adrp	x26, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a56f4:	f9444b48 	ldr	x8, [x26, #2192]
   a56f8:	b4000348 	cbz	x8, a5760 <JNI_OnLoad@@Base+0xf28>
   a56fc:	aa0103f5 	orr	x21, xzr, x1
   a5700:	aa0003f6 	orr	x22, xzr, x0
   a5704:	aa0203e0 	orr	x0, xzr, x2
   a5708:	aa0503e1 	orr	x1, xzr, x5
   a570c:	aa0203f7 	orr	x23, xzr, x2
   a5710:	aa0603e2 	orr	x2, xzr, x6
   a5714:	aa0403f3 	orr	x19, xzr, x4
   a5718:	aa0303f4 	orr	x20, xzr, x3
   a571c:	aa0603f8 	orr	x24, xzr, x6
   a5720:	aa0503f9 	orr	x25, xzr, x5
   a5724:	97fffd22 	bl	a4bac <JNI_OnLoad@@Base+0x374>
   a5728:	f9444b47 	ldr	x7, [x26, #2192]
   a572c:	aa1603e0 	orr	x0, xzr, x22
   a5730:	aa1503e1 	orr	x1, xzr, x21
   a5734:	aa1703e2 	orr	x2, xzr, x23
   a5738:	aa1403e3 	orr	x3, xzr, x20
   a573c:	aa1303e4 	orr	x4, xzr, x19
   a5740:	aa1903e5 	orr	x5, xzr, x25
   a5744:	aa1803e6 	orr	x6, xzr, x24
   a5748:	a9444ff4 	ldp	x20, x19, [sp, #64]
   a574c:	a94357f6 	ldp	x22, x21, [sp, #48]
   a5750:	a9425ff8 	ldp	x24, x23, [sp, #32]
   a5754:	a94167fa 	ldp	x26, x25, [sp, #16]
   a5758:	a8c57bfd 	ldp	x29, x30, [sp], #80
   a575c:	d61f00e0 	br	x7
   a5760:	aa1f03e0 	orr	x0, xzr, xzr
   a5764:	a9444ff4 	ldp	x20, x19, [sp, #64]
   a5768:	a94357f6 	ldp	x22, x21, [sp, #48]
   a576c:	a9425ff8 	ldp	x24, x23, [sp, #32]
   a5770:	a94167fa 	ldp	x26, x25, [sp, #16]
   a5774:	a8c57bfd 	ldp	x29, x30, [sp], #80
   a5778:	d65f03c0 	ret
   a577c:	a9bc7bfd 	stp	x29, x30, [sp, #-64]!
   a5780:	a9015ff8 	stp	x24, x23, [sp, #16]
   a5784:	a90257f6 	stp	x22, x21, [sp, #32]
   a5788:	a9034ff4 	stp	x20, x19, [sp, #48]
   a578c:	910003fd 	add	x29, sp, #0x0
   a5790:	f0000098 	adrp	x24, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a5794:	f9444f08 	ldr	x8, [x24, #2200]
   a5798:	b40002a8 	cbz	x8, a57ec <JNI_OnLoad@@Base+0xfb4>
   a579c:	aa0203f3 	orr	x19, xzr, x2
   a57a0:	aa0003f4 	orr	x20, xzr, x0
   a57a4:	aa0103e0 	orr	x0, xzr, x1
   a57a8:	aa0103f5 	orr	x21, xzr, x1
   a57ac:	aa0303e1 	orr	x1, xzr, x3
   a57b0:	aa0403e2 	orr	x2, xzr, x4
   a57b4:	aa0403f6 	orr	x22, xzr, x4
   a57b8:	aa0303f7 	orr	x23, xzr, x3
   a57bc:	97fffcfc 	bl	a4bac <JNI_OnLoad@@Base+0x374>
   a57c0:	f9444f05 	ldr	x5, [x24, #2200]
   a57c4:	aa1403e0 	orr	x0, xzr, x20
   a57c8:	aa1503e1 	orr	x1, xzr, x21
   a57cc:	aa1303e2 	orr	x2, xzr, x19
   a57d0:	aa1703e3 	orr	x3, xzr, x23
   a57d4:	aa1603e4 	orr	x4, xzr, x22
   a57d8:	a9434ff4 	ldp	x20, x19, [sp, #48]
   a57dc:	a94257f6 	ldp	x22, x21, [sp, #32]
   a57e0:	a9415ff8 	ldp	x24, x23, [sp, #16]
   a57e4:	a8c47bfd 	ldp	x29, x30, [sp], #64
   a57e8:	d61f00a0 	br	x5
   a57ec:	aa1f03e0 	orr	x0, xzr, xzr
   a57f0:	a9434ff4 	ldp	x20, x19, [sp, #48]
   a57f4:	a94257f6 	ldp	x22, x21, [sp, #32]
   a57f8:	a9415ff8 	ldp	x24, x23, [sp, #16]
   a57fc:	a8c47bfd 	ldp	x29, x30, [sp], #64
   a5800:	d65f03c0 	ret
   a5804:	d10643ff 	sub	sp, sp, #0x190
   a5808:	a9137bfd 	stp	x29, x30, [sp, #304]
   a580c:	a9146ffc 	stp	x28, x27, [sp, #320]
   a5810:	a91567fa 	stp	x26, x25, [sp, #336]
   a5814:	a9165ff8 	stp	x24, x23, [sp, #352]
   a5818:	a91757f6 	stp	x22, x21, [sp, #368]
   a581c:	a9184ff4 	stp	x20, x19, [sp, #384]
   a5820:	9104c3fd 	add	x29, sp, #0x130
   a5824:	d53bd05c 	mrs	x28, tpidr_el0
   a5828:	6f00e400 	movi	v0.2d, #0x0
   a582c:	aa0503f3 	orr	x19, xzr, x5
   a5830:	f9401788 	ldr	x8, [x28, #40]
   a5834:	2a0403f4 	orr	w20, wzr, w4
   a5838:	2a0303f5 	orr	w21, wzr, w3
   a583c:	2a0203f6 	orr	w22, wzr, w2
   a5840:	aa0103f7 	orr	x23, xzr, x1
   a5844:	aa0003f8 	orr	x24, xzr, x0
   a5848:	f81f03a8 	stur	x8, [x29, #-16]
   a584c:	d50320ff 	hint	#0x7
   a5850:	ad0103e0 	stp	q0, q0, [sp, #32]
   a5854:	ad0203e0 	stp	q0, q0, [sp, #64]
   a5858:	ad0303e0 	stp	q0, q0, [sp, #96]
   a585c:	ad0403e0 	stp	q0, q0, [sp, #128]
   a5860:	ad0503e0 	stp	q0, q0, [sp, #160]
   a5864:	ad0603e0 	stp	q0, q0, [sp, #192]
   a5868:	ad0703e0 	stp	q0, q0, [sp, #224]
   a586c:	ad0803e0 	stp	q0, q0, [sp, #256]
   a5870:	f9000ffe 	str	x30, [sp, #24]
   a5874:	910083e1 	add	x1, sp, #0x20
   a5878:	2a0403e0 	orr	w0, wzr, w4
   a587c:	52802002 	movz	w2, #0x100
   a5880:	97fe74a7 	bl	42b1c <JNI_OnUnload@@Base+0xdb3c>
   a5884:	910083e0 	add	x0, sp, #0x20
   a5888:	94000a5e 	bl	a8200 <strlen@plt>
   a588c:	b100401f 	adds	xzr, x0, #0x10
   a5890:	54000922 	b.cs	a59b4 <JNI_OnLoad@@Base+0x117c>  // b.hs, b.nlast
   a5894:	aa0003f9 	orr	x25, xzr, x0
   a5898:	f1005c1f 	subs	xzr, x0, #0x17
   a589c:	540000e2 	b.cs	a58b8 <JNI_OnLoad@@Base+0x1080>  // b.hs, b.nlast
   a58a0:	531f7b28 	ubfm	w8, w25, #31, #30
   a58a4:	910003e9 	add	x9, sp, #0x0
   a58a8:	b240013a 	orr	x26, x9, #0x1
   a58ac:	390003e8 	strb	w8, [sp]
   a58b0:	b5000159 	cbnz	x25, a58d8 <JNI_OnLoad@@Base+0x10a0>
   a58b4:	1400000d 	b	a58e8 <JNI_OnLoad@@Base+0x10b0>
   a58b8:	b2400f28 	orr	x8, x25, #0xf
   a58bc:	9100051b 	add	x27, x8, #0x1
   a58c0:	aa1b03e0 	orr	x0, xzr, x27
   a58c4:	97ffdbf6 	bl	9c89c <JNI_OnUnload@@Base+0x678bc>
   a58c8:	b2400368 	orr	x8, x27, #0x1
   a58cc:	aa0003fa 	orr	x26, xzr, x0
   a58d0:	a90083f9 	stp	x25, x0, [sp, #8]
   a58d4:	f90003e8 	str	x8, [sp]
   a58d8:	910083e1 	add	x1, sp, #0x20
   a58dc:	aa1a03e0 	orr	x0, xzr, x26
   a58e0:	aa1903e2 	orr	x2, xzr, x25
   a58e4:	94000a53 	bl	a8230 <memcpy@plt>
   a58e8:	38396b5f 	strb	wzr, [x26, x25]
   a58ec:	910003e0 	add	x0, sp, #0x0
   a58f0:	97fe7341 	bl	425f4 <JNI_OnUnload@@Base+0xd614>
   a58f4:	370001e0 	tbnz	w0, #0, a5930 <JNI_OnLoad@@Base+0x10f8>
   a58f8:	f0000088 	adrp	x8, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a58fc:	120006c9 	and	w9, w22, #0x3
   a5900:	321f02ca 	orr	w10, w22, #0x2
   a5904:	b9485908 	ldr	w8, [x8, #2136]
   a5908:	7100053f 	subs	wzr, w9, #0x1
   a590c:	1a960156 	csel	w22, w10, w22, eq	// eq = none
   a5910:	7100791f 	subs	wzr, w8, #0x1e
   a5914:	540000e1 	b.ne	a5930 <JNI_OnLoad@@Base+0x10f8>  // b.any
   a5918:	d0fffb81 	adrp	x1, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a591c:	911ad421 	add	x1, x1, #0x6b5
   a5920:	910083e0 	add	x0, sp, #0x20
   a5924:	94000a87 	bl	a8340 <strstr@plt>
   a5928:	f100001f 	subs	xzr, x0, #0x0
   a592c:	1a9f02b5 	csel	w21, w21, wzr, eq	// eq = none
   a5930:	d503201f 	hint	#0x0
   a5934:	10fff680 	adr	x0, a5804 <JNI_OnLoad@@Base+0xfcc>
   a5938:	97fea763 	bl	4f6c4 <JNI_OnUnload@@Base+0x1a6e4>
   a593c:	aa0003e8 	orr	x8, xzr, x0
   a5940:	aa1803e0 	orr	x0, xzr, x24
   a5944:	aa1703e1 	orr	x1, xzr, x23
   a5948:	2a1603e2 	orr	w2, wzr, w22
   a594c:	2a1503e3 	orr	w3, wzr, w21
   a5950:	2a1403e4 	orr	w4, wzr, w20
   a5954:	aa1303e5 	orr	x5, xzr, x19
   a5958:	d63f0100 	blr	x8
   a595c:	394003e8 	ldrb	w8, [sp]
   a5960:	aa0003f3 	orr	x19, xzr, x0
   a5964:	36000068 	tbz	w8, #0, a5970 <JNI_OnLoad@@Base+0x1138>
   a5968:	f9400be0 	ldr	x0, [sp, #16]
   a596c:	97ffdbea 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a5970:	97fea758 	bl	4f6d0 <JNI_OnUnload@@Base+0x1a6f0>
   a5974:	35000060 	cbnz	w0, a5980 <JNI_OnLoad@@Base+0x1148>
   a5978:	f9400fe0 	ldr	x0, [sp, #24]
   a597c:	97fea754 	bl	4f6cc <JNI_OnUnload@@Base+0x1a6ec>
   a5980:	f9401788 	ldr	x8, [x28, #40]
   a5984:	f85f03a9 	ldur	x9, [x29, #-16]
   a5988:	eb09011f 	subs	xzr, x8, x9
   a598c:	54000461 	b.ne	a5a18 <JNI_OnLoad@@Base+0x11e0>  // b.any
   a5990:	aa1303e0 	orr	x0, xzr, x19
   a5994:	a9584ff4 	ldp	x20, x19, [sp, #384]
   a5998:	a95757f6 	ldp	x22, x21, [sp, #368]
   a599c:	a9565ff8 	ldp	x24, x23, [sp, #352]
   a59a0:	a95567fa 	ldp	x26, x25, [sp, #336]
   a59a4:	a9546ffc 	ldp	x28, x27, [sp, #320]
   a59a8:	a9537bfd 	ldp	x29, x30, [sp, #304]
   a59ac:	910643ff 	add	sp, sp, #0x190
   a59b0:	d65f03c0 	ret
   a59b4:	f9401788 	ldr	x8, [x28, #40]
   a59b8:	f85f03a9 	ldur	x9, [x29, #-16]
   a59bc:	eb09011f 	subs	xzr, x8, x9
   a59c0:	540002c1 	b.ne	a5a18 <JNI_OnLoad@@Base+0x11e0>  // b.any
   a59c4:	910003e0 	add	x0, sp, #0x0
   a59c8:	97fe3de2 	bl	35150 <JNI_OnUnload@@Base+0x170>
   a59cc:	14000006 	b	a59e4 <JNI_OnLoad@@Base+0x11ac>
   a59d0:	aa0003f3 	orr	x19, xzr, x0
   a59d4:	14000009 	b	a59f8 <JNI_OnLoad@@Base+0x11c0>
   a59d8:	97fe3d90 	bl	35018 <JNI_OnUnload@@Base+0x38>
   a59dc:	aa0003f3 	orr	x19, xzr, x0
   a59e0:	14000006 	b	a59f8 <JNI_OnLoad@@Base+0x11c0>
   a59e4:	394003e8 	ldrb	w8, [sp]
   a59e8:	aa0003f3 	orr	x19, xzr, x0
   a59ec:	36000068 	tbz	w8, #0, a59f8 <JNI_OnLoad@@Base+0x11c0>
   a59f0:	f9400be0 	ldr	x0, [sp, #16]
   a59f4:	97ffdbc8 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a59f8:	910063e0 	add	x0, sp, #0x18
   a59fc:	97fe6ecd 	bl	41530 <JNI_OnUnload@@Base+0xc550>
   a5a00:	f9401788 	ldr	x8, [x28, #40]
   a5a04:	f85f03a9 	ldur	x9, [x29, #-16]
   a5a08:	eb09011f 	subs	xzr, x8, x9
   a5a0c:	54000061 	b.ne	a5a18 <JNI_OnLoad@@Base+0x11e0>  // b.any
   a5a10:	aa1303e0 	orr	x0, xzr, x19
   a5a14:	97ffe45e 	bl	9eb8c <JNI_OnUnload@@Base+0x69bac>
   a5a18:	940009ee 	bl	a81d0 <__stack_chk_fail@plt>
   a5a1c:	d10143ff 	sub	sp, sp, #0x50
   a5a20:	a9017bfd 	stp	x29, x30, [sp, #16]
   a5a24:	f90013f7 	str	x23, [sp, #32]
   a5a28:	a90357f6 	stp	x22, x21, [sp, #48]
   a5a2c:	a9044ff4 	stp	x20, x19, [sp, #64]
   a5a30:	910043fd 	add	x29, sp, #0x10
   a5a34:	d53bd057 	mrs	x23, tpidr_el0
   a5a38:	aa0103f5 	orr	x21, xzr, x1
   a5a3c:	b0fffb81 	adrp	x1, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a5a40:	910ba021 	add	x1, x1, #0x2e8
   a5a44:	f94016e8 	ldr	x8, [x23, #40]
   a5a48:	aa0203f4 	orr	x20, xzr, x2
   a5a4c:	aa0003f6 	orr	x22, xzr, x0
   a5a50:	f90007e8 	str	x8, [sp, #8]
   a5a54:	d50320ff 	hint	#0x7
   a5a58:	aa1e03f3 	orr	x19, xzr, x30
   a5a5c:	f90003fe 	str	x30, [sp]
   a5a60:	94000a38 	bl	a8340 <strstr@plt>
   a5a64:	b40000c0 	cbz	x0, a5a7c <JNI_OnLoad@@Base+0x1244>
   a5a68:	94000a16 	bl	a82c0 <__errno@plt>
   a5a6c:	528001a8 	movz	w8, #0xd
   a5a70:	12800014 	movn	w20, #0x0
   a5a74:	b9000008 	str	w8, [x0]
   a5a78:	1400000a 	b	a5aa0 <JNI_OnLoad@@Base+0x1268>
   a5a7c:	d503201f 	hint	#0x0
   a5a80:	10fffce0 	adr	x0, a5a1c <JNI_OnLoad@@Base+0x11e4>
   a5a84:	97fea710 	bl	4f6c4 <JNI_OnUnload@@Base+0x1a6e4>
   a5a88:	aa0003e8 	orr	x8, xzr, x0
   a5a8c:	aa1603e0 	orr	x0, xzr, x22
   a5a90:	aa1503e1 	orr	x1, xzr, x21
   a5a94:	aa1403e2 	orr	x2, xzr, x20
   a5a98:	d63f0100 	blr	x8
   a5a9c:	2a0003f4 	orr	w20, wzr, w0
   a5aa0:	97fea70c 	bl	4f6d0 <JNI_OnUnload@@Base+0x1a6f0>
   a5aa4:	35000060 	cbnz	w0, a5ab0 <JNI_OnLoad@@Base+0x1278>
   a5aa8:	aa1303e0 	orr	x0, xzr, x19
   a5aac:	97fea708 	bl	4f6cc <JNI_OnUnload@@Base+0x1a6ec>
   a5ab0:	f94016e8 	ldr	x8, [x23, #40]
   a5ab4:	f94007e9 	ldr	x9, [sp, #8]
   a5ab8:	eb09011f 	subs	xzr, x8, x9
   a5abc:	54000221 	b.ne	a5b00 <JNI_OnLoad@@Base+0x12c8>  // b.any
   a5ac0:	2a1403e0 	orr	w0, wzr, w20
   a5ac4:	a9444ff4 	ldp	x20, x19, [sp, #64]
   a5ac8:	f94013f7 	ldr	x23, [sp, #32]
   a5acc:	a94357f6 	ldp	x22, x21, [sp, #48]
   a5ad0:	a9417bfd 	ldp	x29, x30, [sp, #16]
   a5ad4:	910143ff 	add	sp, sp, #0x50
   a5ad8:	d65f03c0 	ret
   a5adc:	aa0003f3 	orr	x19, xzr, x0
   a5ae0:	910003e0 	add	x0, sp, #0x0
   a5ae4:	97fe6e93 	bl	41530 <JNI_OnUnload@@Base+0xc550>
   a5ae8:	f94016e8 	ldr	x8, [x23, #40]
   a5aec:	f94007e9 	ldr	x9, [sp, #8]
   a5af0:	eb09011f 	subs	xzr, x8, x9
   a5af4:	54000061 	b.ne	a5b00 <JNI_OnLoad@@Base+0x12c8>  // b.any
   a5af8:	aa1303e0 	orr	x0, xzr, x19
   a5afc:	97ffe424 	bl	9eb8c <JNI_OnUnload@@Base+0x69bac>
   a5b00:	940009b4 	bl	a81d0 <__stack_chk_fail@plt>
   a5b04:	97fe3d45 	bl	35018 <JNI_OnUnload@@Base+0x38>
   a5b08:	d101c3ff 	sub	sp, sp, #0x70
   a5b0c:	a9037bfd 	stp	x29, x30, [sp, #48]
   a5b10:	a9045ff8 	stp	x24, x23, [sp, #64]
   a5b14:	a90557f6 	stp	x22, x21, [sp, #80]
   a5b18:	a9064ff4 	stp	x20, x19, [sp, #96]
   a5b1c:	9100c3fd 	add	x29, sp, #0x30
   a5b20:	d53bd057 	mrs	x23, tpidr_el0
   a5b24:	aa0203f4 	orr	x20, xzr, x2
   a5b28:	aa0103f5 	orr	x21, xzr, x1
   a5b2c:	f94016e8 	ldr	x8, [x23, #40]
   a5b30:	2a0003f6 	orr	w22, wzr, w0
   a5b34:	f81f83a8 	stur	x8, [x29, #-8]
   a5b38:	d50320ff 	hint	#0x7
   a5b3c:	aa1e03f3 	orr	x19, xzr, x30
   a5b40:	f9000ffe 	str	x30, [sp, #24]
   a5b44:	b4000161 	cbz	x1, a5b70 <JNI_OnLoad@@Base+0x1338>
   a5b48:	f101c69f 	subs	xzr, x20, #0x71
   a5b4c:	54000123 	b.cc	a5b70 <JNI_OnLoad@@Base+0x1338>  // b.lo, b.ul, b.last
   a5b50:	528cac88 	movz	w8, #0x6564
   a5b54:	72a14f08 	movk	w8, #0xa78, lsl #16
   a5b58:	b81f43a8 	stur	w8, [x29, #-12]
   a5b5c:	d10033a1 	sub	x1, x29, #0xc
   a5b60:	aa1503e0 	orr	x0, xzr, x21
   a5b64:	52800082 	movz	w2, #0x4
   a5b68:	97fe7f94 	bl	459b8 <JNI_OnUnload@@Base+0x109d8>
   a5b6c:	34000320 	cbz	w0, a5bd0 <JNI_OnLoad@@Base+0x1398>
   a5b70:	d503201f 	hint	#0x0
   a5b74:	10fffca0 	adr	x0, a5b08 <JNI_OnLoad@@Base+0x12d0>
   a5b78:	97fea6d3 	bl	4f6c4 <JNI_OnUnload@@Base+0x1a6e4>
   a5b7c:	aa0003e8 	orr	x8, xzr, x0
   a5b80:	2a1603e0 	orr	w0, wzr, w22
   a5b84:	aa1503e1 	orr	x1, xzr, x21
   a5b88:	aa1403e2 	orr	x2, xzr, x20
   a5b8c:	d63f0100 	blr	x8
   a5b90:	aa0003f4 	orr	x20, xzr, x0
   a5b94:	97fea6cf 	bl	4f6d0 <JNI_OnUnload@@Base+0x1a6f0>
   a5b98:	35000060 	cbnz	w0, a5ba4 <JNI_OnLoad@@Base+0x136c>
   a5b9c:	aa1303e0 	orr	x0, xzr, x19
   a5ba0:	97fea6cb 	bl	4f6cc <JNI_OnUnload@@Base+0x1a6ec>
   a5ba4:	f94016e8 	ldr	x8, [x23, #40]
   a5ba8:	f85f83a9 	ldur	x9, [x29, #-8]
   a5bac:	eb09011f 	subs	xzr, x8, x9
   a5bb0:	54000681 	b.ne	a5c80 <JNI_OnLoad@@Base+0x1448>  // b.any
   a5bb4:	aa1403e0 	orr	x0, xzr, x20
   a5bb8:	a9464ff4 	ldp	x20, x19, [sp, #96]
   a5bbc:	a94557f6 	ldp	x22, x21, [sp, #80]
   a5bc0:	a9445ff8 	ldp	x24, x23, [sp, #64]
   a5bc4:	a9437bfd 	ldp	x29, x30, [sp, #48]
   a5bc8:	9101c3ff 	add	sp, sp, #0x70
   a5bcc:	d65f03c0 	ret
   a5bd0:	910003e8 	add	x8, sp, #0x0
   a5bd4:	910026a0 	add	x0, x21, #0x9
   a5bd8:	52800281 	movz	w1, #0x14
   a5bdc:	910003f8 	add	x24, sp, #0x0
   a5be0:	97fe7791 	bl	43a24 <JNI_OnUnload@@Base+0xea44>
   a5be4:	90000068 	adrp	x8, b1000 <_ZTVNSt6__ndk117bad_function_callE@@Base+0x42b8>
   a5be8:	b240030c 	orr	x12, x24, #0x1
   a5bec:	f945b108 	ldr	x8, [x8, #2912]
   a5bf0:	394003e9 	ldrb	w9, [sp]
   a5bf4:	f9400bea 	ldr	x10, [sp, #16]
   a5bf8:	3941810b 	ldrb	w11, [x8, #96]
   a5bfc:	7200013f 	ands	wzr, w9, #0x1
   a5c00:	f9403909 	ldr	x9, [x8, #112]
   a5c04:	9a8a0180 	csel	x0, x12, x10, eq	// eq = none
   a5c08:	91018508 	add	x8, x8, #0x61
   a5c0c:	7200017f 	ands	wzr, w11, #0x1
   a5c10:	9a890101 	csel	x1, x8, x9, eq	// eq = none
   a5c14:	52800502 	movz	w2, #0x28
   a5c18:	97fe7f9d 	bl	45a8c <JNI_OnUnload@@Base+0x10aac>
   a5c1c:	35000040 	cbnz	w0, a5c24 <JNI_OnLoad@@Base+0x13ec>
   a5c20:	940003bd 	bl	a6b14 <JNI_OnLoad@@Base+0x22dc>
   a5c24:	394003e8 	ldrb	w8, [sp]
   a5c28:	3607fa48 	tbz	w8, #0, a5b70 <JNI_OnLoad@@Base+0x1338>
   a5c2c:	f9400be0 	ldr	x0, [sp, #16]
   a5c30:	97ffdb39 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a5c34:	17ffffcf 	b	a5b70 <JNI_OnLoad@@Base+0x1338>
   a5c38:	14000009 	b	a5c5c <JNI_OnLoad@@Base+0x1424>
   a5c3c:	394003e8 	ldrb	w8, [sp]
   a5c40:	aa0003f3 	orr	x19, xzr, x0
   a5c44:	360000e8 	tbz	w8, #0, a5c60 <JNI_OnLoad@@Base+0x1428>
   a5c48:	f9400be0 	ldr	x0, [sp, #16]
   a5c4c:	97ffdb32 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a5c50:	14000004 	b	a5c60 <JNI_OnLoad@@Base+0x1428>
   a5c54:	14000002 	b	a5c5c <JNI_OnLoad@@Base+0x1424>
   a5c58:	97fe3cf0 	bl	35018 <JNI_OnUnload@@Base+0x38>
   a5c5c:	aa0003f3 	orr	x19, xzr, x0
   a5c60:	910063e0 	add	x0, sp, #0x18
   a5c64:	97fe6e33 	bl	41530 <JNI_OnUnload@@Base+0xc550>
   a5c68:	f94016e8 	ldr	x8, [x23, #40]
   a5c6c:	f85f83a9 	ldur	x9, [x29, #-8]
   a5c70:	eb09011f 	subs	xzr, x8, x9
   a5c74:	54000061 	b.ne	a5c80 <JNI_OnLoad@@Base+0x1448>  // b.any
   a5c78:	aa1303e0 	orr	x0, xzr, x19
   a5c7c:	97ffe3c4 	bl	9eb8c <JNI_OnUnload@@Base+0x69bac>
   a5c80:	94000954 	bl	a81d0 <__stack_chk_fail@plt>
   a5c84:	a9bc7bfd 	stp	x29, x30, [sp, #-64]!
   a5c88:	a9015ffc 	stp	x28, x23, [sp, #16]
   a5c8c:	a90257f6 	stp	x22, x21, [sp, #32]
   a5c90:	a9034ff4 	stp	x20, x19, [sp, #48]
   a5c94:	910003fd 	add	x29, sp, #0x0
   a5c98:	d108c3ff 	sub	sp, sp, #0x230
   a5c9c:	d53bd056 	mrs	x22, tpidr_el0
   a5ca0:	6f00e400 	movi	v0.2d, #0x0
   a5ca4:	910083f7 	add	x23, sp, #0x20
   a5ca8:	f94016c8 	ldr	x8, [x22, #40]
   a5cac:	aa0203f3 	orr	x19, xzr, x2
   a5cb0:	aa0103f4 	orr	x20, xzr, x1
   a5cb4:	aa0003f5 	orr	x21, xzr, x0
   a5cb8:	f81f83a8 	stur	x8, [x29, #-8]
   a5cbc:	910083e8 	add	x8, sp, #0x20
   a5cc0:	ad0802e0 	stp	q0, q0, [x23, #256]
   a5cc4:	ad0902e0 	stp	q0, q0, [x23, #288]
   a5cc8:	ad0a02e0 	stp	q0, q0, [x23, #320]
   a5ccc:	ad0b02e0 	stp	q0, q0, [x23, #352]
   a5cd0:	ad0c02e0 	stp	q0, q0, [x23, #384]
   a5cd4:	ad0d02e0 	stp	q0, q0, [x23, #416]
   a5cd8:	ad0e02e0 	stp	q0, q0, [x23, #448]
   a5cdc:	ad0f02e0 	stp	q0, q0, [x23, #480]
   a5ce0:	97fe7576 	bl	432b8 <JNI_OnUnload@@Base+0xe2d8>
   a5ce4:	394083e8 	ldrb	w8, [sp, #32]
   a5ce8:	f9401be9 	ldr	x9, [sp, #48]
   a5cec:	b24002ea 	orr	x10, x23, #0x1
   a5cf0:	7200011f 	ands	wzr, w8, #0x1
   a5cf4:	9a890144 	csel	x4, x10, x9, eq	// eq = none
   a5cf8:	d0fffb83 	adrp	x3, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a5cfc:	91149863 	add	x3, x3, #0x526
   a5d00:	d0fffb85 	adrp	x5, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a5d04:	91256ca5 	add	x5, x5, #0x95b
   a5d08:	90fffb86 	adrp	x6, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a5d0c:	911d90c6 	add	x6, x6, #0x764
   a5d10:	910483e0 	add	x0, sp, #0x120
   a5d14:	52802001 	movz	w1, #0x100
   a5d18:	52802002 	movz	w2, #0x100
   a5d1c:	97fe720d 	bl	42550 <JNI_OnUnload@@Base+0xd570>
   a5d20:	394083e8 	ldrb	w8, [sp, #32]
   a5d24:	36000068 	tbz	w8, #0, a5d30 <JNI_OnLoad@@Base+0x14f8>
   a5d28:	f9401be0 	ldr	x0, [sp, #48]
   a5d2c:	97ffdafa 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a5d30:	6f00e400 	movi	v0.2d, #0x0
   a5d34:	910023e8 	add	x8, sp, #0x8
   a5d38:	aa1503e0 	orr	x0, xzr, x21
   a5d3c:	910023f5 	add	x21, sp, #0x8
   a5d40:	ad0402e0 	stp	q0, q0, [x23, #128]
   a5d44:	ad0502e0 	stp	q0, q0, [x23, #160]
   a5d48:	ad0602e0 	stp	q0, q0, [x23, #192]
   a5d4c:	ad0702e0 	stp	q0, q0, [x23, #224]
   a5d50:	3d801ee0 	str	q0, [x23, #112]
   a5d54:	ad0183e0 	stp	q0, q0, [sp, #48]
   a5d58:	ad0283e0 	stp	q0, q0, [sp, #80]
   a5d5c:	ad0383e0 	stp	q0, q0, [sp, #112]
   a5d60:	3d800be0 	str	q0, [sp, #32]
   a5d64:	97fe7555 	bl	432b8 <JNI_OnUnload@@Base+0xe2d8>
   a5d68:	394023e8 	ldrb	w8, [sp, #8]
   a5d6c:	f9400fe9 	ldr	x9, [sp, #24]
   a5d70:	b24002aa 	orr	x10, x21, #0x1
   a5d74:	7200011f 	ands	wzr, w8, #0x1
   a5d78:	9a890144 	csel	x4, x10, x9, eq	// eq = none
   a5d7c:	d0fffb83 	adrp	x3, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a5d80:	91235863 	add	x3, x3, #0x8d6
   a5d84:	d0fffb85 	adrp	x5, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a5d88:	91256ca5 	add	x5, x5, #0x95b
   a5d8c:	910083e0 	add	x0, sp, #0x20
   a5d90:	52802001 	movz	w1, #0x100
   a5d94:	52802002 	movz	w2, #0x100
   a5d98:	97fe71ee 	bl	42550 <JNI_OnUnload@@Base+0xd570>
   a5d9c:	394023e8 	ldrb	w8, [sp, #8]
   a5da0:	36000068 	tbz	w8, #0, a5dac <JNI_OnLoad@@Base+0x1574>
   a5da4:	f9400fe0 	ldr	x0, [sp, #24]
   a5da8:	97ffdadb 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a5dac:	910083e0 	add	x0, sp, #0x20
   a5db0:	2a1f03e1 	orr	w1, wzr, wzr
   a5db4:	9400095f 	bl	a8330 <access@plt>
   a5db8:	340002c0 	cbz	w0, a5e10 <JNI_OnLoad@@Base+0x15d8>
   a5dbc:	910083e0 	add	x0, sp, #0x20
   a5dc0:	52803fa1 	movz	w1, #0x1fd
   a5dc4:	94000997 	bl	a8420 <mkdir@plt>
   a5dc8:	35000100 	cbnz	w0, a5de8 <JNI_OnLoad@@Base+0x15b0>
   a5dcc:	910483e0 	add	x0, sp, #0x120
   a5dd0:	aa1403e1 	orr	x1, xzr, x20
   a5dd4:	aa1303e2 	orr	x2, xzr, x19
   a5dd8:	94000024 	bl	a5e68 <JNI_OnLoad@@Base+0x1630>
   a5ddc:	910483e0 	add	x0, sp, #0x120
   a5de0:	52802481 	movz	w1, #0x124
   a5de4:	94000993 	bl	a8430 <chmod@plt>
   a5de8:	f94016c8 	ldr	x8, [x22, #40]
   a5dec:	f85f83a9 	ldur	x9, [x29, #-8]
   a5df0:	eb09011f 	subs	xzr, x8, x9
   a5df4:	54000381 	b.ne	a5e64 <JNI_OnLoad@@Base+0x162c>  // b.any
   a5df8:	9108c3ff 	add	sp, sp, #0x230
   a5dfc:	a9434ff4 	ldp	x20, x19, [sp, #48]
   a5e00:	a94257f6 	ldp	x22, x21, [sp, #32]
   a5e04:	a9415ffc 	ldp	x28, x23, [sp, #16]
   a5e08:	a8c47bfd 	ldp	x29, x30, [sp], #64
   a5e0c:	d65f03c0 	ret
   a5e10:	910483e0 	add	x0, sp, #0x120
   a5e14:	2a1f03e1 	orr	w1, wzr, wzr
   a5e18:	94000946 	bl	a8330 <access@plt>
   a5e1c:	35fffd80 	cbnz	w0, a5dcc <JNI_OnLoad@@Base+0x1594>
   a5e20:	17fffff2 	b	a5de8 <JNI_OnLoad@@Base+0x15b0>
   a5e24:	394023e8 	ldrb	w8, [sp, #8]
   a5e28:	aa0003f3 	orr	x19, xzr, x0
   a5e2c:	36000108 	tbz	w8, #0, a5e4c <JNI_OnLoad@@Base+0x1614>
   a5e30:	f9400fe0 	ldr	x0, [sp, #24]
   a5e34:	14000005 	b	a5e48 <JNI_OnLoad@@Base+0x1610>
   a5e38:	394083e8 	ldrb	w8, [sp, #32]
   a5e3c:	aa0003f3 	orr	x19, xzr, x0
   a5e40:	36000068 	tbz	w8, #0, a5e4c <JNI_OnLoad@@Base+0x1614>
   a5e44:	f9401be0 	ldr	x0, [sp, #48]
   a5e48:	97ffdab3 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a5e4c:	f94016c8 	ldr	x8, [x22, #40]
   a5e50:	f85f83a9 	ldur	x9, [x29, #-8]
   a5e54:	eb09011f 	subs	xzr, x8, x9
   a5e58:	54000061 	b.ne	a5e64 <JNI_OnLoad@@Base+0x162c>  // b.any
   a5e5c:	aa1303e0 	orr	x0, xzr, x19
   a5e60:	97ffe34b 	bl	9eb8c <JNI_OnUnload@@Base+0x69bac>
   a5e64:	940008db 	bl	a81d0 <__stack_chk_fail@plt>
   a5e68:	d10143ff 	sub	sp, sp, #0x50
   a5e6c:	a9027bfd 	stp	x29, x30, [sp, #32]
   a5e70:	a90357f6 	stp	x22, x21, [sp, #48]
   a5e74:	a9044ff4 	stp	x20, x19, [sp, #64]
   a5e78:	910083fd 	add	x29, sp, #0x20
   a5e7c:	d53bd056 	mrs	x22, tpidr_el0
   a5e80:	aa0103f5 	orr	x21, xzr, x1
   a5e84:	90fffb81 	adrp	x1, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a5e88:	912b6c21 	add	x1, x1, #0xadb
   a5e8c:	f94016c8 	ldr	x8, [x22, #40]
   a5e90:	aa0203f4 	orr	x20, xzr, x2
   a5e94:	f81f83a8 	stur	x8, [x29, #-8]
   a5e98:	94000932 	bl	a8360 <fopen@plt>
   a5e9c:	b4000440 	cbz	x0, a5f24 <JNI_OnLoad@@Base+0x16ec>
   a5ea0:	aa0003f3 	orr	x19, xzr, x0
   a5ea4:	90fffb82 	adrp	x2, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a5ea8:	912b7842 	add	x2, x2, #0xade
   a5eac:	910003e8 	add	x8, sp, #0x0
   a5eb0:	aa1503e0 	orr	x0, xzr, x21
   a5eb4:	aa1403e1 	orr	x1, xzr, x20
   a5eb8:	94000025 	bl	a5f4c <JNI_OnLoad@@Base+0x1714>
   a5ebc:	394043e8 	ldrb	w8, [sp, #16]
   a5ec0:	340002e8 	cbz	w8, a5f1c <JNI_OnLoad@@Base+0x16e4>
   a5ec4:	a94023e0 	ldp	x0, x8, [sp]
   a5ec8:	f100111f 	subs	xzr, x8, #0x4
   a5ecc:	54000062 	b.cs	a5ed8 <JNI_OnLoad@@Base+0x16a0>  // b.hs, b.nlast
   a5ed0:	b5000240 	cbnz	x0, a5f18 <JNI_OnLoad@@Base+0x16e0>
   a5ed4:	14000012 	b	a5f1c <JNI_OnLoad@@Base+0x16e4>
   a5ed8:	8b080009 	add	x9, x0, x8
   a5edc:	b85fc129 	ldur	w9, [x9, #-4]
   a5ee0:	340001c9 	cbz	w9, a5f18 <JNI_OnLoad@@Base+0x16e0>
   a5ee4:	5ac00922 	rev	w2, w9
   a5ee8:	11001049 	add	w9, w2, #0x4
   a5eec:	eb09011f 	subs	xzr, x8, x9
   a5ef0:	54000149 	b.ls	a5f18 <JNI_OnLoad@@Base+0x16e0>  // b.plast
   a5ef4:	cb020108 	sub	x8, x8, x2
   a5ef8:	aa0003f4 	orr	x20, xzr, x0
   a5efc:	52800021 	movz	w1, #0x1
   a5f00:	8b080008 	add	x8, x0, x8
   a5f04:	aa1303e3 	orr	x3, xzr, x19
   a5f08:	d1001108 	sub	x8, x8, #0x4
   a5f0c:	aa0803e0 	orr	x0, xzr, x8
   a5f10:	9400094c 	bl	a8440 <fwrite@plt>
   a5f14:	aa1403e0 	orr	x0, xzr, x20
   a5f18:	97ffda81 	bl	9c91c <JNI_OnUnload@@Base+0x6793c>
   a5f1c:	aa1303e0 	orr	x0, xzr, x19
   a5f20:	94000928 	bl	a83c0 <fclose@plt>
   a5f24:	f94016c8 	ldr	x8, [x22, #40]
   a5f28:	f85f83a9 	ldur	x9, [x29, #-8]
   a5f2c:	eb09011f 	subs	xzr, x8, x9
   a5f30:	540000c1 	b.ne	a5f48 <JNI_OnLoad@@Base+0x1710>  // b.any
   a5f34:	a9444ff4 	ldp	x20, x19, [sp, #64]
   a5f38:	a94357f6 	ldp	x22, x21, [sp, #48]
   a5f3c:	a9427bfd 	ldp	x29, x30, [sp, #32]
   a5f40:	910143ff 	add	sp, sp, #0x50
   a5f44:	d65f03c0 	ret
   a5f48:	940008a2 	bl	a81d0 <__stack_chk_fail@plt>
   a5f4c:	d10183ff 	sub	sp, sp, #0x60
   a5f50:	a9027bfd 	stp	x29, x30, [sp, #32]
   a5f54:	a9035ff8 	stp	x24, x23, [sp, #48]
   a5f58:	a90457f6 	stp	x22, x21, [sp, #64]
   a5f5c:	a9054ff4 	stp	x20, x19, [sp, #80]
   a5f60:	910083fd 	add	x29, sp, #0x20
   a5f64:	d53bd058 	mrs	x24, tpidr_el0
   a5f68:	aa0803f3 	orr	x19, xzr, x8
   a5f6c:	aa0203f5 	orr	x21, xzr, x2
   a5f70:	f9401708 	ldr	x8, [x24, #40]
   a5f74:	aa0103f4 	orr	x20, xzr, x1
   a5f78:	aa0003f6 	orr	x22, xzr, x0
   a5f7c:	f81f83a8 	stur	x8, [x29, #-8]
   a5f80:	97fe86df 	bl	47afc <JNI_OnUnload@@Base+0x12b1c>
   a5f84:	aa1603e1 	orr	x1, xzr, x22
   a5f88:	2a1403e2 	orr	w2, wzr, w20
   a5f8c:	aa0003f7 	orr	x23, xzr, x0
   a5f90:	f9000be0 	str	x0, [sp, #16]
   a5f94:	97fe86be 	bl	47a8c <JNI_OnUnload@@Base+0x12aac>
   a5f98:	aa1703e0 	orr	x0, xzr, x23
   a5f9c:	aa1f03e1 	orr	x1, xzr, xzr
   a5fa0:	52800022 	movz	w2, #0x1
   a5fa4:	97fe809a 	bl	4620c <JNI_OnUnload@@Base+0x1122c>
   a5fa8:	97fe86f6 	bl	47b80 <JNI_OnUnload@@Base+0x12ba0>
   a5fac:	aa1703e1 	orr	x1, xzr, x23
   a5fb0:	52800022 	movz	w2, #0x1
   a5fb4:	aa0003f4 	orr	x20, xzr, x0
   a5fb8:	f90007e0 	str	x0, [sp, #8]
   a5fbc:	97fe8709 	bl	47be0 <JNI_OnUnload@@Base+0x12c00>
   a5fc0:	35000580 	cbnz	w0, a6070 <JNI_OnLoad@@Base+0x1838>
   a5fc4:	aa1403e0 	orr	x0, xzr, x20
   a5fc8:	97fe9304 	bl	4abd8 <JNI_OnUnload@@Base+0x15bf8>
   a5fcc:	35000520 	cbnz	w0, a6070 <JNI_OnLoad@@Base+0x1838>
   a5fd0:	1400000a 	b	a5ff8 <JNI_OnLoad@@Base+0x17c0>
   a5fd4:	f94003f4 	ldr	x20, [sp]
   a5fd8:	aa1503e1 	orr	x1, xzr, x21
   a5fdc:	f9402e80 	ldr	x0, [x20, #88]
   a5fe0:	940008f4 	bl	a83b0 <strcmp@plt>
   a5fe4:	34000160 	cbz	w0, a6010 <JNI_OnLoad@@Base+0x17d8>
   a5fe8:	f94007f4 	ldr	x20, [sp, #8]
   a5fec:	aa1403e0 	orr	x0, xzr, x20
   a5ff0:	97fe9318 	bl	4ac50 <JNI_OnUnload@@Base+0x15c70>
   a5ff4:	350003e0 	cbnz	w0, a6070 <JNI_OnLoad@@Base+0x1838>
   a5ff8:	910003e1 	add	x1, sp, #0x0
   a5ffc:	aa1403e0 	orr	x0, xzr, x20
   a6000:	f90003ff 	str	xzr, [sp]
   a6004:	97fe92ea 	bl	4abac <JNI_OnUnload@@Base+0x15bcc>
   a6008:	34fffe60 	cbz	w0, a5fd4 <JNI_OnLoad@@Base+0x179c>
   a600c:	14000018 	b	a606c <JNI_OnLoad@@Base+0x1834>
   a6010:	f9401a88 	ldr	x8, [x20, #48]
   a6014:	b40002c8 	cbz	x8, a606c <JNI_OnLoad@@Base+0x1834>
   a6018:	f94007f4 	ldr	x20, [sp, #8]
   a601c:	2a1f03e1 	orr	w1, wzr, wzr
   a6020:	aa1f03e2 	orr	x2, xzr, xzr
   a6024:	aa1403e0 	orr	x0, xzr, x20
   a6028:	97fe8a6d 	bl	489dc <JNI_OnUnload@@Base+0x139fc>
   a602c:	35000220 	cbnz	w0, a6070 <JNI_OnLoad@@Base+0x1838>
   a6030:	f94003f6 	ldr	x22, [sp]
   a6034:	f9401ad7 	ldr	x23, [x22, #48]
   a6038:	910006e0 	add	x0, x23, #0x1
   a603c:	97ffda34 	bl	9c90c <JNI_OnUnload@@Base+0x6792c>
   a6040:	910006e2 	add	x2, x23, #0x1
   a6044:	2a1f03e1 	orr	w1, wzr, wzr
   a6048:	aa0003f5 	orr	x21, xzr, x0
   a604c:	94000895 	bl	a82a0 <memset@plt>
   a6050:	b94032c2 	ldr	w2, [x22, #48]
   a6054:	aa1403e0 	orr	x0, xzr, x20
   a6058:	aa1503e1 	orr	x1, xzr, x21
   a605c:	97fe9023 	bl	4a0e8 <JNI_OnUnload@@Base+0x15108>
   a6060:	f94003e8 	ldr	x8, [sp]
   a6064:	f9401916 	ldr	x22, [x8, #48]
   a6068:	14000004 	b	a6078 <JNI_OnLoad@@Base+0x1840>
   a606c:	f94007f4 	ldr	x20, [sp, #8]
   a6070:	aa1f03f6 	orr	x22, xzr, xzr
   a6074:	aa1f03f5 	orr	x21, xzr, xzr
   a6078:	aa1403e0 	orr	x0, xzr, x20
   a607c:	97fe8944 	bl	4858c <JNI_OnUnload@@Base+0x135ac>
   a6080:	910023e0 	add	x0, sp, #0x8
   a6084:	97fe86cb 	bl	47bb0 <JNI_OnUnload@@Base+0x12bd0>
   a6088:	910043e0 	add	x0, sp, #0x10
   a608c:	97fe86a9 	bl	47b30 <JNI_OnUnload@@Base+0x12b50>
   a6090:	b4000095 	cbz	x21, a60a0 <JNI_OnLoad@@Base+0x1868>
   a6094:	a9005a75 	stp	x21, x22, [x19]
   a6098:	52800028 	movz	w8, #0x1
   a609c:	14000003 	b	a60a8 <JNI_OnLoad@@Base+0x1870>
   a60a0:	2a1f03e8 	orr	w8, wzr, wzr
   a60a4:	3900027f 	strb	wzr, [x19]
   a60a8:	39004268 	strb	w8, [x19, #16]
   a60ac:	f9401708 	ldr	x8, [x24, #40]
   a60b0:	f85f83a9 	ldur	x9, [x29, #-8]
   a60b4:	eb09011f 	subs	xzr, x8, x9
   a60b8:	540000e1 	b.ne	a60d4 <JNI_OnLoad@@Base+0x189c>  // b.any
   a60bc:	a9454ff4 	ldp	x20, x19, [sp, #80]
   a60c0:	a94457f6 	ldp	x22, x21, [sp, #64]
   a60c4:	a9435ff8 	ldp	x24, x23, [sp, #48]
   a60c8:	a9427bfd 	ldp	x29, x30, [sp, #32]
   a60cc:	910183ff 	add	sp, sp, #0x60
   a60d0:	d65f03c0 	ret
   a60d4:	9400083f 	bl	a81d0 <__stack_chk_fail@plt>
   a60d8:	d105c3ff 	sub	sp, sp, #0x170
   a60dc:	a9117bfd 	stp	x29, x30, [sp, #272]
   a60e0:	a9126ffc 	stp	x28, x27, [sp, #288]
   a60e4:	a91367fa 	stp	x26, x25, [sp, #304]
   a60e8:	a9145ff8 	stp	x24, x23, [sp, #320]
   a60ec:	a91557f6 	stp	x22, x21, [sp, #336]
   a60f0:	a9164ff4 	stp	x20, x19, [sp, #352]
   a60f4:	910443fd 	add	x29, sp, #0x110
   a60f8:	a93b17a4 	stp	x4, x5, [x29, #-80]
   a60fc:	aa0103f7 	orr	x23, xzr, x1
   a6100:	d503201f 	hint	#0x0
   a6104:	30b8d161 	adr	x1, 17b31 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x13b9>
   a6108:	a93c1fa6 	stp	x6, x7, [x29, #-64]
   a610c:	2a0303f4 	orr	w20, wzr, w3
   a6110:	ad0207e0 	stp	q0, q1, [sp, #64]
   a6114:	aa0203f5 	orr	x21, xzr, x2
   a6118:	ad030fe2 	stp	q2, q3, [sp, #96]
   a611c:	ad0417e4 	stp	q4, q5, [sp, #128]
   a6120:	ad051fe6 	stp	q6, q7, [sp, #160]
   a6124:	d53bd053 	mrs	x19, tpidr_el0
   a6128:	f9401668 	ldr	x8, [x19, #40]
   a612c:	f81f03a8 	stur	x8, [x29, #-16]
   a6130:	9400088c 	bl	a8360 <fopen@plt>
   a6134:	b4000da0 	cbz	x0, a62e8 <JNI_OnLoad@@Base+0x1ab0>
   a6138:	aa1f03e1 	orr	x1, xzr, xzr
   a613c:	52800042 	movz	w2, #0x2
   a6140:	aa0003f6 	orr	x22, xzr, x0
   a6144:	940008db 	bl	a84b0 <fseek@plt>
   a6148:	aa1603e0 	orr	x0, xzr, x22
   a614c:	940008dd 	bl	a84c0 <ftell@plt>
   a6150:	aa0003f8 	orr	x24, xzr, x0
   a6154:	aa1603e0 	orr	x0, xzr, x22
   a6158:	aa1f03e1 	orr	x1, xzr, xzr
   a615c:	2a1f03e2 	orr	w2, wzr, wzr
   a6160:	940008d4 	bl	a84b0 <fseek@plt>
   a6164:	aa1803e0 	orr	x0, xzr, x24
   a6168:	52800021 	movz	w1, #0x1
   a616c:	94000841 	bl	a8270 <calloc@plt>
   a6170:	52800021 	movz	w1, #0x1
   a6174:	aa1803e2 	orr	x2, xzr, x24
   a6178:	aa1603e3 	orr	x3, xzr, x22
   a617c:	aa0003f9 	orr	x25, xzr, x0
   a6180:	940008d4 	bl	a84d0 <fread@plt>
   a6184:	79407b28 	ldrh	w8, [x25, #60]
   a6188:	34000a88 	cbz	w8, a62d8 <JNI_OnLoad@@Base+0x1aa0>
   a618c:	f9401729 	ldr	x9, [x25, #40]
   a6190:	8b190129 	add	x9, x9, x25
   a6194:	9100813b 	add	x27, x9, #0x20
   a6198:	b85e4369 	ldur	w9, [x27, #-28]
   a619c:	71000d3f 	subs	wzr, w9, #0x3
   a61a0:	540000a0 	b.eq	a61b4 <JNI_OnLoad@@Base+0x197c>  // b.none
   a61a4:	71000508 	subs	w8, w8, #0x1
   a61a8:	9101037b 	add	x27, x27, #0x40
   a61ac:	54ffff61 	b.ne	a6198 <JNI_OnLoad@@Base+0x1960>  // b.any
   a61b0:	1400004a 	b	a62d8 <JNI_OnLoad@@Base+0x1aa0>
   a61b4:	f9400368 	ldr	x8, [x27]
   a61b8:	a901e7f3 	stp	x19, x25, [sp, #24]
   a61bc:	a900d7f7 	stp	x23, x21, [sp, #8]
   a61c0:	f100051f 	subs	xzr, x8, #0x1
   a61c4:	5400086b 	b.lt	a62d0 <JNI_OnLoad@@Base+0x1a98>  // b.tstop
   a61c8:	f85f8368 	ldur	x8, [x27, #-8]
   a61cc:	f94013e9 	ldr	x9, [sp, #32]
   a61d0:	8b080138 	add	x24, x9, x8
   a61d4:	910103e8 	add	x8, sp, #0x40
   a61d8:	d10143a9 	sub	x9, x29, #0x50
   a61dc:	9102010a 	add	x10, x8, #0x80
   a61e0:	91008128 	add	x8, x9, #0x20
   a61e4:	f9001ff8 	str	x24, [sp, #56]
   a61e8:	a902abe8 	stp	x8, x10, [sp, #40]
   a61ec:	1400000a 	b	a6214 <JNI_OnLoad@@Base+0x19dc>
   a61f0:	6b14035f 	subs	wzr, w26, w20
   a61f4:	54000620 	b.eq	a62b8 <JNI_OnLoad@@Base+0x1a80>  // b.none
   a61f8:	8b180329 	add	x9, x25, x24
   a61fc:	f9400368 	ldr	x8, [x27]
   a6200:	91000538 	add	x24, x9, #0x1
   a6204:	f9401fe9 	ldr	x9, [sp, #56]
   a6208:	8b080128 	add	x8, x9, x8
   a620c:	eb08031f 	subs	xzr, x24, x8
   a6210:	54000602 	b.cs	a62d0 <JNI_OnLoad@@Base+0x1a98>  // b.hs, b.nlast
   a6214:	aa1803e0 	orr	x0, xzr, x24
   a6218:	940007fa 	bl	a8200 <strlen@plt>
   a621c:	aa0003f9 	orr	x25, xzr, x0
   a6220:	b4fffec0 	cbz	x0, a61f8 <JNI_OnLoad@@Base+0x19c0>
   a6224:	928003e8 	movn	x8, #0x1f
   a6228:	f9401be9 	ldr	x9, [sp, #48]
   a622c:	7100069f 	subs	wzr, w20, #0x1
   a6230:	f2dff008 	movk	x8, #0xff80, lsl #32
   a6234:	2a1f03fa 	orr	w26, wzr, wzr
   a6238:	f81e83a8 	stur	x8, [x29, #-24]
   a623c:	910183a8 	add	x8, x29, #0x60
   a6240:	f81d03a8 	stur	x8, [x29, #-48]
   a6244:	f94017e8 	ldr	x8, [sp, #40]
   a6248:	a93da7a8 	stp	x8, x9, [x29, #-40]
   a624c:	54fffd2b 	b.lt	a61f0 <JNI_OnLoad@@Base+0x19b8>  // b.tstop
   a6250:	a97d73a8 	ldp	x8, x28, [x29, #-48]
   a6254:	2a1403f5 	orr	w21, wzr, w20
   a6258:	b85e83a9 	ldur	w9, [x29, #-24]
   a625c:	1400000d 	b	a6290 <JNI_OnLoad@@Base+0x1a58>
   a6260:	8b29c389 	add	x9, x28, w9, sxtw
   a6264:	aa0803f3 	orr	x19, xzr, x8
   a6268:	aa0903e8 	orr	x8, xzr, x9
   a626c:	f9400101 	ldr	x1, [x8]
   a6270:	aa1803e0 	orr	x0, xzr, x24
   a6274:	94000833 	bl	a8340 <strstr@plt>
   a6278:	f100001f 	subs	xzr, x0, #0x0
   a627c:	2a1703e9 	orr	w9, wzr, w23
   a6280:	aa1303e8 	orr	x8, xzr, x19
   a6284:	1a9a075a 	csinc	w26, w26, w26, eq	// eq = none
   a6288:	710006b5 	subs	w21, w21, #0x1
   a628c:	54fffb20 	b.eq	a61f0 <JNI_OnLoad@@Base+0x19b8>  // b.none
   a6290:	37f80069 	tbnz	w9, #31, a629c <JNI_OnLoad@@Base+0x1a64>
   a6294:	2a0903f7 	orr	w23, wzr, w9
   a6298:	14000005 	b	a62ac <JNI_OnLoad@@Base+0x1a74>
   a629c:	11002137 	add	w23, w9, #0x8
   a62a0:	3100213f 	adds	wzr, w9, #0x8
   a62a4:	b81e83b7 	stur	w23, [x29, #-24]
   a62a8:	54fffdc9 	b.ls	a6260 <JNI_OnLoad@@Base+0x1a28>  // b.plast
   a62ac:	91002113 	add	x19, x8, #0x8
   a62b0:	f81d03b3 	stur	x19, [x29, #-48]
   a62b4:	17ffffee 	b	a626c <JNI_OnLoad@@Base+0x1a34>
   a62b8:	a9408be0 	ldp	x0, x2, [sp, #8]
   a62bc:	90fffb83 	adrp	x3, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a62c0:	912f7863 	add	x3, x3, #0xbde
   a62c4:	92800001 	movn	x1, #0x0
   a62c8:	aa1803e4 	orr	x4, xzr, x24
   a62cc:	97fe70a1 	bl	42550 <JNI_OnUnload@@Base+0xd570>
   a62d0:	a941e7f3 	ldp	x19, x25, [sp, #24]
   a62d4:	b4000079 	cbz	x25, a62e0 <JNI_OnLoad@@Base+0x1aa8>
   a62d8:	aa1903e0 	orr	x0, xzr, x25
   a62dc:	940007d9 	bl	a8240 <free@plt>
   a62e0:	aa1603e0 	orr	x0, xzr, x22
   a62e4:	94000837 	bl	a83c0 <fclose@plt>
   a62e8:	f9401668 	ldr	x8, [x19, #40]
   a62ec:	f85f03a9 	ldur	x9, [x29, #-16]
   a62f0:	eb09011f 	subs	xzr, x8, x9
   a62f4:	54000121 	b.ne	a6318 <JNI_OnLoad@@Base+0x1ae0>  // b.any
   a62f8:	a9564ff4 	ldp	x20, x19, [sp, #352]
   a62fc:	a95557f6 	ldp	x22, x21, [sp, #336]
   a6300:	a9545ff8 	ldp	x24, x23, [sp, #320]
   a6304:	a95367fa 	ldp	x26, x25, [sp, #304]
   a6308:	a9526ffc 	ldp	x28, x27, [sp, #288]
   a630c:	a9517bfd 	ldp	x29, x30, [sp, #272]
   a6310:	9105c3ff 	add	sp, sp, #0x170
   a6314:	d65f03c0 	ret
   a6318:	940007ae 	bl	a81d0 <__stack_chk_fail@plt>
   a631c:	a9ba7bfd 	stp	x29, x30, [sp, #-96]!
   a6320:	a9016ffc 	stp	x28, x27, [sp, #16]
   a6324:	a90267fa 	stp	x26, x25, [sp, #32]
   a6328:	a9035ff8 	stp	x24, x23, [sp, #48]
   a632c:	a90457f6 	stp	x22, x21, [sp, #64]
   a6330:	a9054ff4 	stp	x20, x19, [sp, #80]
   a6334:	910003fd 	add	x29, sp, #0x0
   a6338:	d11003ff 	sub	sp, sp, #0x400
   a633c:	ad0087e0 	stp	q0, q1, [sp, #16]
   a6340:	6f00e400 	movi	v0.2d, #0x0
   a6344:	2a0003fa 	orr	w26, wzr, w0
   a6348:	a9098be1 	stp	x1, x2, [sp, #152]
   a634c:	d503201f 	hint	#0x0
   a6350:	10082fc0 	adr	x0, b6948 <DPT_UNKNOWN_DATA@@Base+0x4bf>
   a6354:	a90a93e3 	stp	x3, x4, [sp, #168]
   a6358:	a90b9be5 	stp	x5, x6, [sp, #184]
   a635c:	f90067e7 	str	x7, [sp, #200]
   a6360:	ad018fe2 	stp	q2, q3, [sp, #48]
   a6364:	ad0297e4 	stp	q4, q5, [sp, #80]
   a6368:	ad039fe6 	stp	q6, q7, [sp, #112]
   a636c:	d53bd057 	mrs	x23, tpidr_el0
   a6370:	f94016e8 	ldr	x8, [x23, #40]
   a6374:	f81f03a8 	stur	x8, [x29, #-16]
   a6378:	ad3b83a0 	stp	q0, q0, [x29, #-144]
   a637c:	ad3c83a0 	stp	q0, q0, [x29, #-112]
   a6380:	ad3d83a0 	stp	q0, q0, [x29, #-80]
   a6384:	ad3e83a0 	stp	q0, q0, [x29, #-48]
   a6388:	97ffde95 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a638c:	39400008 	ldrb	w8, [x0]
   a6390:	36001488 	tbz	w8, #0, a6620 <JNI_OnLoad@@Base+0x1de8>
   a6394:	d503201f 	hint	#0x0
   a6398:	10082c80 	adr	x0, b6928 <DPT_UNKNOWN_DATA@@Base+0x49f>
   a639c:	97ffde90 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a63a0:	39403808 	ldrb	w8, [x0, #14]
   a63a4:	aa0003f4 	orr	x20, xzr, x0
   a63a8:	340002c8 	cbz	w8, a6400 <JNI_OnLoad@@Base+0x1bc8>
   a63ac:	bd400a80 	ldr	s0, [x20, #8]
   a63b0:	d0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a63b4:	fd400282 	ldr	d2, [x20]
   a63b8:	fd453d01 	ldr	d1, [x8, #2680]
   a63bc:	d0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a63c0:	52800e29 	movz	w9, #0x71
   a63c4:	2f08a400 	ushll	v0.8h, v0.8b, #0
   a63c8:	3940368a 	ldrb	w10, [x20, #13]
   a63cc:	39003a9f 	strb	wzr, [x20, #14]
   a63d0:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a63d4:	fd460101 	ldr	d1, [x8, #3072]
   a63d8:	39403288 	ldrb	w8, [x20, #12]
   a63dc:	2e211c41 	eor	v1.8b, v2.8b, v1.8b
   a63e0:	4a090108 	eor	w8, w8, w9
   a63e4:	52801129 	movz	w9, #0x89
   a63e8:	0e212800 	xtn	v0.8b, v0.8h
   a63ec:	4a090149 	eor	w9, w10, w9
   a63f0:	39003288 	strb	w8, [x20, #12]
   a63f4:	39003689 	strb	w9, [x20, #13]
   a63f8:	fd000281 	str	d1, [x20]
   a63fc:	bd000a80 	str	s0, [x20, #8]
   a6400:	940007d4 	bl	a8350 <getpid@plt>
   a6404:	2a0003e4 	orr	w4, wzr, w0
   a6408:	d10243a0 	sub	x0, x29, #0x90
   a640c:	52801001 	movz	w1, #0x80
   a6410:	52801002 	movz	w2, #0x80
   a6414:	aa1403e3 	orr	x3, xzr, x20
   a6418:	97fe704e 	bl	42550 <JNI_OnUnload@@Base+0xd570>
   a641c:	d503201f 	hint	#0x0
   a6420:	30b8b881 	adr	x1, 17b31 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x13b9>
   a6424:	d10243a0 	sub	x0, x29, #0x90
   a6428:	940007ce 	bl	a8360 <fopen@plt>
   a642c:	d503201f 	hint	#0x0
   a6430:	50b967c8 	adr	x8, 1912a <_ZTSNSt6__ndk117bad_function_callE@@Base+0x240>
   a6434:	aa0003f4 	orr	x20, xzr, x0
   a6438:	ad400500 	ldp	q0, q1, [x8]
   a643c:	52801768 	movz	w8, #0xbb
   a6440:	d503201f 	hint	#0x0
   a6444:	10082a20 	adr	x0, b6988 <DPT_UNKNOWN_DATA@@Base+0x4ff>
   a6448:	390643e8 	strb	w8, [sp, #400]
   a644c:	ad0b87e0 	stp	q0, q1, [sp, #368]
   a6450:	97ffde63 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6454:	39400008 	ldrb	w8, [x0]
   a6458:	36001148 	tbz	w8, #0, a6680 <JNI_OnLoad@@Base+0x1e48>
   a645c:	d503201f 	hint	#0x0
   a6460:	10082840 	adr	x0, b6968 <DPT_UNKNOWN_DATA@@Base+0x4df>
   a6464:	97ffde5e 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6468:	39408408 	ldrb	w8, [x0, #33]
   a646c:	aa0003f5 	orr	x21, xzr, x0
   a6470:	34000168 	cbz	w8, a649c <JNI_OnLoad@@Base+0x1c64>
   a6474:	d0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a6478:	ad400aa1 	ldp	q1, q2, [x21]
   a647c:	3dc3a500 	ldr	q0, [x8, #3728]
   a6480:	394082a8 	ldrb	w8, [x21, #32]
   a6484:	390086bf 	strb	wzr, [x21, #33]
   a6488:	6e201c21 	eor	v1.16b, v1.16b, v0.16b
   a648c:	6e201c40 	eor	v0.16b, v2.16b, v0.16b
   a6490:	5201e908 	eor	w8, w8, #0xbbbbbbbb
   a6494:	390082a8 	strb	w8, [x21, #32]
   a6498:	ad0002a1 	stp	q1, q0, [x21]
   a649c:	b4000a74 	cbz	x20, a65e8 <JNI_OnLoad@@Base+0x1db0>
   a64a0:	6f00e400 	movi	v0.2d, #0x0
   a64a4:	9105c3e0 	add	x0, sp, #0x170
   a64a8:	52804001 	movz	w1, #0x200
   a64ac:	aa1403e2 	orr	x2, xzr, x20
   a64b0:	f90007f7 	str	x23, [sp, #8]
   a64b4:	ad0b83e0 	stp	q0, q0, [sp, #368]
   a64b8:	ad0c83e0 	stp	q0, q0, [sp, #400]
   a64bc:	ad0d83e0 	stp	q0, q0, [sp, #432]
   a64c0:	ad0e83e0 	stp	q0, q0, [sp, #464]
   a64c4:	ad0f83e0 	stp	q0, q0, [sp, #496]
   a64c8:	ad1083e0 	stp	q0, q0, [sp, #528]
   a64cc:	ad1183e0 	stp	q0, q0, [sp, #560]
   a64d0:	ad1283e0 	stp	q0, q0, [sp, #592]
   a64d4:	ad1383e0 	stp	q0, q0, [sp, #624]
   a64d8:	ad1483e0 	stp	q0, q0, [sp, #656]
   a64dc:	ad1583e0 	stp	q0, q0, [sp, #688]
   a64e0:	ad1683e0 	stp	q0, q0, [sp, #720]
   a64e4:	ad1783e0 	stp	q0, q0, [sp, #752]
   a64e8:	ad1883e0 	stp	q0, q0, [sp, #784]
   a64ec:	ad1983e0 	stp	q0, q0, [sp, #816]
   a64f0:	ad1a83e0 	stp	q0, q0, [sp, #848]
   a64f4:	940007a3 	bl	a8380 <fgets@plt>
   a64f8:	2a1f03f6 	orr	w22, wzr, wzr
   a64fc:	b40006e0 	cbz	x0, a65d8 <JNI_OnLoad@@Base+0x1da0>
   a6500:	910043e8 	add	x8, sp, #0x10
   a6504:	910263e9 	add	x9, sp, #0x98
   a6508:	2a1f03f8 	orr	w24, wzr, wzr
   a650c:	52850019 	movz	w25, #0x2800
   a6510:	910183bb 	add	x27, x29, #0x60
   a6514:	9102011c 	add	x28, x8, #0x80
   a6518:	9100e137 	add	x23, x9, #0x38
   a651c:	14000007 	b	a6538 <JNI_OnLoad@@Base+0x1d00>
   a6520:	9105c3e0 	add	x0, sp, #0x170
   a6524:	52804001 	movz	w1, #0x200
   a6528:	aa1403e2 	orr	x2, xzr, x20
   a652c:	11000718 	add	w24, w24, #0x1
   a6530:	94000794 	bl	a8380 <fgets@plt>
   a6534:	b4000520 	cbz	x0, a65d8 <JNI_OnLoad@@Base+0x1da0>
   a6538:	6b19031f 	subs	wzr, w24, w25
   a653c:	540004e0 	b.eq	a65d8 <JNI_OnLoad@@Base+0x1da0>  // b.none
   a6540:	6f00e400 	movi	v0.2d, #0x0
   a6544:	9105c3e0 	add	x0, sp, #0x170
   a6548:	9103c3e2 	add	x2, sp, #0xf0
   a654c:	aa1503e1 	orr	x1, xzr, x21
   a6550:	ad0783e0 	stp	q0, q0, [sp, #240]
   a6554:	ad0883e0 	stp	q0, q0, [sp, #272]
   a6558:	ad0983e0 	stp	q0, q0, [sp, #304]
   a655c:	ad0a83e0 	stp	q0, q0, [sp, #336]
   a6560:	9400078c 	bl	a8390 <sscanf@plt>
   a6564:	7100041f 	subs	wzr, w0, #0x1
   a6568:	54fffdc1 	b.ne	a6520 <JNI_OnLoad@@Base+0x1ce8>  // b.any
   a656c:	928006e8 	movn	x8, #0x37
   a6570:	7100075f 	subs	wzr, w26, #0x1
   a6574:	2a1a03f3 	orr	w19, wzr, w26
   a6578:	f2dff008 	movk	x8, #0xff80, lsl #32
   a657c:	a90d5ffb 	stp	x27, x23, [sp, #208]
   a6580:	a90e23fc 	stp	x28, x8, [sp, #224]
   a6584:	5400018a 	b.ge	a65b4 <JNI_OnLoad@@Base+0x1d7c>  // b.tcont
   a6588:	17ffffe6 	b	a6520 <JNI_OnLoad@@Base+0x1ce8>
   a658c:	f9406be8 	ldr	x8, [sp, #208]
   a6590:	91002109 	add	x9, x8, #0x8
   a6594:	f9006be9 	str	x9, [sp, #208]
   a6598:	f9400101 	ldr	x1, [x8]
   a659c:	9103c3e0 	add	x0, sp, #0xf0
   a65a0:	97fe7d18 	bl	45a00 <JNI_OnUnload@@Base+0x10a20>
   a65a4:	f100001f 	subs	xzr, x0, #0x0
   a65a8:	1a9606d6 	csinc	w22, w22, w22, eq	// eq = none
   a65ac:	71000673 	subs	w19, w19, #0x1
   a65b0:	54fffb80 	b.eq	a6520 <JNI_OnLoad@@Base+0x1ce8>  // b.none
   a65b4:	b980ebe8 	ldrsw	x8, [sp, #232]
   a65b8:	36fffea8 	tbz	w8, #31, a658c <JNI_OnLoad@@Base+0x1d54>
   a65bc:	11002109 	add	w9, w8, #0x8
   a65c0:	3100211f 	adds	wzr, w8, #0x8
   a65c4:	b900ebe9 	str	w9, [sp, #232]
   a65c8:	54fffe28 	b.hi	a658c <JNI_OnLoad@@Base+0x1d54>  // b.pmore
   a65cc:	f9406fe9 	ldr	x9, [sp, #216]
   a65d0:	8b080128 	add	x8, x9, x8
   a65d4:	17fffff1 	b	a6598 <JNI_OnLoad@@Base+0x1d60>
   a65d8:	aa1403e0 	orr	x0, xzr, x20
   a65dc:	94000779 	bl	a83c0 <fclose@plt>
   a65e0:	f94007f7 	ldr	x23, [sp, #8]
   a65e4:	14000002 	b	a65ec <JNI_OnLoad@@Base+0x1db4>
   a65e8:	2a1f03f6 	orr	w22, wzr, wzr
   a65ec:	f94016e8 	ldr	x8, [x23, #40]
   a65f0:	f85f03a9 	ldur	x9, [x29, #-16]
   a65f4:	eb09011f 	subs	xzr, x8, x9
   a65f8:	54000681 	b.ne	a66c8 <JNI_OnLoad@@Base+0x1e90>  // b.any
   a65fc:	2a1603e0 	orr	w0, wzr, w22
   a6600:	911003ff 	add	sp, sp, #0x400
   a6604:	a9454ff4 	ldp	x20, x19, [sp, #80]
   a6608:	a94457f6 	ldp	x22, x21, [sp, #64]
   a660c:	a9435ff8 	ldp	x24, x23, [sp, #48]
   a6610:	a94267fa 	ldp	x26, x25, [sp, #32]
   a6614:	a9416ffc 	ldp	x28, x27, [sp, #16]
   a6618:	a8c67bfd 	ldp	x29, x30, [sp], #96
   a661c:	d65f03c0 	ret
   a6620:	d503201f 	hint	#0x0
   a6624:	10081820 	adr	x0, b6928 <DPT_UNKNOWN_DATA@@Base+0x49f>
   a6628:	97ffdded 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a662c:	d0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a6630:	d0fffb69 	adrp	x9, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a6634:	52800033 	movz	w19, #0x1
   a6638:	fd45e900 	ldr	d0, [x8, #3024]
   a663c:	fd455121 	ldr	d1, [x9, #2720]
   a6640:	52912048 	movz	w8, #0x8902
   a6644:	aa0003e1 	orr	x1, xzr, x0
   a6648:	39003813 	strb	w19, [x0, #14]
   a664c:	d503201f 	hint	#0x0
   a6650:	10032202 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a6654:	fd000000 	str	d0, [x0]
   a6658:	bd000801 	str	s1, [x0, #8]
   a665c:	79001808 	strh	w8, [x0, #12]
   a6660:	d503201f 	hint	#0x0
   a6664:	10cec820 	adr	x0, 43f68 <JNI_OnUnload@@Base+0xef88>
   a6668:	97ffdd43 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a666c:	d503201f 	hint	#0x0
   a6670:	100816c0 	adr	x0, b6948 <DPT_UNKNOWN_DATA@@Base+0x4bf>
   a6674:	97ffddda 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6678:	39000013 	strb	w19, [x0]
   a667c:	17ffff46 	b	a6394 <JNI_OnLoad@@Base+0x1b5c>
   a6680:	d503201f 	hint	#0x0
   a6684:	10081720 	adr	x0, b6968 <DPT_UNKNOWN_DATA@@Base+0x4df>
   a6688:	97ffddd5 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a668c:	9105c3e1 	add	x1, sp, #0x170
   a6690:	aa0003f5 	orr	x21, xzr, x0
   a6694:	97fe7638 	bl	43f74 <JNI_OnUnload@@Base+0xef94>
   a6698:	d503201f 	hint	#0x0
   a669c:	10cecf60 	adr	x0, 44088 <JNI_OnUnload@@Base+0xf0a8>
   a66a0:	d503201f 	hint	#0x0
   a66a4:	10031f62 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a66a8:	aa1503e1 	orr	x1, xzr, x21
   a66ac:	97ffdd32 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a66b0:	d503201f 	hint	#0x0
   a66b4:	100816a0 	adr	x0, b6988 <DPT_UNKNOWN_DATA@@Base+0x4ff>
   a66b8:	97ffddc9 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a66bc:	52800028 	movz	w8, #0x1
   a66c0:	39000008 	strb	w8, [x0]
   a66c4:	17ffff66 	b	a645c <JNI_OnLoad@@Base+0x1c24>
   a66c8:	940006c2 	bl	a81d0 <__stack_chk_fail@plt>
   a66cc:	a9ba7bfd 	stp	x29, x30, [sp, #-96]!
   a66d0:	a9016ffc 	stp	x28, x27, [sp, #16]
   a66d4:	a90267fa 	stp	x26, x25, [sp, #32]
   a66d8:	a9035ff8 	stp	x24, x23, [sp, #48]
   a66dc:	a90457f6 	stp	x22, x21, [sp, #64]
   a66e0:	a9054ff4 	stp	x20, x19, [sp, #80]
   a66e4:	910003fd 	add	x29, sp, #0x0
   a66e8:	d10e83ff 	sub	sp, sp, #0x3a0
   a66ec:	ad0187e0 	stp	q0, q1, [sp, #48]
   a66f0:	6f00e400 	movi	v0.2d, #0x0
   a66f4:	2a0003f8 	orr	w24, wzr, w0
   a66f8:	a90b8be1 	stp	x1, x2, [sp, #184]
   a66fc:	a90c93e3 	stp	x3, x4, [sp, #200]
   a6700:	a90d9be5 	stp	x5, x6, [sp, #216]
   a6704:	f90077e7 	str	x7, [sp, #232]
   a6708:	ad028fe2 	stp	q2, q3, [sp, #80]
   a670c:	ad0397e4 	stp	q4, q5, [sp, #112]
   a6710:	ad049fe6 	stp	q6, q7, [sp, #144]
   a6714:	d53bd05c 	mrs	x28, tpidr_el0
   a6718:	f9401788 	ldr	x8, [x28, #40]
   a671c:	f81f03a8 	stur	x8, [x29, #-16]
   a6720:	ad3b83a0 	stp	q0, q0, [x29, #-144]
   a6724:	ad3c83a0 	stp	q0, q0, [x29, #-112]
   a6728:	ad3d83a0 	stp	q0, q0, [x29, #-80]
   a672c:	ad3e83a0 	stp	q0, q0, [x29, #-48]
   a6730:	94000708 	bl	a8350 <getpid@plt>
   a6734:	2a0003f4 	orr	w20, wzr, w0
   a6738:	d503201f 	hint	#0x0
   a673c:	10081460 	adr	x0, b69c8 <DPT_UNKNOWN_DATA@@Base+0x53f>
   a6740:	97ffdda7 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6744:	39400008 	ldrb	w8, [x0]
   a6748:	360018c8 	tbz	w8, #0, a6a60 <JNI_OnLoad@@Base+0x2228>
   a674c:	d503201f 	hint	#0x0
   a6750:	100812c0 	adr	x0, b69a8 <DPT_UNKNOWN_DATA@@Base+0x51f>
   a6754:	97ffdda2 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6758:	39403808 	ldrb	w8, [x0, #14]
   a675c:	aa0003e3 	orr	x3, xzr, x0
   a6760:	340002c8 	cbz	w8, a67b8 <JNI_OnLoad@@Base+0x1f80>
   a6764:	bd400860 	ldr	s0, [x3, #8]
   a6768:	d0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a676c:	fd400062 	ldr	d2, [x3]
   a6770:	fd44d501 	ldr	d1, [x8, #2472]
   a6774:	d0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a6778:	528019a9 	movz	w9, #0xcd
   a677c:	2f08a400 	ushll	v0.8h, v0.8b, #0
   a6780:	3940346a 	ldrb	w10, [x3, #13]
   a6784:	3900387f 	strb	wzr, [x3, #14]
   a6788:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a678c:	fd457101 	ldr	d1, [x8, #2784]
   a6790:	39403068 	ldrb	w8, [x3, #12]
   a6794:	2e211c41 	eor	v1.8b, v2.8b, v1.8b
   a6798:	4a090108 	eor	w8, w8, w9
   a679c:	528002a9 	movz	w9, #0x15
   a67a0:	0e212800 	xtn	v0.8b, v0.8h
   a67a4:	4a090149 	eor	w9, w10, w9
   a67a8:	39003068 	strb	w8, [x3, #12]
   a67ac:	39003469 	strb	w9, [x3, #13]
   a67b0:	fd000061 	str	d1, [x3]
   a67b4:	bd000860 	str	s0, [x3, #8]
   a67b8:	d10243a0 	sub	x0, x29, #0x90
   a67bc:	52801001 	movz	w1, #0x80
   a67c0:	52801002 	movz	w2, #0x80
   a67c4:	2a1403e4 	orr	w4, wzr, w20
   a67c8:	97fe6f62 	bl	42550 <JNI_OnUnload@@Base+0xd570>
   a67cc:	d10243a0 	sub	x0, x29, #0x90
   a67d0:	94000744 	bl	a84e0 <opendir@plt>
   a67d4:	b40012a0 	cbz	x0, a6a28 <JNI_OnLoad@@Base+0x21f0>
   a67d8:	aa0003f5 	orr	x21, xzr, x0
   a67dc:	d503201f 	hint	#0x0
   a67e0:	10081140 	adr	x0, b6a08 <DPT_UNKNOWN_DATA@@Base+0x57f>
   a67e4:	97ffdd7e 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a67e8:	39400008 	ldrb	w8, [x0]
   a67ec:	360016a8 	tbz	w8, #0, a6ac0 <JNI_OnLoad@@Base+0x2288>
   a67f0:	d503201f 	hint	#0x0
   a67f4:	10080fa0 	adr	x0, b69e8 <DPT_UNKNOWN_DATA@@Base+0x55f>
   a67f8:	97ffdd79 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a67fc:	39401408 	ldrb	w8, [x0, #5]
   a6800:	aa0003f6 	orr	x22, xzr, x0
   a6804:	340001a8 	cbz	w8, a6838 <JNI_OnLoad@@Base+0x2000>
   a6808:	bd4002c0 	ldr	s0, [x22]
   a680c:	d0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a6810:	52800de9 	movz	w9, #0x6f
   a6814:	fd450901 	ldr	d1, [x8, #2576]
   a6818:	394012c8 	ldrb	w8, [x22, #4]
   a681c:	390016df 	strb	wzr, [x22, #5]
   a6820:	2f08a400 	ushll	v0.8h, v0.8b, #0
   a6824:	4a090108 	eor	w8, w8, w9
   a6828:	390012c8 	strb	w8, [x22, #4]
   a682c:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a6830:	0e212800 	xtn	v0.8b, v0.8h
   a6834:	bd0002c0 	str	s0, [x22]
   a6838:	910443e8 	add	x8, sp, #0x110
   a683c:	9102e3e9 	add	x9, sp, #0xb8
   a6840:	2a1f03f7 	orr	w23, wzr, wzr
   a6844:	91000519 	add	x25, x8, #0x1
   a6848:	9100c3e8 	add	x8, sp, #0x30
   a684c:	90fffb93 	adrp	x19, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a6850:	912f8673 	add	x19, x19, #0xbe1
   a6854:	d503201f 	hint	#0x0
   a6858:	30b896da 	adr	x26, 17b31 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x13b9>
   a685c:	9102011b 	add	x27, x8, #0x80
   a6860:	9100e128 	add	x8, x9, #0x38
   a6864:	f90017e8 	str	x8, [sp, #40]
   a6868:	aa1503e0 	orr	x0, xzr, x21
   a686c:	94000721 	bl	a84f0 <readdir@plt>
   a6870:	b4000d60 	cbz	x0, a6a1c <JNI_OnLoad@@Base+0x21e4>
   a6874:	38413c08 	ldrb	w8, [x0, #19]!
   a6878:	5100e908 	sub	w8, w8, #0x3a
   a687c:	3100291f 	adds	wzr, w8, #0xa
   a6880:	54ffff43 	b.cc	a6868 <JNI_OnLoad@@Base+0x2030>  // b.lo, b.ul, b.last
   a6884:	9400064f 	bl	a81c0 <atoi@plt>
   a6888:	6b14001f 	subs	wzr, w0, w20
   a688c:	54fffee0 	b.eq	a6868 <JNI_OnLoad@@Base+0x2030>  // b.none
   a6890:	6f00e400 	movi	v0.2d, #0x0
   a6894:	2a0003e5 	orr	w5, wzr, w0
   a6898:	910843e0 	add	x0, sp, #0x210
   a689c:	d10243a4 	sub	x4, x29, #0x90
   a68a0:	52802001 	movz	w1, #0x100
   a68a4:	52802002 	movz	w2, #0x100
   a68a8:	aa1303e3 	orr	x3, xzr, x19
   a68ac:	aa1603e6 	orr	x6, xzr, x22
   a68b0:	ad1083e0 	stp	q0, q0, [sp, #528]
   a68b4:	ad1183e0 	stp	q0, q0, [sp, #560]
   a68b8:	ad1283e0 	stp	q0, q0, [sp, #592]
   a68bc:	ad1383e0 	stp	q0, q0, [sp, #624]
   a68c0:	ad1483e0 	stp	q0, q0, [sp, #656]
   a68c4:	ad1583e0 	stp	q0, q0, [sp, #688]
   a68c8:	ad1683e0 	stp	q0, q0, [sp, #720]
   a68cc:	ad1783e0 	stp	q0, q0, [sp, #752]
   a68d0:	97fe6f20 	bl	42550 <JNI_OnUnload@@Base+0xd570>
   a68d4:	910843e0 	add	x0, sp, #0x210
   a68d8:	aa1a03e1 	orr	x1, xzr, x26
   a68dc:	940006a1 	bl	a8360 <fopen@plt>
   a68e0:	6f00e400 	movi	v0.2d, #0x0
   a68e4:	ad0883e0 	stp	q0, q0, [sp, #272]
   a68e8:	ad0983e0 	stp	q0, q0, [sp, #304]
   a68ec:	ad0a83e0 	stp	q0, q0, [sp, #336]
   a68f0:	ad0b83e0 	stp	q0, q0, [sp, #368]
   a68f4:	ad0c83e0 	stp	q0, q0, [sp, #400]
   a68f8:	ad0d83e0 	stp	q0, q0, [sp, #432]
   a68fc:	ad0e83e0 	stp	q0, q0, [sp, #464]
   a6900:	ad0f83e0 	stp	q0, q0, [sp, #496]
   a6904:	b4fffb20 	cbz	x0, a6868 <JNI_OnLoad@@Base+0x2030>
   a6908:	aa0003e2 	orr	x2, xzr, x0
   a690c:	928006fa 	movn	x26, #0x37
   a6910:	910443e0 	add	x0, sp, #0x110
   a6914:	52802001 	movz	w1, #0x100
   a6918:	a901efe2 	stp	x2, x27, [sp, #24]
   a691c:	f2dff01a 	movk	x26, #0xff80, lsl #32
   a6920:	94000698 	bl	a8380 <fgets@plt>
   a6924:	910443e0 	add	x0, sp, #0x110
   a6928:	52802001 	movz	w1, #0x100
   a692c:	9400064d 	bl	a8260 <strnlen@plt>
   a6930:	aa1f03fb 	orr	x27, xzr, xzr
   a6934:	aa1903e8 	orr	x8, xzr, x25
   a6938:	b4000120 	cbz	x0, a695c <JNI_OnLoad@@Base+0x2124>
   a693c:	385ff109 	ldurb	w9, [x8, #-1]
   a6940:	d1000400 	sub	x0, x0, #0x1
   a6944:	7100a13f 	subs	wzr, w9, #0x28
   a6948:	9a9b011b 	csel	x27, x8, x27, eq	// eq = none
   a694c:	7100a53f 	subs	wzr, w9, #0x29
   a6950:	91000508 	add	x8, x8, #0x1
   a6954:	54ffff21 	b.ne	a6938 <JNI_OnLoad@@Base+0x2100>  // b.any
   a6958:	381fe11f 	sturb	wzr, [x8, #-2]
   a695c:	b400055b 	cbz	x27, a6a04 <JNI_OnLoad@@Base+0x21cc>
   a6960:	f94013e8 	ldr	x8, [sp, #32]
   a6964:	7100071f 	subs	wzr, w24, #0x1
   a6968:	f90007f9 	str	x25, [sp, #8]
   a696c:	b90017f8 	str	w24, [sp, #20]
   a6970:	a9106be8 	stp	x8, x26, [sp, #256]
   a6974:	910183a8 	add	x8, x29, #0x60
   a6978:	f9007be8 	str	x8, [sp, #240]
   a697c:	f94017e8 	ldr	x8, [sp, #40]
   a6980:	f9007fe8 	str	x8, [sp, #248]
   a6984:	5400038b 	b.lt	a69f4 <JNI_OnLoad@@Base+0x21bc>  // b.tstop
   a6988:	a94f67e8 	ldp	x8, x25, [sp, #240]
   a698c:	2a1803f3 	orr	w19, wzr, w24
   a6990:	b9410be9 	ldr	w9, [sp, #264]
   a6994:	1400000e 	b	a69cc <JNI_OnLoad@@Base+0x2194>
   a6998:	8b29c329 	add	x9, x25, w9, sxtw
   a699c:	aa0803fa 	orr	x26, xzr, x8
   a69a0:	aa0903e8 	orr	x8, xzr, x9
   a69a4:	f9400101 	ldr	x1, [x8]
   a69a8:	aa1b03e0 	orr	x0, xzr, x27
   a69ac:	52802002 	movz	w2, #0x100
   a69b0:	9400060c 	bl	a81e0 <strncmp@plt>
   a69b4:	7100001f 	subs	wzr, w0, #0x0
   a69b8:	2a1803e9 	orr	w9, wzr, w24
   a69bc:	aa1a03e8 	orr	x8, xzr, x26
   a69c0:	1a9716f7 	csinc	w23, w23, w23, ne	// ne = any
   a69c4:	71000673 	subs	w19, w19, #0x1
   a69c8:	54000160 	b.eq	a69f4 <JNI_OnLoad@@Base+0x21bc>  // b.none
   a69cc:	37f80069 	tbnz	w9, #31, a69d8 <JNI_OnLoad@@Base+0x21a0>
   a69d0:	2a0903f8 	orr	w24, wzr, w9
   a69d4:	14000005 	b	a69e8 <JNI_OnLoad@@Base+0x21b0>
   a69d8:	11002138 	add	w24, w9, #0x8
   a69dc:	3100213f 	adds	wzr, w9, #0x8
   a69e0:	b9010bf8 	str	w24, [sp, #264]
   a69e4:	54fffda9 	b.ls	a6998 <JNI_OnLoad@@Base+0x2160>  // b.plast
   a69e8:	9100211a 	add	x26, x8, #0x8
   a69ec:	f9007bfa 	str	x26, [sp, #240]
   a69f0:	17ffffed 	b	a69a4 <JNI_OnLoad@@Base+0x216c>
   a69f4:	b94017f8 	ldr	w24, [sp, #20]
   a69f8:	f94007f9 	ldr	x25, [sp, #8]
   a69fc:	90fffb93 	adrp	x19, 16000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x2eea>
   a6a00:	912f8673 	add	x19, x19, #0xbe1
   a6a04:	f9400fe0 	ldr	x0, [sp, #24]
   a6a08:	9400066e 	bl	a83c0 <fclose@plt>
   a6a0c:	f94013fb 	ldr	x27, [sp, #32]
   a6a10:	d503201f 	hint	#0x0
   a6a14:	30b888fa 	adr	x26, 17b31 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x13b9>
   a6a18:	17ffff94 	b	a6868 <JNI_OnLoad@@Base+0x2030>
   a6a1c:	aa1503e0 	orr	x0, xzr, x21
   a6a20:	940006b8 	bl	a8500 <closedir@plt>
   a6a24:	14000002 	b	a6a2c <JNI_OnLoad@@Base+0x21f4>
   a6a28:	2a1f03f7 	orr	w23, wzr, wzr
   a6a2c:	f9401788 	ldr	x8, [x28, #40]
   a6a30:	f85f03a9 	ldur	x9, [x29, #-16]
   a6a34:	eb09011f 	subs	xzr, x8, x9
   a6a38:	540006c1 	b.ne	a6b10 <JNI_OnLoad@@Base+0x22d8>  // b.any
   a6a3c:	2a1703e0 	orr	w0, wzr, w23
   a6a40:	910e83ff 	add	sp, sp, #0x3a0
   a6a44:	a9454ff4 	ldp	x20, x19, [sp, #80]
   a6a48:	a94457f6 	ldp	x22, x21, [sp, #64]
   a6a4c:	a9435ff8 	ldp	x24, x23, [sp, #48]
   a6a50:	a94267fa 	ldp	x26, x25, [sp, #32]
   a6a54:	a9416ffc 	ldp	x28, x27, [sp, #16]
   a6a58:	a8c67bfd 	ldp	x29, x30, [sp], #96
   a6a5c:	d65f03c0 	ret
   a6a60:	d503201f 	hint	#0x0
   a6a64:	1007fa20 	adr	x0, b69a8 <DPT_UNKNOWN_DATA@@Base+0x51f>
   a6a68:	97ffdcdd 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6a6c:	d0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a6a70:	d0fffb69 	adrp	x9, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a6a74:	52800033 	movz	w19, #0x1
   a6a78:	fd456d00 	ldr	d0, [x8, #2776]
   a6a7c:	fd459921 	ldr	d1, [x9, #2864]
   a6a80:	5282b4c8 	movz	w8, #0x15a6
   a6a84:	aa0003e1 	orr	x1, xzr, x0
   a6a88:	39003813 	strb	w19, [x0, #14]
   a6a8c:	d503201f 	hint	#0x0
   a6a90:	10030002 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a6a94:	fd000000 	str	d0, [x0]
   a6a98:	bd000801 	str	s1, [x0, #8]
   a6a9c:	79001808 	strh	w8, [x0, #12]
   a6aa0:	d503201f 	hint	#0x0
   a6aa4:	10ceafa0 	adr	x0, 44098 <JNI_OnUnload@@Base+0xf0b8>
   a6aa8:	97ffdc33 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a6aac:	d503201f 	hint	#0x0
   a6ab0:	1007f8c0 	adr	x0, b69c8 <DPT_UNKNOWN_DATA@@Base+0x53f>
   a6ab4:	97ffdcca 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6ab8:	39000013 	strb	w19, [x0]
   a6abc:	17ffff24 	b	a674c <JNI_OnLoad@@Base+0x1f14>
   a6ac0:	d503201f 	hint	#0x0
   a6ac4:	1007f920 	adr	x0, b69e8 <DPT_UNKNOWN_DATA@@Base+0x55f>
   a6ac8:	97ffdcc5 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6acc:	d0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a6ad0:	aa0003e1 	orr	x1, xzr, x0
   a6ad4:	d503201f 	hint	#0x0
   a6ad8:	1002fdc2 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a6adc:	fd45d900 	ldr	d0, [x8, #2992]
   a6ae0:	52802de8 	movz	w8, #0x16f
   a6ae4:	79000808 	strh	w8, [x0, #4]
   a6ae8:	bd000000 	str	s0, [x0]
   a6aec:	d503201f 	hint	#0x0
   a6af0:	10ceada0 	adr	x0, 440a4 <JNI_OnUnload@@Base+0xf0c4>
   a6af4:	97ffdc20 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a6af8:	d503201f 	hint	#0x0
   a6afc:	1007f860 	adr	x0, b6a08 <DPT_UNKNOWN_DATA@@Base+0x57f>
   a6b00:	97ffdcb7 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6b04:	52800028 	movz	w8, #0x1
   a6b08:	39000008 	strb	w8, [x0]
   a6b0c:	17ffff39 	b	a67f0 <JNI_OnLoad@@Base+0x1fb8>
   a6b10:	940005b0 	bl	a81d0 <__stack_chk_fail@plt>
   a6b14:	d280001e 	movz	x30, #0x0
   a6b18:	d65f03c0 	ret
   a6b1c:	d10183ff 	sub	sp, sp, #0x60
   a6b20:	a9037bfd 	stp	x29, x30, [sp, #48]
   a6b24:	f90023f5 	str	x21, [sp, #64]
   a6b28:	a9054ff4 	stp	x20, x19, [sp, #80]
   a6b2c:	9100c3fd 	add	x29, sp, #0x30
   a6b30:	d53bd054 	mrs	x20, tpidr_el0
   a6b34:	f0000048 	adrp	x8, b1000 <_ZTVNSt6__ndk117bad_function_callE@@Base+0x42b8>
   a6b38:	f9401689 	ldr	x9, [x20, #40]
   a6b3c:	f945b108 	ldr	x8, [x8, #2912]
   a6b40:	f81f83a9 	stur	x9, [x29, #-8]
   a6b44:	3941e109 	ldrb	w9, [x8, #120]
   a6b48:	f940410a 	ldr	x10, [x8, #128]
   a6b4c:	d341fd2b 	ubfm	x11, x9, #1, #63
   a6b50:	7200013f 	ands	wzr, w9, #0x1
   a6b54:	9a8a016a 	csel	x10, x11, x10, eq	// eq = none
   a6b58:	b400010a 	cbz	x10, a6b78 <JNI_OnLoad@@Base+0x2340>
   a6b5c:	f940450a 	ldr	x10, [x8, #136]
   a6b60:	9101e508 	add	x8, x8, #0x79
   a6b64:	7200013f 	ands	wzr, w9, #0x1
   a6b68:	9a8a0101 	csel	x1, x8, x10, eq	// eq = none
   a6b6c:	97fe6aef 	bl	41728 <JNI_OnUnload@@Base+0xc748>
   a6b70:	b5000460 	cbnz	x0, a6bfc <JNI_OnLoad@@Base+0x23c4>
   a6b74:	14000021 	b	a6bf8 <JNI_OnLoad@@Base+0x23c0>
   a6b78:	d503201f 	hint	#0x0
   a6b7c:	70b92e69 	adr	x9, 1914b <_ZTSNSt6__ndk117bad_function_callE@@Base+0x261>
   a6b80:	aa0003f5 	orr	x21, xzr, x0
   a6b84:	ad400520 	ldp	q0, q1, [x9]
   a6b88:	52800f68 	movz	w8, #0x7b
   a6b8c:	d503201f 	hint	#0x0
   a6b90:	1007f5c0 	adr	x0, b6a48 <DPT_UNKNOWN_DATA@@Base+0x5bf>
   a6b94:	390083e8 	strb	w8, [sp, #32]
   a6b98:	ad0007e0 	stp	q0, q1, [sp]
   a6b9c:	97ffdc90 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6ba0:	39400008 	ldrb	w8, [x0]
   a6ba4:	36000468 	tbz	w8, #0, a6c30 <JNI_OnLoad@@Base+0x23f8>
   a6ba8:	d503201f 	hint	#0x0
   a6bac:	1007f3e0 	adr	x0, b6a28 <DPT_UNKNOWN_DATA@@Base+0x59f>
   a6bb0:	97ffdc8b 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6bb4:	39408408 	ldrb	w8, [x0, #33]
   a6bb8:	aa0003e1 	orr	x1, xzr, x0
   a6bbc:	34000328 	cbz	w8, a6c20 <JNI_OnLoad@@Base+0x23e8>
   a6bc0:	f0fffb68 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a6bc4:	ad400821 	ldp	q1, q2, [x1]
   a6bc8:	3dc02500 	ldr	q0, [x8, #144]
   a6bcc:	39408028 	ldrb	w8, [x1, #32]
   a6bd0:	52800f69 	movz	w9, #0x7b
   a6bd4:	3900843f 	strb	wzr, [x1, #33]
   a6bd8:	aa1503e0 	orr	x0, xzr, x21
   a6bdc:	6e201c21 	eor	v1.16b, v1.16b, v0.16b
   a6be0:	6e201c40 	eor	v0.16b, v2.16b, v0.16b
   a6be4:	4a090108 	eor	w8, w8, w9
   a6be8:	39008028 	strb	w8, [x1, #32]
   a6bec:	ad000021 	stp	q1, q0, [x1]
   a6bf0:	97fe6ace 	bl	41728 <JNI_OnUnload@@Base+0xc748>
   a6bf4:	b5000040 	cbnz	x0, a6bfc <JNI_OnLoad@@Base+0x23c4>
   a6bf8:	97ffffc7 	bl	a6b14 <JNI_OnLoad@@Base+0x22dc>
   a6bfc:	f9401688 	ldr	x8, [x20, #40]
   a6c00:	f85f83a9 	ldur	x9, [x29, #-8]
   a6c04:	eb09011f 	subs	xzr, x8, x9
   a6c08:	54000381 	b.ne	a6c78 <JNI_OnLoad@@Base+0x2440>  // b.any
   a6c0c:	a9454ff4 	ldp	x20, x19, [sp, #80]
   a6c10:	f94023f5 	ldr	x21, [sp, #64]
   a6c14:	a9437bfd 	ldp	x29, x30, [sp, #48]
   a6c18:	910183ff 	add	sp, sp, #0x60
   a6c1c:	d65f03c0 	ret
   a6c20:	aa1503e0 	orr	x0, xzr, x21
   a6c24:	97fe6ac1 	bl	41728 <JNI_OnUnload@@Base+0xc748>
   a6c28:	b5fffea0 	cbnz	x0, a6bfc <JNI_OnLoad@@Base+0x23c4>
   a6c2c:	17fffff3 	b	a6bf8 <JNI_OnLoad@@Base+0x23c0>
   a6c30:	d503201f 	hint	#0x0
   a6c34:	1007efa0 	adr	x0, b6a28 <DPT_UNKNOWN_DATA@@Base+0x59f>
   a6c38:	97ffdc69 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6c3c:	910003e1 	add	x1, sp, #0x0
   a6c40:	aa0003f3 	orr	x19, xzr, x0
   a6c44:	97fe7731 	bl	44908 <JNI_OnUnload@@Base+0xf928>
   a6c48:	d503201f 	hint	#0x0
   a6c4c:	10ceee80 	adr	x0, 44a1c <JNI_OnUnload@@Base+0xfa3c>
   a6c50:	d503201f 	hint	#0x0
   a6c54:	1002f1e2 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a6c58:	aa1303e1 	orr	x1, xzr, x19
   a6c5c:	97ffdbc6 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a6c60:	d503201f 	hint	#0x0
   a6c64:	1007ef20 	adr	x0, b6a48 <DPT_UNKNOWN_DATA@@Base+0x5bf>
   a6c68:	97ffdc5d 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6c6c:	52800028 	movz	w8, #0x1
   a6c70:	39000008 	strb	w8, [x0]
   a6c74:	17ffffcd 	b	a6ba8 <JNI_OnLoad@@Base+0x2370>
   a6c78:	94000556 	bl	a81d0 <__stack_chk_fail@plt>
   a6c7c:	d103c3ff 	sub	sp, sp, #0xf0
   a6c80:	a9097bfd 	stp	x29, x30, [sp, #144]
   a6c84:	f90053fb 	str	x27, [sp, #160]
   a6c88:	a90b67fa 	stp	x26, x25, [sp, #176]
   a6c8c:	a90c5ff8 	stp	x24, x23, [sp, #192]
   a6c90:	a90d57f6 	stp	x22, x21, [sp, #208]
   a6c94:	a90e4ff4 	stp	x20, x19, [sp, #224]
   a6c98:	910243fd 	add	x29, sp, #0x90
   a6c9c:	6f00e400 	movi	v0.2d, #0x0
   a6ca0:	d53bd058 	mrs	x24, tpidr_el0
   a6ca4:	f0000040 	adrp	x0, b1000 <_ZTVNSt6__ndk117bad_function_callE@@Base+0x42b8>
   a6ca8:	f9401708 	ldr	x8, [x24, #40]
   a6cac:	d100c3a1 	sub	x1, x29, #0x30
   a6cb0:	f81f83a8 	stur	x8, [x29, #-8]
   a6cb4:	ad3e83a0 	stp	q0, q0, [x29, #-48]
   a6cb8:	f9460400 	ldr	x0, [x0, #3080]
   a6cbc:	9400054d 	bl	a81f0 <dladdr@plt>
   a6cc0:	34001820 	cbz	w0, a6fc4 <JNI_OnLoad@@Base+0x278c>
   a6cc4:	f85d83a8 	ldur	x8, [x29, #-40]
   a6cc8:	b40017e8 	cbz	x8, a6fc4 <JNI_OnLoad@@Base+0x278c>
   a6ccc:	f85d03b3 	ldur	x19, [x29, #-48]
   a6cd0:	a9047fff 	stp	xzr, xzr, [sp, #64]
   a6cd4:	f9002bff 	str	xzr, [sp, #80]
   a6cd8:	b4000493 	cbz	x19, a6d68 <JNI_OnLoad@@Base+0x2530>
   a6cdc:	39400268 	ldrb	w8, [x19]
   a6ce0:	7100bd1f 	subs	wzr, w8, #0x2f
   a6ce4:	54000221 	b.ne	a6d28 <JNI_OnLoad@@Base+0x24f0>  // b.any
   a6ce8:	aa1303e0 	orr	x0, xzr, x19
   a6cec:	94000545 	bl	a8200 <strlen@plt>
   a6cf0:	aa0003f4 	orr	x20, xzr, x0
   a6cf4:	f100581f 	subs	xzr, x0, #0x16
   a6cf8:	54000308 	b.hi	a6d58 <JNI_OnLoad@@Base+0x2520>  // b.pmore
   a6cfc:	531f7a88 	ubfm	w8, w20, #31, #30
   a6d00:	910103e9 	add	x9, sp, #0x40
   a6d04:	b2400135 	orr	x21, x9, #0x1
   a6d08:	390103e8 	strb	w8, [sp, #64]
   a6d0c:	b40000b4 	cbz	x20, a6d20 <JNI_OnLoad@@Base+0x24e8>
   a6d10:	aa1503e0 	orr	x0, xzr, x21
   a6d14:	aa1303e1 	orr	x1, xzr, x19
   a6d18:	aa1403e2 	orr	x2, xzr, x20
   a6d1c:	9400053d 	bl	a8210 <memmove@plt>
   a6d20:	38346abf 	strb	wzr, [x21, x20]
   a6d24:	14000011 	b	a6d68 <JNI_OnLoad@@Base+0x2530>
   a6d28:	910003e8 	add	x8, sp, #0x0
   a6d2c:	aa1303e0 	orr	x0, xzr, x19
   a6d30:	97fe6d24 	bl	421c0 <JNI_OnUnload@@Base+0xd1e0>
   a6d34:	394103e8 	ldrb	w8, [sp, #64]
   a6d38:	36000068 	tbz	w8, #0, a6d44 <JNI_OnLoad@@Base+0x250c>
   a6d3c:	f9402be0 	ldr	x0, [sp, #80]
   a6d40:	97ffd6f5 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a6d44:	3dc003e0 	ldr	q0, [sp]
   a6d48:	f9400be8 	ldr	x8, [sp, #16]
   a6d4c:	3d8013e0 	str	q0, [sp, #64]
   a6d50:	f9002be8 	str	x8, [sp, #80]
   a6d54:	14000005 	b	a6d68 <JNI_OnLoad@@Base+0x2530>
   a6d58:	910103e0 	add	x0, sp, #0x40
   a6d5c:	aa1303e1 	orr	x1, xzr, x19
   a6d60:	aa1403e2 	orr	x2, xzr, x20
   a6d64:	97fe38b1 	bl	35028 <JNI_OnUnload@@Base+0x48>
   a6d68:	394103e8 	ldrb	w8, [sp, #64]
   a6d6c:	f94027e9 	ldr	x9, [sp, #72]
   a6d70:	d341fd0a 	ubfm	x10, x8, #1, #63
   a6d74:	72000108 	ands	w8, w8, #0x1
   a6d78:	9a89014b 	csel	x11, x10, x9, eq	// eq = none
   a6d7c:	b50003eb 	cbnz	x11, a6df8 <JNI_OnLoad@@Base+0x25c0>
   a6d80:	d503201f 	hint	#0x0
   a6d84:	1007e820 	adr	x0, b6a88 <DPT_UNKNOWN_DATA@@Base+0x5ff>
   a6d88:	97ffdc15 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6d8c:	39400008 	ldrb	w8, [x0]
   a6d90:	360015c8 	tbz	w8, #0, a7048 <JNI_OnLoad@@Base+0x2810>
   a6d94:	d503201f 	hint	#0x0
   a6d98:	1007e680 	adr	x0, b6a68 <DPT_UNKNOWN_DATA@@Base+0x5df>
   a6d9c:	97ffdc10 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6da0:	39402008 	ldrb	w8, [x0, #8]
   a6da4:	340000e8 	cbz	w8, a6dc0 <JNI_OnLoad@@Base+0x2588>
   a6da8:	d0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a6dac:	fd400000 	ldr	d0, [x0]
   a6db0:	3900201f 	strb	wzr, [x0, #8]
   a6db4:	fd451501 	ldr	d1, [x8, #2600]
   a6db8:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a6dbc:	fd000000 	str	d0, [x0]
   a6dc0:	910003e8 	add	x8, sp, #0x0
   a6dc4:	97fe6cff 	bl	421c0 <JNI_OnUnload@@Base+0xd1e0>
   a6dc8:	394103e8 	ldrb	w8, [sp, #64]
   a6dcc:	36000068 	tbz	w8, #0, a6dd8 <JNI_OnLoad@@Base+0x25a0>
   a6dd0:	f9402be0 	ldr	x0, [sp, #80]
   a6dd4:	97ffd6d0 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a6dd8:	3dc003e0 	ldr	q0, [sp]
   a6ddc:	f9400beb 	ldr	x11, [sp, #16]
   a6de0:	3d8013e0 	str	q0, [sp, #64]
   a6de4:	394103e8 	ldrb	w8, [sp, #64]
   a6de8:	f94027e9 	ldr	x9, [sp, #72]
   a6dec:	f9002beb 	str	x11, [sp, #80]
   a6df0:	d341fd0a 	ubfm	x10, x8, #1, #63
   a6df4:	12000108 	and	w8, w8, #0x1
   a6df8:	7100011f 	subs	wzr, w8, #0x0
   a6dfc:	9a890149 	csel	x9, x10, x9, eq	// eq = none
   a6e00:	b4000dc9 	cbz	x9, a6fb8 <JNI_OnLoad@@Base+0x2780>
   a6e04:	6f00e400 	movi	v0.2d, #0x0
   a6e08:	f9402be9 	ldr	x9, [sp, #80]
   a6e0c:	910103ea 	add	x10, sp, #0x40
   a6e10:	7100011f 	subs	wzr, w8, #0x0
   a6e14:	b2400154 	orr	x20, x10, #0x1
   a6e18:	d503201f 	hint	#0x0
   a6e1c:	1007e560 	adr	x0, b6ac8 <DPT_UNKNOWN_DATA@@Base+0x63f>
   a6e20:	9a890293 	csel	x19, x20, x9, eq	// eq = none
   a6e24:	ad0003e0 	stp	q0, q0, [sp]
   a6e28:	ad0103e0 	stp	q0, q0, [sp, #32]
   a6e2c:	97ffdbec 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6e30:	39400008 	ldrb	w8, [x0]
   a6e34:	36000e08 	tbz	w8, #0, a6ff4 <JNI_OnLoad@@Base+0x27bc>
   a6e38:	d503201f 	hint	#0x0
   a6e3c:	1007e360 	adr	x0, b6aa8 <DPT_UNKNOWN_DATA@@Base+0x61f>
   a6e40:	97ffdbe7 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a6e44:	39401808 	ldrb	w8, [x0, #6]
   a6e48:	aa0003e2 	orr	x2, xzr, x0
   a6e4c:	34000228 	cbz	w8, a6e90 <JNI_OnLoad@@Base+0x2658>
   a6e50:	bd400040 	ldr	s0, [x2]
   a6e54:	d0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a6e58:	528019a9 	movz	w9, #0xcd
   a6e5c:	fd452d01 	ldr	d1, [x8, #2648]
   a6e60:	39401048 	ldrb	w8, [x2, #4]
   a6e64:	3940144a 	ldrb	w10, [x2, #5]
   a6e68:	2f08a400 	ushll	v0.8h, v0.8b, #0
   a6e6c:	3900185f 	strb	wzr, [x2, #6]
   a6e70:	4a090108 	eor	w8, w8, w9
   a6e74:	528003a9 	movz	w9, #0x1d
   a6e78:	4a090149 	eor	w9, w10, w9
   a6e7c:	39001048 	strb	w8, [x2, #4]
   a6e80:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a6e84:	39001449 	strb	w9, [x2, #5]
   a6e88:	0e212800 	xtn	v0.8b, v0.8h
   a6e8c:	bd000040 	str	s0, [x2]
   a6e90:	910003e0 	add	x0, sp, #0x0
   a6e94:	aa1303e1 	orr	x1, xzr, x19
   a6e98:	97fe729c 	bl	43908 <JNI_OnUnload@@Base+0xe928>
   a6e9c:	f94013e8 	ldr	x8, [sp, #32]
   a6ea0:	b4000888 	cbz	x8, a6fb0 <JNI_OnLoad@@Base+0x2778>
   a6ea4:	394103e8 	ldrb	w8, [sp, #64]
   a6ea8:	f9402be9 	ldr	x9, [sp, #80]
   a6eac:	d503201f 	hint	#0x0
   a6eb0:	30b86401 	adr	x1, 17b31 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x13b9>
   a6eb4:	7200011f 	ands	wzr, w8, #0x1
   a6eb8:	9a890280 	csel	x0, x20, x9, eq	// eq = none
   a6ebc:	94000529 	bl	a8360 <fopen@plt>
   a6ec0:	b4000780 	cbz	x0, a6fb0 <JNI_OnLoad@@Base+0x2778>
   a6ec4:	f9400fe1 	ldr	x1, [sp, #24]
   a6ec8:	2a1f03e2 	orr	w2, wzr, wzr
   a6ecc:	aa0003f4 	orr	x20, xzr, x0
   a6ed0:	94000578 	bl	a84b0 <fseek@plt>
   a6ed4:	34000080 	cbz	w0, a6ee4 <JNI_OnLoad@@Base+0x26ac>
   a6ed8:	aa1403e0 	orr	x0, xzr, x20
   a6edc:	94000539 	bl	a83c0 <fclose@plt>
   a6ee0:	14000034 	b	a6fb0 <JNI_OnLoad@@Base+0x2778>
   a6ee4:	f94013f5 	ldr	x21, [sp, #32]
   a6ee8:	aa1503e0 	orr	x0, xzr, x21
   a6eec:	940004cd 	bl	a8220 <malloc@plt>
   a6ef0:	b4ffff40 	cbz	x0, a6ed8 <JNI_OnLoad@@Base+0x26a0>
   a6ef4:	52800021 	movz	w1, #0x1
   a6ef8:	aa1503e2 	orr	x2, xzr, x21
   a6efc:	aa1403e3 	orr	x3, xzr, x20
   a6f00:	aa0003f3 	orr	x19, xzr, x0
   a6f04:	94000573 	bl	a84d0 <fread@plt>
   a6f08:	aa0003f5 	orr	x21, xzr, x0
   a6f0c:	aa1403e0 	orr	x0, xzr, x20
   a6f10:	9400052c 	bl	a83c0 <fclose@plt>
   a6f14:	f94013e8 	ldr	x8, [sp, #32]
   a6f18:	eb0802bf 	subs	xzr, x21, x8
   a6f1c:	54000461 	b.ne	a6fa8 <JNI_OnLoad@@Base+0x2770>  // b.any
   a6f20:	f85d83a8 	ldur	x8, [x29, #-40]
   a6f24:	f9400be9 	ldr	x9, [sp, #16]
   a6f28:	8b090114 	add	x20, x8, x9
   a6f2c:	aa1403e0 	orr	x0, xzr, x20
   a6f30:	aa1503e1 	orr	x1, xzr, x21
   a6f34:	97fe6f95 	bl	42d88 <JNI_OnUnload@@Base+0xdda8>
   a6f38:	36000380 	tbz	w0, #0, a6fa8 <JNI_OnLoad@@Base+0x2770>
   a6f3c:	f94013f9 	ldr	x25, [sp, #32]
   a6f40:	b4000359 	cbz	x25, a6fa8 <JNI_OnLoad@@Base+0x2770>
   a6f44:	aa1f03fa 	orr	x26, xzr, xzr
   a6f48:	2a1f03f5 	orr	w21, wzr, wzr
   a6f4c:	2a1f03f6 	orr	w22, wzr, wzr
   a6f50:	12b0001b 	movn	w27, #0x8000, lsl #16
   a6f54:	eb1b033f 	subs	xzr, x25, x27
   a6f58:	9a9b3337 	csel	x23, x25, x27, cc	// cc = lo, ul, last
   a6f5c:	8b1a0261 	add	x1, x19, x26
   a6f60:	2a1603e0 	orr	w0, wzr, w22
   a6f64:	2a1703e2 	orr	w2, wzr, w23
   a6f68:	97fe7ca3 	bl	461f4 <JNI_OnUnload@@Base+0x11214>
   a6f6c:	2a0003f6 	orr	w22, wzr, w0
   a6f70:	8b1a0281 	add	x1, x20, x26
   a6f74:	2a1503e0 	orr	w0, wzr, w21
   a6f78:	2a1703e2 	orr	w2, wzr, w23
   a6f7c:	97fe7c9e 	bl	461f4 <JNI_OnUnload@@Base+0x11214>
   a6f80:	2a0003f5 	orr	w21, wzr, w0
   a6f84:	eb170339 	subs	x25, x25, x23
   a6f88:	8b17035a 	add	x26, x26, x23
   a6f8c:	54fffe41 	b.ne	a6f54 <JNI_OnLoad@@Base+0x271c>  // b.any
   a6f90:	aa1303e0 	orr	x0, xzr, x19
   a6f94:	940004ab 	bl	a8240 <free@plt>
   a6f98:	6b1502df 	subs	wzr, w22, w21
   a6f9c:	540000a0 	b.eq	a6fb0 <JNI_OnLoad@@Base+0x2778>  // b.none
   a6fa0:	97fffedd 	bl	a6b14 <JNI_OnLoad@@Base+0x22dc>
   a6fa4:	14000003 	b	a6fb0 <JNI_OnLoad@@Base+0x2778>
   a6fa8:	aa1303e0 	orr	x0, xzr, x19
   a6fac:	940004a5 	bl	a8240 <free@plt>
   a6fb0:	394103e8 	ldrb	w8, [sp, #64]
   a6fb4:	12000108 	and	w8, w8, #0x1
   a6fb8:	34000068 	cbz	w8, a6fc4 <JNI_OnLoad@@Base+0x278c>
   a6fbc:	f9402be0 	ldr	x0, [sp, #80]
   a6fc0:	97ffd655 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a6fc4:	f9401708 	ldr	x8, [x24, #40]
   a6fc8:	f85f83a9 	ldur	x9, [x29, #-8]
   a6fcc:	eb09011f 	subs	xzr, x8, x9
   a6fd0:	54000821 	b.ne	a70d4 <JNI_OnLoad@@Base+0x289c>  // b.any
   a6fd4:	a94e4ff4 	ldp	x20, x19, [sp, #224]
   a6fd8:	f94053fb 	ldr	x27, [sp, #160]
   a6fdc:	a94d57f6 	ldp	x22, x21, [sp, #208]
   a6fe0:	a94c5ff8 	ldp	x24, x23, [sp, #192]
   a6fe4:	a94b67fa 	ldp	x26, x25, [sp, #176]
   a6fe8:	a9497bfd 	ldp	x29, x30, [sp, #144]
   a6fec:	9103c3ff 	add	sp, sp, #0xf0
   a6ff0:	d65f03c0 	ret
   a6ff4:	d503201f 	hint	#0x0
   a6ff8:	1007d580 	adr	x0, b6aa8 <DPT_UNKNOWN_DATA@@Base+0x61f>
   a6ffc:	97ffdb78 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7000:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7004:	52800035 	movz	w21, #0x1
   a7008:	aa0003e1 	orr	x1, xzr, x0
   a700c:	fd45ed00 	ldr	d0, [x8, #3032]
   a7010:	5283b728 	movz	w8, #0x1db9
   a7014:	39001815 	strb	w21, [x0, #6]
   a7018:	79000808 	strh	w8, [x0, #4]
   a701c:	d503201f 	hint	#0x0
   a7020:	1002d382 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a7024:	bd000000 	str	s0, [x0]
   a7028:	d503201f 	hint	#0x0
   a702c:	10ced040 	adr	x0, 44a34 <JNI_OnUnload@@Base+0xfa54>
   a7030:	97ffdad1 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a7034:	d503201f 	hint	#0x0
   a7038:	1007d480 	adr	x0, b6ac8 <DPT_UNKNOWN_DATA@@Base+0x63f>
   a703c:	97ffdb68 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7040:	39000015 	strb	w21, [x0]
   a7044:	17ffff7d 	b	a6e38 <JNI_OnLoad@@Base+0x2600>
   a7048:	d503201f 	hint	#0x0
   a704c:	1007d0e0 	adr	x0, b6a68 <DPT_UNKNOWN_DATA@@Base+0x5df>
   a7050:	97ffdb63 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7054:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7058:	52800033 	movz	w19, #0x1
   a705c:	aa0003e1 	orr	x1, xzr, x0
   a7060:	fd45bd00 	ldr	d0, [x8, #2936]
   a7064:	39002013 	strb	w19, [x0, #8]
   a7068:	d503201f 	hint	#0x0
   a706c:	1002d122 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a7070:	fd000000 	str	d0, [x0]
   a7074:	d503201f 	hint	#0x0
   a7078:	10cecda0 	adr	x0, 44a2c <JNI_OnUnload@@Base+0xfa4c>
   a707c:	97ffdabe 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a7080:	d503201f 	hint	#0x0
   a7084:	1007d020 	adr	x0, b6a88 <DPT_UNKNOWN_DATA@@Base+0x5ff>
   a7088:	97ffdb55 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a708c:	39000013 	strb	w19, [x0]
   a7090:	17ffff41 	b	a6d94 <JNI_OnLoad@@Base+0x255c>
   a7094:	14000005 	b	a70a8 <JNI_OnLoad@@Base+0x2870>
   a7098:	14000004 	b	a70a8 <JNI_OnLoad@@Base+0x2870>
   a709c:	14000003 	b	a70a8 <JNI_OnLoad@@Base+0x2870>
   a70a0:	14000002 	b	a70a8 <JNI_OnLoad@@Base+0x2870>
   a70a4:	14000001 	b	a70a8 <JNI_OnLoad@@Base+0x2870>
   a70a8:	394103e8 	ldrb	w8, [sp, #64]
   a70ac:	aa0003f3 	orr	x19, xzr, x0
   a70b0:	36000068 	tbz	w8, #0, a70bc <JNI_OnLoad@@Base+0x2884>
   a70b4:	f9402be0 	ldr	x0, [sp, #80]
   a70b8:	97ffd617 	bl	9c914 <JNI_OnUnload@@Base+0x67934>
   a70bc:	f9401708 	ldr	x8, [x24, #40]
   a70c0:	f85f83a9 	ldur	x9, [x29, #-8]
   a70c4:	eb09011f 	subs	xzr, x8, x9
   a70c8:	54000061 	b.ne	a70d4 <JNI_OnLoad@@Base+0x289c>  // b.any
   a70cc:	aa1303e0 	orr	x0, xzr, x19
   a70d0:	97ffdeaf 	bl	9eb8c <JNI_OnUnload@@Base+0x69bac>
   a70d4:	9400043f 	bl	a81d0 <__stack_chk_fail@plt>
   a70d8:	a9bc7bfd 	stp	x29, x30, [sp, #-64]!
   a70dc:	f9000bf7 	str	x23, [sp, #16]
   a70e0:	a90257f6 	stp	x22, x21, [sp, #32]
   a70e4:	a9034ff4 	stp	x20, x19, [sp, #48]
   a70e8:	910003fd 	add	x29, sp, #0x0
   a70ec:	d503201f 	hint	#0x0
   a70f0:	1007d0c0 	adr	x0, b6b08 <DPT_UNKNOWN_DATA@@Base+0x67f>
   a70f4:	97ffdb3a 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a70f8:	39400008 	ldrb	w8, [x0]
   a70fc:	360012e8 	tbz	w8, #0, a7358 <JNI_OnLoad@@Base+0x2b20>
   a7100:	d503201f 	hint	#0x0
   a7104:	1007cf20 	adr	x0, b6ae8 <DPT_UNKNOWN_DATA@@Base+0x65f>
   a7108:	97ffdb35 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a710c:	39403008 	ldrb	w8, [x0, #12]
   a7110:	aa0003f3 	orr	x19, xzr, x0
   a7114:	340001c8 	cbz	w8, a714c <JNI_OnLoad@@Base+0x2914>
   a7118:	bd400a60 	ldr	s0, [x19, #8]
   a711c:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7120:	3900327f 	strb	wzr, [x19, #12]
   a7124:	fd44e501 	ldr	d1, [x8, #2504]
   a7128:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a712c:	2f08a400 	ushll	v0.8h, v0.8b, #0
   a7130:	fd44ed02 	ldr	d2, [x8, #2520]
   a7134:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a7138:	fd400261 	ldr	d1, [x19]
   a713c:	2e221c21 	eor	v1.8b, v1.8b, v2.8b
   a7140:	0e212800 	xtn	v0.8b, v0.8h
   a7144:	fd000261 	str	d1, [x19]
   a7148:	bd000a60 	str	s0, [x19, #8]
   a714c:	d503201f 	hint	#0x0
   a7150:	1007cfc0 	adr	x0, b6b48 <DPT_UNKNOWN_DATA@@Base+0x6bf>
   a7154:	97ffdb22 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7158:	39400008 	ldrb	w8, [x0]
   a715c:	360012a8 	tbz	w8, #0, a73b0 <JNI_OnLoad@@Base+0x2b78>
   a7160:	d503201f 	hint	#0x0
   a7164:	1007ce20 	adr	x0, b6b28 <DPT_UNKNOWN_DATA@@Base+0x69f>
   a7168:	97ffdb1d 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a716c:	39402c08 	ldrb	w8, [x0, #11]
   a7170:	aa0003f4 	orr	x20, xzr, x0
   a7174:	34000268 	cbz	w8, a71c0 <JNI_OnLoad@@Base+0x2988>
   a7178:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a717c:	fd400280 	ldr	d0, [x20]
   a7180:	39402289 	ldrb	w9, [x20, #8]
   a7184:	3940268a 	ldrb	w10, [x20, #9]
   a7188:	fd45c101 	ldr	d1, [x8, #2944]
   a718c:	528006ab 	movz	w11, #0x35
   a7190:	52800c68 	movz	w8, #0x63
   a7194:	4a0b0129 	eor	w9, w9, w11
   a7198:	39002e9f 	strb	wzr, [x20, #11]
   a719c:	4a080148 	eor	w8, w10, w8
   a71a0:	39402a8a 	ldrb	w10, [x20, #10]
   a71a4:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a71a8:	39002289 	strb	w9, [x20, #8]
   a71ac:	52801969 	movz	w9, #0xcb
   a71b0:	4a090149 	eor	w9, w10, w9
   a71b4:	39002688 	strb	w8, [x20, #9]
   a71b8:	39002a89 	strb	w9, [x20, #10]
   a71bc:	fd000280 	str	d0, [x20]
   a71c0:	d503201f 	hint	#0x0
   a71c4:	1007ce20 	adr	x0, b6b88 <DPT_UNKNOWN_DATA@@Base+0x6ff>
   a71c8:	97ffdb05 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a71cc:	39400008 	ldrb	w8, [x0]
   a71d0:	360011a8 	tbz	w8, #0, a7404 <JNI_OnLoad@@Base+0x2bcc>
   a71d4:	d503201f 	hint	#0x0
   a71d8:	1007cc80 	adr	x0, b6b68 <DPT_UNKNOWN_DATA@@Base+0x6df>
   a71dc:	97ffdb00 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a71e0:	39401808 	ldrb	w8, [x0, #6]
   a71e4:	aa0003f5 	orr	x21, xzr, x0
   a71e8:	34000228 	cbz	w8, a722c <JNI_OnLoad@@Base+0x29f4>
   a71ec:	bd4002a0 	ldr	s0, [x21]
   a71f0:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a71f4:	52800429 	movz	w9, #0x21
   a71f8:	fd44f901 	ldr	d1, [x8, #2544]
   a71fc:	394012a8 	ldrb	w8, [x21, #4]
   a7200:	394016aa 	ldrb	w10, [x21, #5]
   a7204:	2f08a400 	ushll	v0.8h, v0.8b, #0
   a7208:	39001abf 	strb	wzr, [x21, #6]
   a720c:	4a090108 	eor	w8, w8, w9
   a7210:	528009a9 	movz	w9, #0x4d
   a7214:	4a090149 	eor	w9, w10, w9
   a7218:	390012a8 	strb	w8, [x21, #4]
   a721c:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a7220:	390016a9 	strb	w9, [x21, #5]
   a7224:	0e212800 	xtn	v0.8b, v0.8h
   a7228:	bd0002a0 	str	s0, [x21]
   a722c:	d503201f 	hint	#0x0
   a7230:	1007ccc0 	adr	x0, b6bc8 <DPT_UNKNOWN_DATA@@Base+0x73f>
   a7234:	97ffdaea 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7238:	39400008 	ldrb	w8, [x0]
   a723c:	360010e8 	tbz	w8, #0, a7458 <JNI_OnLoad@@Base+0x2c20>
   a7240:	d503201f 	hint	#0x0
   a7244:	1007cb20 	adr	x0, b6ba8 <DPT_UNKNOWN_DATA@@Base+0x71f>
   a7248:	97ffdae5 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a724c:	39401808 	ldrb	w8, [x0, #6]
   a7250:	aa0003f6 	orr	x22, xzr, x0
   a7254:	34000228 	cbz	w8, a7298 <JNI_OnLoad@@Base+0x2a60>
   a7258:	bd4002c0 	ldr	s0, [x22]
   a725c:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7260:	52800f29 	movz	w9, #0x79
   a7264:	fd452501 	ldr	d1, [x8, #2632]
   a7268:	394012c8 	ldrb	w8, [x22, #4]
   a726c:	394016ca 	ldrb	w10, [x22, #5]
   a7270:	2f08a400 	ushll	v0.8h, v0.8b, #0
   a7274:	39001adf 	strb	wzr, [x22, #6]
   a7278:	4a090108 	eor	w8, w8, w9
   a727c:	52800829 	movz	w9, #0x41
   a7280:	4a090149 	eor	w9, w10, w9
   a7284:	390012c8 	strb	w8, [x22, #4]
   a7288:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a728c:	390016c9 	strb	w9, [x22, #5]
   a7290:	0e212800 	xtn	v0.8b, v0.8h
   a7294:	bd0002c0 	str	s0, [x22]
   a7298:	d503201f 	hint	#0x0
   a729c:	1007cb60 	adr	x0, b6c08 <DPT_UNKNOWN_DATA@@Base+0x77f>
   a72a0:	97ffdacf 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a72a4:	39400008 	ldrb	w8, [x0]
   a72a8:	36001028 	tbz	w8, #0, a74ac <JNI_OnLoad@@Base+0x2c74>
   a72ac:	d503201f 	hint	#0x0
   a72b0:	1007c9c0 	adr	x0, b6be8 <DPT_UNKNOWN_DATA@@Base+0x75f>
   a72b4:	97ffdaca 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a72b8:	39403008 	ldrb	w8, [x0, #12]
   a72bc:	aa0003f7 	orr	x23, xzr, x0
   a72c0:	340001c8 	cbz	w8, a72f8 <JNI_OnLoad@@Base+0x2ac0>
   a72c4:	bd400ae0 	ldr	s0, [x23, #8]
   a72c8:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a72cc:	390032ff 	strb	wzr, [x23, #12]
   a72d0:	fd455501 	ldr	d1, [x8, #2728]
   a72d4:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a72d8:	2f08a400 	ushll	v0.8h, v0.8b, #0
   a72dc:	fd454102 	ldr	d2, [x8, #2688]
   a72e0:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a72e4:	fd4002e1 	ldr	d1, [x23]
   a72e8:	2e221c21 	eor	v1.8b, v1.8b, v2.8b
   a72ec:	0e212800 	xtn	v0.8b, v0.8h
   a72f0:	fd0002e1 	str	d1, [x23]
   a72f4:	bd000ae0 	str	s0, [x23, #8]
   a72f8:	52800020 	movz	w0, #0x1
   a72fc:	aa1303e1 	orr	x1, xzr, x19
   a7300:	97fffc07 	bl	a631c <JNI_OnLoad@@Base+0x1ae4>
   a7304:	7100041f 	subs	wzr, w0, #0x1
   a7308:	5400004b 	b.lt	a7310 <JNI_OnLoad@@Base+0x2ad8>  // b.tstop
   a730c:	97fffe02 	bl	a6b14 <JNI_OnLoad@@Base+0x22dc>
   a7310:	52800080 	movz	w0, #0x4
   a7314:	aa1403e1 	orr	x1, xzr, x20
   a7318:	aa1503e2 	orr	x2, xzr, x21
   a731c:	aa1603e3 	orr	x3, xzr, x22
   a7320:	aa1703e4 	orr	x4, xzr, x23
   a7324:	97fffcea 	bl	a66cc <JNI_OnLoad@@Base+0x1e94>
   a7328:	7100041f 	subs	wzr, w0, #0x1
   a732c:	540000cd 	b.le	a7344 <JNI_OnLoad@@Base+0x2b0c>
   a7330:	a9434ff4 	ldp	x20, x19, [sp, #48]
   a7334:	f9400bf7 	ldr	x23, [sp, #16]
   a7338:	a94257f6 	ldp	x22, x21, [sp, #32]
   a733c:	a8c47bfd 	ldp	x29, x30, [sp], #64
   a7340:	17fffdf5 	b	a6b14 <JNI_OnLoad@@Base+0x22dc>
   a7344:	a9434ff4 	ldp	x20, x19, [sp, #48]
   a7348:	f9400bf7 	ldr	x23, [sp, #16]
   a734c:	a94257f6 	ldp	x22, x21, [sp, #32]
   a7350:	a8c47bfd 	ldp	x29, x30, [sp], #64
   a7354:	d65f03c0 	ret
   a7358:	d503201f 	hint	#0x0
   a735c:	1007bc60 	adr	x0, b6ae8 <DPT_UNKNOWN_DATA@@Base+0x65f>
   a7360:	97ffda9f 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7364:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7368:	b0fffb69 	adrp	x9, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a736c:	52800033 	movz	w19, #0x1
   a7370:	fd459d00 	ldr	d0, [x8, #2872]
   a7374:	fd45dd21 	ldr	d1, [x9, #3000]
   a7378:	aa0003e1 	orr	x1, xzr, x0
   a737c:	39003013 	strb	w19, [x0, #12]
   a7380:	d503201f 	hint	#0x0
   a7384:	1002b862 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a7388:	fd000000 	str	d0, [x0]
   a738c:	bd000801 	str	s1, [x0, #8]
   a7390:	d503201f 	hint	#0x0
   a7394:	10ceb560 	adr	x0, 44a40 <JNI_OnUnload@@Base+0xfa60>
   a7398:	97ffd9f7 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a739c:	d503201f 	hint	#0x0
   a73a0:	1007bb40 	adr	x0, b6b08 <DPT_UNKNOWN_DATA@@Base+0x67f>
   a73a4:	97ffda8e 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a73a8:	39000013 	strb	w19, [x0]
   a73ac:	17ffff55 	b	a7100 <JNI_OnLoad@@Base+0x28c8>
   a73b0:	d503201f 	hint	#0x0
   a73b4:	1007bba0 	adr	x0, b6b28 <DPT_UNKNOWN_DATA@@Base+0x69f>
   a73b8:	97ffda89 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a73bc:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a73c0:	b0fffb69 	adrp	x9, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a73c4:	aa0003e1 	orr	x1, xzr, x0
   a73c8:	fd44fd00 	ldr	d0, [x8, #2552]
   a73cc:	fd460521 	ldr	d1, [x9, #3080]
   a73d0:	d503201f 	hint	#0x0
   a73d4:	1002b5e2 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a73d8:	fd000000 	str	d0, [x0]
   a73dc:	bd000801 	str	s1, [x0, #8]
   a73e0:	d503201f 	hint	#0x0
   a73e4:	10ceb340 	adr	x0, 44a4c <JNI_OnUnload@@Base+0xfa6c>
   a73e8:	97ffd9e3 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a73ec:	d503201f 	hint	#0x0
   a73f0:	1007bac0 	adr	x0, b6b48 <DPT_UNKNOWN_DATA@@Base+0x6bf>
   a73f4:	97ffda7a 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a73f8:	52800028 	movz	w8, #0x1
   a73fc:	39000008 	strb	w8, [x0]
   a7400:	17ffff58 	b	a7160 <JNI_OnLoad@@Base+0x2928>
   a7404:	d503201f 	hint	#0x0
   a7408:	1007bb00 	adr	x0, b6b68 <DPT_UNKNOWN_DATA@@Base+0x6df>
   a740c:	97ffda74 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7410:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7414:	52800035 	movz	w21, #0x1
   a7418:	aa0003e1 	orr	x1, xzr, x0
   a741c:	fd45ad00 	ldr	d0, [x8, #2904]
   a7420:	5289a9e8 	movz	w8, #0x4d4f
   a7424:	39001815 	strb	w21, [x0, #6]
   a7428:	79000808 	strh	w8, [x0, #4]
   a742c:	d503201f 	hint	#0x0
   a7430:	1002b302 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a7434:	bd000000 	str	s0, [x0]
   a7438:	d503201f 	hint	#0x0
   a743c:	10ceb0e0 	adr	x0, 44a58 <JNI_OnUnload@@Base+0xfa78>
   a7440:	97ffd9cd 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a7444:	d503201f 	hint	#0x0
   a7448:	1007ba00 	adr	x0, b6b88 <DPT_UNKNOWN_DATA@@Base+0x6ff>
   a744c:	97ffda64 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7450:	39000015 	strb	w21, [x0]
   a7454:	17ffff60 	b	a71d4 <JNI_OnLoad@@Base+0x299c>
   a7458:	d503201f 	hint	#0x0
   a745c:	1007ba60 	adr	x0, b6ba8 <DPT_UNKNOWN_DATA@@Base+0x71f>
   a7460:	97ffda5f 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7464:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7468:	52800036 	movz	w22, #0x1
   a746c:	aa0003e1 	orr	x1, xzr, x0
   a7470:	fd45c500 	ldr	d0, [x8, #2952]
   a7474:	52882148 	movz	w8, #0x410a
   a7478:	39001816 	strb	w22, [x0, #6]
   a747c:	79000808 	strh	w8, [x0, #4]
   a7480:	d503201f 	hint	#0x0
   a7484:	1002b062 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a7488:	bd000000 	str	s0, [x0]
   a748c:	d503201f 	hint	#0x0
   a7490:	10ceaea0 	adr	x0, 44a64 <JNI_OnUnload@@Base+0xfa84>
   a7494:	97ffd9b8 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a7498:	d503201f 	hint	#0x0
   a749c:	1007b960 	adr	x0, b6bc8 <DPT_UNKNOWN_DATA@@Base+0x73f>
   a74a0:	97ffda4f 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a74a4:	39000016 	strb	w22, [x0]
   a74a8:	17ffff66 	b	a7240 <JNI_OnLoad@@Base+0x2a08>
   a74ac:	d503201f 	hint	#0x0
   a74b0:	1007b9c0 	adr	x0, b6be8 <DPT_UNKNOWN_DATA@@Base+0x75f>
   a74b4:	97ffda4a 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a74b8:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a74bc:	b0fffb69 	adrp	x9, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a74c0:	52800037 	movz	w23, #0x1
   a74c4:	fd45a500 	ldr	d0, [x8, #2888]
   a74c8:	fd44c521 	ldr	d1, [x9, #2440]
   a74cc:	aa0003e1 	orr	x1, xzr, x0
   a74d0:	39003017 	strb	w23, [x0, #12]
   a74d4:	d503201f 	hint	#0x0
   a74d8:	1002adc2 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a74dc:	fd000000 	str	d0, [x0]
   a74e0:	bd000801 	str	s1, [x0, #8]
   a74e4:	d503201f 	hint	#0x0
   a74e8:	10ceac40 	adr	x0, 44a70 <JNI_OnUnload@@Base+0xfa90>
   a74ec:	97ffd9a2 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a74f0:	d503201f 	hint	#0x0
   a74f4:	1007b8a0 	adr	x0, b6c08 <DPT_UNKNOWN_DATA@@Base+0x77f>
   a74f8:	97ffda39 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a74fc:	39000017 	strb	w23, [x0]
   a7500:	17ffff6b 	b	a72ac <JNI_OnLoad@@Base+0x2a74>
   a7504:	d10543ff 	sub	sp, sp, #0x150
   a7508:	a9127bfd 	stp	x29, x30, [sp, #288]
   a750c:	a91357fc 	stp	x28, x21, [sp, #304]
   a7510:	a9144ff4 	stp	x20, x19, [sp, #320]
   a7514:	910483fd 	add	x29, sp, #0x120
   a7518:	d53bd055 	mrs	x21, tpidr_el0
   a751c:	d503201f 	hint	#0x0
   a7520:	10b8e269 	adr	x9, 1916c <_ZTSNSt6__ndk117bad_function_callE@@Base+0x282>
   a7524:	f94016a8 	ldr	x8, [x21, #40]
   a7528:	3dc00120 	ldr	q0, [x9]
   a752c:	d503201f 	hint	#0x0
   a7530:	1007b8c0 	adr	x0, b6c48 <DPT_UNKNOWN_DATA@@Base+0x7bf>
   a7534:	f81f83a8 	stur	x8, [x29, #-8]
   a7538:	528724c8 	movz	w8, #0x3926
   a753c:	3d8007e0 	str	q0, [sp, #16]
   a7540:	790043e8 	strh	w8, [sp, #32]
   a7544:	97ffda26 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7548:	39400008 	ldrb	w8, [x0]
   a754c:	360009a8 	tbz	w8, #0, a7680 <JNI_OnLoad@@Base+0x2e48>
   a7550:	d503201f 	hint	#0x0
   a7554:	1007b6a0 	adr	x0, b6c28 <DPT_UNKNOWN_DATA@@Base+0x79f>
   a7558:	97ffda21 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a755c:	aa0003f3 	orr	x19, xzr, x0
   a7560:	97fe774a 	bl	45288 <JNI_OnUnload@@Base+0x102a8>
   a7564:	d503201f 	hint	#0x0
   a7568:	30b82e41 	adr	x1, 17b31 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x13b9>
   a756c:	aa1303e0 	orr	x0, xzr, x19
   a7570:	9400037c 	bl	a8360 <fopen@plt>
   a7574:	b4000740 	cbz	x0, a765c <JNI_OnLoad@@Base+0x2e24>
   a7578:	aa0003f3 	orr	x19, xzr, x0
   a757c:	d503201f 	hint	#0x0
   a7580:	1007b840 	adr	x0, b6c88 <DPT_UNKNOWN_DATA@@Base+0x7ff>
   a7584:	97ffda16 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7588:	39400008 	ldrb	w8, [x0]
   a758c:	360009e8 	tbz	w8, #0, a76c8 <JNI_OnLoad@@Base+0x2e90>
   a7590:	d503201f 	hint	#0x0
   a7594:	1007b6a0 	adr	x0, b6c68 <DPT_UNKNOWN_DATA@@Base+0x7df>
   a7598:	97ffda11 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a759c:	39402c08 	ldrb	w8, [x0, #11]
   a75a0:	aa0003f4 	orr	x20, xzr, x0
   a75a4:	34000248 	cbz	w8, a75ec <JNI_OnLoad@@Base+0x2db4>
   a75a8:	b0fffb69 	adrp	x9, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a75ac:	39402288 	ldrb	w8, [x20, #8]
   a75b0:	fd400280 	ldr	d0, [x20]
   a75b4:	fd45f121 	ldr	d1, [x9, #3040]
   a75b8:	5280082a 	movz	w10, #0x41
   a75bc:	39402689 	ldrb	w9, [x20, #9]
   a75c0:	4a0a0108 	eor	w8, w8, w10
   a75c4:	39402a8a 	ldrb	w10, [x20, #10]
   a75c8:	39002e9f 	strb	wzr, [x20, #11]
   a75cc:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a75d0:	39002288 	strb	w8, [x20, #8]
   a75d4:	528018a8 	movz	w8, #0xc5
   a75d8:	521a7529 	eor	w9, w9, #0xffffffcf
   a75dc:	4a080148 	eor	w8, w10, w8
   a75e0:	39002689 	strb	w9, [x20, #9]
   a75e4:	39002a88 	strb	w8, [x20, #10]
   a75e8:	fd000280 	str	d0, [x20]
   a75ec:	910043e0 	add	x0, sp, #0x10
   a75f0:	52802001 	movz	w1, #0x100
   a75f4:	aa1303e2 	orr	x2, xzr, x19
   a75f8:	94000362 	bl	a8380 <fgets@plt>
   a75fc:	b40002c0 	cbz	x0, a7654 <JNI_OnLoad@@Base+0x2e1c>
   a7600:	aa1403e0 	orr	x0, xzr, x20
   a7604:	940002ff 	bl	a8200 <strlen@plt>
   a7608:	aa0003e2 	orr	x2, xzr, x0
   a760c:	910043e0 	add	x0, sp, #0x10
   a7610:	aa1403e1 	orr	x1, xzr, x20
   a7614:	940002f3 	bl	a81e0 <strncmp@plt>
   a7618:	35fffea0 	cbnz	w0, a75ec <JNI_OnLoad@@Base+0x2db4>
   a761c:	aa1403e0 	orr	x0, xzr, x20
   a7620:	b9000fff 	str	wzr, [sp, #12]
   a7624:	940002f7 	bl	a8200 <strlen@plt>
   a7628:	910043e8 	add	x8, sp, #0x10
   a762c:	90fffb81 	adrp	x1, 17000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x1eea>
   a7630:	91259821 	add	x1, x1, #0x966
   a7634:	8b000100 	add	x0, x8, x0
   a7638:	910033e2 	add	x2, sp, #0xc
   a763c:	94000355 	bl	a8390 <sscanf@plt>
   a7640:	b9400fe8 	ldr	w8, [sp, #12]
   a7644:	34000088 	cbz	w8, a7654 <JNI_OnLoad@@Base+0x2e1c>
   a7648:	aa1303e0 	orr	x0, xzr, x19
   a764c:	9400035d 	bl	a83c0 <fclose@plt>
   a7650:	97fffd31 	bl	a6b14 <JNI_OnLoad@@Base+0x22dc>
   a7654:	aa1303e0 	orr	x0, xzr, x19
   a7658:	9400035a 	bl	a83c0 <fclose@plt>
   a765c:	f94016a8 	ldr	x8, [x21, #40]
   a7660:	f85f83a9 	ldur	x9, [x29, #-8]
   a7664:	eb09011f 	subs	xzr, x8, x9
   a7668:	540005a1 	b.ne	a771c <JNI_OnLoad@@Base+0x2ee4>  // b.any
   a766c:	a9544ff4 	ldp	x20, x19, [sp, #320]
   a7670:	a95357fc 	ldp	x28, x21, [sp, #304]
   a7674:	a9527bfd 	ldp	x29, x30, [sp, #288]
   a7678:	910543ff 	add	sp, sp, #0x150
   a767c:	d65f03c0 	ret
   a7680:	d503201f 	hint	#0x0
   a7684:	1007ad20 	adr	x0, b6c28 <DPT_UNKNOWN_DATA@@Base+0x79f>
   a7688:	97ffd9d5 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a768c:	910043e1 	add	x1, sp, #0x10
   a7690:	aa0003f3 	orr	x19, xzr, x0
   a7694:	97fe74fa 	bl	44a7c <JNI_OnUnload@@Base+0xfa9c>
   a7698:	d503201f 	hint	#0x0
   a769c:	10cea3e0 	adr	x0, 44b18 <JNI_OnUnload@@Base+0xfb38>
   a76a0:	d503201f 	hint	#0x0
   a76a4:	10029f62 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a76a8:	aa1303e1 	orr	x1, xzr, x19
   a76ac:	97ffd932 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a76b0:	d503201f 	hint	#0x0
   a76b4:	1007aca0 	adr	x0, b6c48 <DPT_UNKNOWN_DATA@@Base+0x7bf>
   a76b8:	97ffd9c9 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a76bc:	52800028 	movz	w8, #0x1
   a76c0:	39000008 	strb	w8, [x0]
   a76c4:	17ffffa3 	b	a7550 <JNI_OnLoad@@Base+0x2d18>
   a76c8:	d503201f 	hint	#0x0
   a76cc:	1007ace0 	adr	x0, b6c68 <DPT_UNKNOWN_DATA@@Base+0x7df>
   a76d0:	97ffd9c3 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a76d4:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a76d8:	b0fffb69 	adrp	x9, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a76dc:	aa0003e1 	orr	x1, xzr, x0
   a76e0:	fd45e100 	ldr	d0, [x8, #3008]
   a76e4:	fd450121 	ldr	d1, [x9, #2560]
   a76e8:	d503201f 	hint	#0x0
   a76ec:	10029d22 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a76f0:	fd000000 	str	d0, [x0]
   a76f4:	bd000801 	str	s1, [x0, #8]
   a76f8:	d503201f 	hint	#0x0
   a76fc:	10cea140 	adr	x0, 44b24 <JNI_OnUnload@@Base+0xfb44>
   a7700:	97ffd91d 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a7704:	d503201f 	hint	#0x0
   a7708:	1007ac00 	adr	x0, b6c88 <DPT_UNKNOWN_DATA@@Base+0x7ff>
   a770c:	97ffd9b4 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7710:	52800028 	movz	w8, #0x1
   a7714:	39000008 	strb	w8, [x0]
   a7718:	17ffff9e 	b	a7590 <JNI_OnLoad@@Base+0x2d58>
   a771c:	940002ad 	bl	a81d0 <__stack_chk_fail@plt>
   a7720:	a9be7bfd 	stp	x29, x30, [sp, #-32]!
   a7724:	f9000bf3 	str	x19, [sp, #16]
   a7728:	910003fd 	add	x29, sp, #0x0
   a772c:	d0000053 	adrp	x19, b1000 <_ZTVNSt6__ndk117bad_function_callE@@Base+0x42b8>
   a7730:	f945b273 	ldr	x19, [x19, #2912]
   a7734:	14000003 	b	a7740 <JNI_OnLoad@@Base+0x2f08>
   a7738:	52800140 	movz	w0, #0xa
   a773c:	94000379 	bl	a8520 <sleep@plt>
   a7740:	b940b268 	ldr	w8, [x19, #176]
   a7744:	37000068 	tbnz	w8, #0, a7750 <JNI_OnLoad@@Base+0x2f18>
   a7748:	97fffe64 	bl	a70d8 <JNI_OnLoad@@Base+0x28a0>
   a774c:	b940b268 	ldr	w8, [x19, #176]
   a7750:	37080068 	tbnz	w8, #1, a775c <JNI_OnLoad@@Base+0x2f24>
   a7754:	97fffd4a 	bl	a6c7c <JNI_OnLoad@@Base+0x2444>
   a7758:	b940b268 	ldr	w8, [x19, #176]
   a775c:	3717fee8 	tbnz	w8, #2, a7738 <JNI_OnLoad@@Base+0x2f00>
   a7760:	97ffff69 	bl	a7504 <JNI_OnLoad@@Base+0x2ccc>
   a7764:	17fffff5 	b	a7738 <JNI_OnLoad@@Base+0x2f00>
   a7768:	d100c3ff 	sub	sp, sp, #0x30
   a776c:	a9017bfd 	stp	x29, x30, [sp, #16]
   a7770:	f90013f3 	str	x19, [sp, #32]
   a7774:	910043fd 	add	x29, sp, #0x10
   a7778:	d53bd053 	mrs	x19, tpidr_el0
   a777c:	d503201f 	hint	#0x0
   a7780:	10fffd02 	adr	x2, a7720 <JNI_OnLoad@@Base+0x2ee8>
   a7784:	f9401668 	ldr	x8, [x19, #40]
   a7788:	910003e0 	add	x0, sp, #0x0
   a778c:	aa1f03e1 	orr	x1, xzr, xzr
   a7790:	aa1f03e3 	orr	x3, xzr, xzr
   a7794:	f90007e8 	str	x8, [sp, #8]
   a7798:	94000366 	bl	a8530 <pthread_create@plt>
   a779c:	f9401668 	ldr	x8, [x19, #40]
   a77a0:	f94007e9 	ldr	x9, [sp, #8]
   a77a4:	eb09011f 	subs	xzr, x8, x9
   a77a8:	540000a1 	b.ne	a77bc <JNI_OnLoad@@Base+0x2f84>  // b.any
   a77ac:	a9417bfd 	ldp	x29, x30, [sp, #16]
   a77b0:	f94013f3 	ldr	x19, [sp, #32]
   a77b4:	9100c3ff 	add	sp, sp, #0x30
   a77b8:	d65f03c0 	ret
   a77bc:	94000285 	bl	a81d0 <__stack_chk_fail@plt>
   a77c0:	d103c3ff 	sub	sp, sp, #0xf0
   a77c4:	a9097bfd 	stp	x29, x30, [sp, #144]
   a77c8:	f90053fb 	str	x27, [sp, #160]
   a77cc:	a90b67fa 	stp	x26, x25, [sp, #176]
   a77d0:	a90c5ff8 	stp	x24, x23, [sp, #192]
   a77d4:	a90d57f6 	stp	x22, x21, [sp, #208]
   a77d8:	a90e4ff4 	stp	x20, x19, [sp, #224]
   a77dc:	910243fd 	add	x29, sp, #0x90
   a77e0:	d53bd059 	mrs	x25, tpidr_el0
   a77e4:	f9401728 	ldr	x8, [x25, #40]
   a77e8:	f81f83a8 	stur	x8, [x29, #-8]
   a77ec:	d503201f 	hint	#0x0
   a77f0:	100886c8 	adr	x8, b88c8 <DPT_UNKNOWN_DATA@@Base+0x243f>
   a77f4:	08dffd08 	ldarb	w8, [x8]
   a77f8:	360047a8 	tbz	w8, #0, a80ec <JNI_OnLoad@@Base+0x38b4>
   a77fc:	b40037c1 	cbz	x1, a7ef4 <JNI_OnLoad@@Base+0x36bc>
   a7800:	b40037a2 	cbz	x2, a7ef4 <JNI_OnLoad@@Base+0x36bc>
   a7804:	39400048 	ldrb	w8, [x2]
   a7808:	34003768 	cbz	w8, a7ef4 <JNI_OnLoad@@Base+0x36bc>
   a780c:	d503201f 	hint	#0x0
   a7810:	10088509 	adr	x9, b88b0 <DPT_UNKNOWN_DATA@@Base+0x2427>
   a7814:	39400128 	ldrb	w8, [x9]
   a7818:	f9400529 	ldr	x9, [x9, #8]
   a781c:	d341fd0a 	ubfm	x10, x8, #1, #63
   a7820:	7200011f 	ands	wzr, w8, #0x1
   a7824:	9a890149 	csel	x9, x10, x9, eq	// eq = none
   a7828:	b40002c9 	cbz	x9, a7880 <JNI_OnLoad@@Base+0x3048>
   a782c:	b0000089 	adrp	x9, b8000 <DPT_UNKNOWN_DATA@@Base+0x1b77>
   a7830:	9122c529 	add	x9, x9, #0x8b1
   a7834:	7200011f 	ands	wzr, w8, #0x1
   a7838:	f840f12a 	ldur	x10, [x9, #15]
   a783c:	aa0203e1 	orr	x1, xzr, x2
   a7840:	52800802 	movz	w2, #0x40
   a7844:	9a8a0120 	csel	x0, x9, x10, eq	// eq = none
   a7848:	97fe7891 	bl	45a8c <JNI_OnUnload@@Base+0x10aac>
   a784c:	34003540 	cbz	w0, a7ef4 <JNI_OnLoad@@Base+0x36bc>
   a7850:	f9401728 	ldr	x8, [x25, #40]
   a7854:	f85f83a9 	ldur	x9, [x29, #-8]
   a7858:	eb09011f 	subs	xzr, x8, x9
   a785c:	540047c1 	b.ne	a8154 <JNI_OnLoad@@Base+0x391c>  // b.any
   a7860:	a94e4ff4 	ldp	x20, x19, [sp, #224]
   a7864:	f94053fb 	ldr	x27, [sp, #160]
   a7868:	a94d57f6 	ldp	x22, x21, [sp, #208]
   a786c:	a94c5ff8 	ldp	x24, x23, [sp, #192]
   a7870:	a94b67fa 	ldp	x26, x25, [sp, #176]
   a7874:	a9497bfd 	ldp	x29, x30, [sp, #144]
   a7878:	9103c3ff 	add	sp, sp, #0xf0
   a787c:	17fffca6 	b	a6b14 <JNI_OnLoad@@Base+0x22dc>
   a7880:	d503201f 	hint	#0x0
   a7884:	50b8c7c9 	adr	x9, 1917e <_ZTSNSt6__ndk117bad_function_callE@@Base+0x294>
   a7888:	aa0003f4 	orr	x20, xzr, x0
   a788c:	3dc00120 	ldr	q0, [x9]
   a7890:	52873c68 	movz	w8, #0x39e3
   a7894:	d503201f 	hint	#0x0
   a7898:	1007a180 	adr	x0, b6cc8 <DPT_UNKNOWN_DATA@@Base+0x83f>
   a789c:	aa0103f6 	orr	x22, xzr, x1
   a78a0:	aa0203f3 	orr	x19, xzr, x2
   a78a4:	790063e8 	strh	w8, [sp, #48]
   a78a8:	3d800be0 	str	q0, [sp, #32]
   a78ac:	97ffd94c 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a78b0:	39400008 	ldrb	w8, [x0]
   a78b4:	36003388 	tbz	w8, #0, a7f24 <JNI_OnLoad@@Base+0x36ec>
   a78b8:	d503201f 	hint	#0x0
   a78bc:	10079f60 	adr	x0, b6ca8 <DPT_UNKNOWN_DATA@@Base+0x81f>
   a78c0:	97ffd947 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a78c4:	aa0003f5 	orr	x21, xzr, x0
   a78c8:	97fe7680 	bl	452c8 <JNI_OnUnload@@Base+0x102e8>
   a78cc:	d503201f 	hint	#0x0
   a78d0:	10b8c608 	adr	x8, 19190 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x2a6>
   a78d4:	d503201f 	hint	#0x0
   a78d8:	1007a180 	adr	x0, b6d08 <DPT_UNKNOWN_DATA@@Base+0x87f>
   a78dc:	ad400500 	ldp	q0, q1, [x8]
   a78e0:	f841e108 	ldur	x8, [x8, #30]
   a78e4:	ad0107e0 	stp	q0, q1, [sp, #32]
   a78e8:	f803e3e8 	stur	x8, [sp, #62]
   a78ec:	97ffd93c 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a78f0:	39400008 	ldrb	w8, [x0]
   a78f4:	360033c8 	tbz	w8, #0, a7f6c <JNI_OnLoad@@Base+0x3734>
   a78f8:	d503201f 	hint	#0x0
   a78fc:	10079f60 	adr	x0, b6ce8 <DPT_UNKNOWN_DATA@@Base+0x85f>
   a7900:	97ffd937 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7904:	39409808 	ldrb	w8, [x0, #38]
   a7908:	aa0003e3 	orr	x3, xzr, x0
   a790c:	340002e8 	cbz	w8, a7968 <JNI_OnLoad@@Base+0x3130>
   a7910:	bd402060 	ldr	s0, [x3, #32]
   a7914:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7918:	b0fffb69 	adrp	x9, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a791c:	fd45a101 	ldr	d1, [x8, #2880]
   a7920:	3dc31522 	ldr	q2, [x9, #3152]
   a7924:	39409068 	ldrb	w8, [x3, #36]
   a7928:	2f08a400 	ushll	v0.8h, v0.8b, #0
   a792c:	52801d69 	movz	w9, #0xeb
   a7930:	3940946a 	ldrb	w10, [x3, #37]
   a7934:	4a090108 	eor	w8, w8, w9
   a7938:	52801429 	movz	w9, #0xa1
   a793c:	3900987f 	strb	wzr, [x3, #38]
   a7940:	4a090149 	eor	w9, w10, w9
   a7944:	39009068 	strb	w8, [x3, #36]
   a7948:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a794c:	ad400463 	ldp	q3, q1, [x3]
   a7950:	39009469 	strb	w9, [x3, #37]
   a7954:	0e212800 	xtn	v0.8b, v0.8h
   a7958:	6e221c63 	eor	v3.16b, v3.16b, v2.16b
   a795c:	6e221c21 	eor	v1.16b, v1.16b, v2.16b
   a7960:	ad000463 	stp	q3, q1, [x3]
   a7964:	bd002060 	str	s0, [x3, #32]
   a7968:	aa1403e0 	orr	x0, xzr, x20
   a796c:	aa1603e1 	orr	x1, xzr, x22
   a7970:	aa1503e2 	orr	x2, xzr, x21
   a7974:	97fe689a 	bl	41bdc <JNI_OnUnload@@Base+0xcbfc>
   a7978:	b4002bc0 	cbz	x0, a7ef0 <JNI_OnLoad@@Base+0x36b8>
   a797c:	aa0003f5 	orr	x21, xzr, x0
   a7980:	d503201f 	hint	#0x0
   a7984:	10079e20 	adr	x0, b6d48 <DPT_UNKNOWN_DATA@@Base+0x8bf>
   a7988:	97ffd915 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a798c:	39400008 	ldrb	w8, [x0]
   a7990:	36003128 	tbz	w8, #0, a7fb4 <JNI_OnLoad@@Base+0x377c>
   a7994:	d503201f 	hint	#0x0
   a7998:	10079c80 	adr	x0, b6d28 <DPT_UNKNOWN_DATA@@Base+0x89f>
   a799c:	97ffd910 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a79a0:	39403c08 	ldrb	w8, [x0, #15]
   a79a4:	aa0003f7 	orr	x23, xzr, x0
   a79a8:	34000308 	cbz	w8, a7a08 <JNI_OnLoad@@Base+0x31d0>
   a79ac:	bd400ae0 	ldr	s0, [x23, #8]
   a79b0:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a79b4:	394032e9 	ldrb	w9, [x23, #12]
   a79b8:	fd453101 	ldr	d1, [x8, #2656]
   a79bc:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a79c0:	fd4002e2 	ldr	d2, [x23]
   a79c4:	2f08a400 	ushll	v0.8h, v0.8b, #0
   a79c8:	521b7129 	eor	w9, w9, #0xffffffe3
   a79cc:	39403aea 	ldrb	w10, [x23, #14]
   a79d0:	390032e9 	strb	w9, [x23, #12]
   a79d4:	528012e9 	movz	w9, #0x97
   a79d8:	4a090149 	eor	w9, w10, w9
   a79dc:	39003eff 	strb	wzr, [x23, #15]
   a79e0:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a79e4:	fd458d01 	ldr	d1, [x8, #2840]
   a79e8:	394036e8 	ldrb	w8, [x23, #13]
   a79ec:	39003ae9 	strb	w9, [x23, #14]
   a79f0:	2e211c41 	eor	v1.8b, v2.8b, v1.8b
   a79f4:	521d7908 	eor	w8, w8, #0xfffffffb
   a79f8:	0e212800 	xtn	v0.8b, v0.8h
   a79fc:	390036e8 	strb	w8, [x23, #13]
   a7a00:	fd0002e1 	str	d1, [x23]
   a7a04:	bd000ae0 	str	s0, [x23, #8]
   a7a08:	d503201f 	hint	#0x0
   a7a0c:	50b8bd48 	adr	x8, 191b6 <_ZTSNSt6__ndk117bad_function_callE@@Base+0x2cc>
   a7a10:	d503201f 	hint	#0x0
   a7a14:	10079ba0 	adr	x0, b6d88 <DPT_UNKNOWN_DATA@@Base+0x8ff>
   a7a18:	3dc00100 	ldr	q0, [x8]
   a7a1c:	f840d108 	ldur	x8, [x8, #13]
   a7a20:	3d800be0 	str	q0, [sp, #32]
   a7a24:	f802d3e8 	stur	x8, [sp, #45]
   a7a28:	97ffd8ed 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7a2c:	39400008 	ldrb	w8, [x0]
   a7a30:	36002e68 	tbz	w8, #0, a7ffc <JNI_OnLoad@@Base+0x37c4>
   a7a34:	d503201f 	hint	#0x0
   a7a38:	10079980 	adr	x0, b6d68 <DPT_UNKNOWN_DATA@@Base+0x8df>
   a7a3c:	97ffd8e8 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7a40:	aa0003f8 	orr	x24, xzr, x0
   a7a44:	97fe7632 	bl	4530c <JNI_OnUnload@@Base+0x1032c>
   a7a48:	aa1403e0 	orr	x0, xzr, x20
   a7a4c:	aa1603e1 	orr	x1, xzr, x22
   a7a50:	aa1703e2 	orr	x2, xzr, x23
   a7a54:	aa1803e3 	orr	x3, xzr, x24
   a7a58:	97fe6861 	bl	41bdc <JNI_OnUnload@@Base+0xcbfc>
   a7a5c:	b40024a0 	cbz	x0, a7ef0 <JNI_OnLoad@@Base+0x36b8>
   a7a60:	6f00e400 	movi	v0.2d, #0x0
   a7a64:	aa0003f6 	orr	x22, xzr, x0
   a7a68:	b0fffb80 	adrp	x0, 18000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0xeea>
   a7a6c:	9102d000 	add	x0, x0, #0xb4
   a7a70:	910083e1 	add	x1, sp, #0x20
   a7a74:	3c86c3e0 	stur	q0, [sp, #108]
   a7a78:	ad0183e0 	stp	q0, q0, [sp, #48]
   a7a7c:	ad0283e0 	stp	q0, q0, [sp, #80]
   a7a80:	3d800be0 	str	q0, [sp, #32]
   a7a84:	940001cb 	bl	a81b0 <__system_property_get@plt>
   a7a88:	7100041f 	subs	wzr, w0, #0x1
   a7a8c:	540000cb 	b.lt	a7aa4 <JNI_OnLoad@@Base+0x326c>  // b.tstop
   a7a90:	910083e0 	add	x0, sp, #0x20
   a7a94:	940001cb 	bl	a81c0 <atoi@plt>
   a7a98:	7100001f 	subs	wzr, w0, #0x0
   a7a9c:	5a9fc01a 	csinv	w26, w0, wzr, gt
   a7aa0:	14000002 	b	a7aa8 <JNI_OnLoad@@Base+0x3270>
   a7aa4:	1280001a 	movn	w26, #0x0
   a7aa8:	71006f5f 	subs	wzr, w26, #0x1b
   a7aac:	52800808 	movz	w8, #0x40
   a7ab0:	52a10009 	movz	w9, #0x800, lsl #16
   a7ab4:	d503201f 	hint	#0x0
   a7ab8:	10079880 	adr	x0, b6dc8 <DPT_UNKNOWN_DATA@@Base+0x93f>
   a7abc:	1a88c137 	csel	w23, w9, w8, gt
   a7ac0:	97ffd8c7 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7ac4:	39400008 	ldrb	w8, [x0]
   a7ac8:	36002be8 	tbz	w8, #0, a8044 <JNI_OnLoad@@Base+0x380c>
   a7acc:	d503201f 	hint	#0x0
   a7ad0:	100796c0 	adr	x0, b6da8 <DPT_UNKNOWN_DATA@@Base+0x91f>
   a7ad4:	97ffd8c2 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7ad8:	39403c08 	ldrb	w8, [x0, #15]
   a7adc:	aa0003f8 	orr	x24, xzr, x0
   a7ae0:	34000308 	cbz	w8, a7b40 <JNI_OnLoad@@Base+0x3308>
   a7ae4:	bd400b00 	ldr	s0, [x24, #8]
   a7ae8:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7aec:	b0fffb69 	adrp	x9, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7af0:	fd451901 	ldr	d1, [x8, #2608]
   a7af4:	fd400302 	ldr	d2, [x24]
   a7af8:	fd455923 	ldr	d3, [x9, #2736]
   a7afc:	2f08a400 	ushll	v0.8h, v0.8b, #0
   a7b00:	39403308 	ldrb	w8, [x24, #12]
   a7b04:	39403709 	ldrb	w9, [x24, #13]
   a7b08:	39403b0b 	ldrb	w11, [x24, #14]
   a7b0c:	52800caa 	movz	w10, #0x65
   a7b10:	39003f1f 	strb	wzr, [x24, #15]
   a7b14:	521b7908 	eor	w8, w8, #0xffffffef
   a7b18:	4a0a0129 	eor	w9, w9, w10
   a7b1c:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a7b20:	2e231c41 	eor	v1.8b, v2.8b, v3.8b
   a7b24:	39003308 	strb	w8, [x24, #12]
   a7b28:	5202e968 	eor	w8, w11, #0xdddddddd
   a7b2c:	39003709 	strb	w9, [x24, #13]
   a7b30:	39003b08 	strb	w8, [x24, #14]
   a7b34:	0e212800 	xtn	v0.8b, v0.8h
   a7b38:	fd000301 	str	d1, [x24]
   a7b3c:	bd000b00 	str	s0, [x24, #8]
   a7b40:	d503201f 	hint	#0x0
   a7b44:	10079620 	adr	x0, b6e08 <DPT_UNKNOWN_DATA@@Base+0x97f>
   a7b48:	97ffd8a5 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7b4c:	39400008 	ldrb	w8, [x0]
   a7b50:	360029e8 	tbz	w8, #0, a808c <JNI_OnLoad@@Base+0x3854>
   a7b54:	d503201f 	hint	#0x0
   a7b58:	10079480 	adr	x0, b6de8 <DPT_UNKNOWN_DATA@@Base+0x95f>
   a7b5c:	97ffd8a0 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7b60:	3940d808 	ldrb	w8, [x0, #54]
   a7b64:	aa0003e3 	orr	x3, xzr, x0
   a7b68:	34000348 	cbz	w8, a7bd0 <JNI_OnLoad@@Base+0x3398>
   a7b6c:	bd403060 	ldr	s0, [x3, #48]
   a7b70:	d0fffb68 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a7b74:	3dc00865 	ldr	q5, [x3, #32]
   a7b78:	3dc03d01 	ldr	q1, [x8, #240]
   a7b7c:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7b80:	52800e69 	movz	w9, #0x73
   a7b84:	2f08a400 	ushll	v0.8h, v0.8b, #0
   a7b88:	fd451d04 	ldr	d4, [x8, #2616]
   a7b8c:	3940d068 	ldrb	w8, [x3, #52]
   a7b90:	ad400c62 	ldp	q2, q3, [x3]
   a7b94:	3940d46a 	ldrb	w10, [x3, #53]
   a7b98:	4a090108 	eor	w8, w8, w9
   a7b9c:	52801429 	movz	w9, #0xa1
   a7ba0:	3900d87f 	strb	wzr, [x3, #54]
   a7ba4:	2e241c00 	eor	v0.8b, v0.8b, v4.8b
   a7ba8:	4a090149 	eor	w9, w10, w9
   a7bac:	3900d068 	strb	w8, [x3, #52]
   a7bb0:	6e211c42 	eor	v2.16b, v2.16b, v1.16b
   a7bb4:	6e211c63 	eor	v3.16b, v3.16b, v1.16b
   a7bb8:	6e211ca1 	eor	v1.16b, v5.16b, v1.16b
   a7bbc:	3900d469 	strb	w9, [x3, #53]
   a7bc0:	0e212800 	xtn	v0.8b, v0.8h
   a7bc4:	ad000c62 	stp	q2, q3, [x3]
   a7bc8:	3d800861 	str	q1, [x3, #32]
   a7bcc:	bd003060 	str	s0, [x3, #48]
   a7bd0:	aa1403e0 	orr	x0, xzr, x20
   a7bd4:	aa1503e1 	orr	x1, xzr, x21
   a7bd8:	aa1803e2 	orr	x2, xzr, x24
   a7bdc:	aa1603e4 	orr	x4, xzr, x22
   a7be0:	2a1703e5 	orr	w5, wzr, w23
   a7be4:	97fe67fe 	bl	41bdc <JNI_OnUnload@@Base+0xcbfc>
   a7be8:	b4001840 	cbz	x0, a7ef0 <JNI_OnLoad@@Base+0x36b8>
   a7bec:	aa0003f5 	orr	x21, xzr, x0
   a7bf0:	7100735f 	subs	wzr, w26, #0x1c
   a7bf4:	54000c2b 	b.lt	a7d78 <JNI_OnLoad@@Base+0x3540>  // b.tstop
   a7bf8:	97fe712e 	bl	440b0 <JNI_OnUnload@@Base+0xf0d0>
   a7bfc:	97fe7151 	bl	44140 <JNI_OnUnload@@Base+0xf160>
   a7c00:	aa0003f6 	orr	x22, xzr, x0
   a7c04:	97fe715f 	bl	44180 <JNI_OnUnload@@Base+0xf1a0>
   a7c08:	39408408 	ldrb	w8, [x0, #33]
   a7c0c:	aa0003e3 	orr	x3, xzr, x0
   a7c10:	34000188 	cbz	w8, a7c40 <JNI_OnLoad@@Base+0x3408>
   a7c14:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7c18:	ad400861 	ldp	q1, q2, [x3]
   a7c1c:	3dc3bd00 	ldr	q0, [x8, #3824]
   a7c20:	39408068 	ldrb	w8, [x3, #32]
   a7c24:	52800ba9 	movz	w9, #0x5d
   a7c28:	3900847f 	strb	wzr, [x3, #33]
   a7c2c:	6e201c21 	eor	v1.16b, v1.16b, v0.16b
   a7c30:	6e201c40 	eor	v0.16b, v2.16b, v0.16b
   a7c34:	4a090108 	eor	w8, w8, w9
   a7c38:	39008068 	strb	w8, [x3, #32]
   a7c3c:	ad000061 	stp	q1, q0, [x3]
   a7c40:	aa1403e0 	orr	x0, xzr, x20
   a7c44:	aa1503e1 	orr	x1, xzr, x21
   a7c48:	aa1603e2 	orr	x2, xzr, x22
   a7c4c:	97fe6761 	bl	419d0 <JNI_OnUnload@@Base+0xc9f0>
   a7c50:	b4001500 	cbz	x0, a7ef0 <JNI_OnLoad@@Base+0x36b8>
   a7c54:	aa0003f5 	orr	x21, xzr, x0
   a7c58:	97fe717a 	bl	44240 <JNI_OnUnload@@Base+0xf260>
   a7c5c:	39405808 	ldrb	w8, [x0, #22]
   a7c60:	aa0003f6 	orr	x22, xzr, x0
   a7c64:	340002a8 	cbz	w8, a7cb8 <JNI_OnLoad@@Base+0x3480>
   a7c68:	bd4012c0 	ldr	s0, [x22, #16]
   a7c6c:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7c70:	3dc002c2 	ldr	q2, [x22]
   a7c74:	fd458101 	ldr	d1, [x8, #2816]
   a7c78:	d0fffb68 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a7c7c:	394056c9 	ldrb	w9, [x22, #21]
   a7c80:	2f08a400 	ushll	v0.8h, v0.8b, #0
   a7c84:	528007aa 	movz	w10, #0x3d
   a7c88:	39005adf 	strb	wzr, [x22, #22]
   a7c8c:	4a0a0129 	eor	w9, w9, w10
   a7c90:	390056c9 	strb	w9, [x22, #21]
   a7c94:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a7c98:	3dc04d01 	ldr	q1, [x8, #304]
   a7c9c:	394052c8 	ldrb	w8, [x22, #20]
   a7ca0:	6e211c41 	eor	v1.16b, v2.16b, v1.16b
   a7ca4:	5201e508 	eor	w8, w8, #0x99999999
   a7ca8:	0e212800 	xtn	v0.8b, v0.8h
   a7cac:	390052c8 	strb	w8, [x22, #20]
   a7cb0:	3d8002c1 	str	q1, [x22]
   a7cb4:	bd0012c0 	str	s0, [x22, #16]
   a7cb8:	97fe7192 	bl	44300 <JNI_OnUnload@@Base+0xf320>
   a7cbc:	39408808 	ldrb	w8, [x0, #34]
   a7cc0:	aa0003e3 	orr	x3, xzr, x0
   a7cc4:	34000268 	cbz	w8, a7d10 <JNI_OnLoad@@Base+0x34d8>
   a7cc8:	91008068 	add	x8, x3, #0x20
   a7ccc:	b0fffb69 	adrp	x9, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7cd0:	3900887f 	strb	wzr, [x3, #34]
   a7cd4:	0d400100 	ld1	{v0.b}[0], [x8]
   a7cd8:	91008468 	add	x8, x3, #0x21
   a7cdc:	fd44d921 	ldr	d1, [x9, #2480]
   a7ce0:	0d401100 	ld1	{v0.b}[4], [x8]
   a7ce4:	d0fffb68 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a7ce8:	3dc06d02 	ldr	q2, [x8, #432]
   a7cec:	2e211c00 	eor	v0.8b, v0.8b, v1.8b
   a7cf0:	ad400c61 	ldp	q1, q3, [x3]
   a7cf4:	0e0c3c08 	umov	w8, v0.s[1]
   a7cf8:	6e221c21 	eor	v1.16b, v1.16b, v2.16b
   a7cfc:	6e221c62 	eor	v2.16b, v3.16b, v2.16b
   a7d00:	1e260009 	fmov	w9, s0
   a7d04:	ad000861 	stp	q1, q2, [x3]
   a7d08:	39008069 	strb	w9, [x3, #32]
   a7d0c:	39008468 	strb	w8, [x3, #33]
   a7d10:	aa1403e0 	orr	x0, xzr, x20
   a7d14:	aa1503e1 	orr	x1, xzr, x21
   a7d18:	aa1603e2 	orr	x2, xzr, x22
   a7d1c:	97fe67b0 	bl	41bdc <JNI_OnUnload@@Base+0xcbfc>
   a7d20:	b4000e80 	cbz	x0, a7ef0 <JNI_OnLoad@@Base+0x36b8>
   a7d24:	f9400288 	ldr	x8, [x20]
   a7d28:	aa0003f5 	orr	x21, xzr, x0
   a7d2c:	aa1403e0 	orr	x0, xzr, x20
   a7d30:	aa1503e1 	orr	x1, xzr, x21
   a7d34:	f942ad08 	ldr	x8, [x8, #1368]
   a7d38:	d63f0100 	blr	x8
   a7d3c:	34000da0 	cbz	w0, a7ef0 <JNI_OnLoad@@Base+0x36b8>
   a7d40:	f9400288 	ldr	x8, [x20]
   a7d44:	aa1403e0 	orr	x0, xzr, x20
   a7d48:	aa1503e1 	orr	x1, xzr, x21
   a7d4c:	2a1f03e2 	orr	w2, wzr, wzr
   a7d50:	aa1403f6 	orr	x22, xzr, x20
   a7d54:	f942b508 	ldr	x8, [x8, #1384]
   a7d58:	d63f0100 	blr	x8
   a7d5c:	aa0003f5 	orr	x21, xzr, x0
   a7d60:	97fe7198 	bl	443c0 <JNI_OnUnload@@Base+0xf3e0>
   a7d64:	97fe71bb 	bl	44450 <JNI_OnUnload@@Base+0xf470>
   a7d68:	aa0003f7 	orr	x23, xzr, x0
   a7d6c:	97fe71c9 	bl	44490 <JNI_OnUnload@@Base+0xf4b0>
   a7d70:	97fe71e8 	bl	44510 <JNI_OnUnload@@Base+0xf530>
   a7d74:	14000028 	b	a7e14 <JNI_OnLoad@@Base+0x35dc>
   a7d78:	97fe71f4 	bl	44548 <JNI_OnUnload@@Base+0xf568>
   a7d7c:	97fe7214 	bl	445cc <JNI_OnUnload@@Base+0xf5ec>
   a7d80:	aa0003f6 	orr	x22, xzr, x0
   a7d84:	97fe7227 	bl	44620 <JNI_OnUnload@@Base+0xf640>
   a7d88:	39408008 	ldrb	w8, [x0, #32]
   a7d8c:	aa0003e3 	orr	x3, xzr, x0
   a7d90:	34000108 	cbz	w8, a7db0 <JNI_OnLoad@@Base+0x3578>
   a7d94:	d0fffb68 	adrp	x8, 15000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x3eea>
   a7d98:	ad400861 	ldp	q1, q2, [x3]
   a7d9c:	3dc06900 	ldr	q0, [x8, #416]
   a7da0:	3900807f 	strb	wzr, [x3, #32]
   a7da4:	6e201c21 	eor	v1.16b, v1.16b, v0.16b
   a7da8:	6e201c40 	eor	v0.16b, v2.16b, v0.16b
   a7dac:	ad000061 	stp	q1, q0, [x3]
   a7db0:	aa1403e0 	orr	x0, xzr, x20
   a7db4:	aa1503e1 	orr	x1, xzr, x21
   a7db8:	aa1603e2 	orr	x2, xzr, x22
   a7dbc:	97fe6705 	bl	419d0 <JNI_OnUnload@@Base+0xc9f0>
   a7dc0:	b4000980 	cbz	x0, a7ef0 <JNI_OnLoad@@Base+0x36b8>
   a7dc4:	f9400288 	ldr	x8, [x20]
   a7dc8:	aa0003f5 	orr	x21, xzr, x0
   a7dcc:	aa1403e0 	orr	x0, xzr, x20
   a7dd0:	aa1503e1 	orr	x1, xzr, x21
   a7dd4:	f942ad08 	ldr	x8, [x8, #1368]
   a7dd8:	d63f0100 	blr	x8
   a7ddc:	340008a0 	cbz	w0, a7ef0 <JNI_OnLoad@@Base+0x36b8>
   a7de0:	f9400288 	ldr	x8, [x20]
   a7de4:	aa1403e0 	orr	x0, xzr, x20
   a7de8:	aa1503e1 	orr	x1, xzr, x21
   a7dec:	2a1f03e2 	orr	w2, wzr, wzr
   a7df0:	aa1403f6 	orr	x22, xzr, x20
   a7df4:	f942b508 	ldr	x8, [x8, #1384]
   a7df8:	d63f0100 	blr	x8
   a7dfc:	aa0003f5 	orr	x21, xzr, x0
   a7e00:	97fe7236 	bl	446d8 <JNI_OnUnload@@Base+0xf6f8>
   a7e04:	97fe7259 	bl	44768 <JNI_OnUnload@@Base+0xf788>
   a7e08:	aa0003f7 	orr	x23, xzr, x0
   a7e0c:	97fe7267 	bl	447a8 <JNI_OnUnload@@Base+0xf7c8>
   a7e10:	97fe7286 	bl	44828 <JNI_OnUnload@@Base+0xf848>
   a7e14:	aa0003e3 	orr	x3, xzr, x0
   a7e18:	aa1403e0 	orr	x0, xzr, x20
   a7e1c:	aa1503e1 	orr	x1, xzr, x21
   a7e20:	aa1703e2 	orr	x2, xzr, x23
   a7e24:	97fe676e 	bl	41bdc <JNI_OnUnload@@Base+0xcbfc>
   a7e28:	aa0003f5 	orr	x21, xzr, x0
   a7e2c:	b4000620 	cbz	x0, a7ef0 <JNI_OnLoad@@Base+0x36b8>
   a7e30:	f9400288 	ldr	x8, [x20]
   a7e34:	aa1403e0 	orr	x0, xzr, x20
   a7e38:	aa1503e1 	orr	x1, xzr, x21
   a7e3c:	f942ad08 	ldr	x8, [x8, #1368]
   a7e40:	d63f0100 	blr	x8
   a7e44:	f9400288 	ldr	x8, [x20]
   a7e48:	2a0003f6 	orr	w22, wzr, w0
   a7e4c:	aa1403e0 	orr	x0, xzr, x20
   a7e50:	aa1503e1 	orr	x1, xzr, x21
   a7e54:	aa1f03e2 	orr	x2, xzr, xzr
   a7e58:	f942e108 	ldr	x8, [x8, #1472]
   a7e5c:	d63f0100 	blr	x8
   a7e60:	93407ec1 	sbfm	x1, x22, #0, #31
   a7e64:	910003e2 	add	x2, sp, #0x0
   a7e68:	2a1f03e3 	orr	w3, wzr, wzr
   a7e6c:	aa0003f6 	orr	x22, xzr, x0
   a7e70:	910003f7 	add	x23, sp, #0x0
   a7e74:	97fed5f6 	bl	5d64c <JNI_OnUnload@@Base+0x2866c>
   a7e78:	f9400288 	ldr	x8, [x20]
   a7e7c:	aa1403e0 	orr	x0, xzr, x20
   a7e80:	aa1503e1 	orr	x1, xzr, x21
   a7e84:	aa1603e2 	orr	x2, xzr, x22
   a7e88:	52800043 	movz	w3, #0x2
   a7e8c:	f9430108 	ldr	x8, [x8, #1536]
   a7e90:	d63f0100 	blr	x8
   a7e94:	6f00e400 	movi	v0.2d, #0x0
   a7e98:	aa1f03f5 	orr	x21, xzr, xzr
   a7e9c:	910083f4 	add	x20, sp, #0x20
   a7ea0:	390183ff 	strb	wzr, [sp, #96]
   a7ea4:	ad0103e0 	stp	q0, q0, [sp, #32]
   a7ea8:	ad0203e0 	stp	q0, q0, [sp, #64]
   a7eac:	38756ae4 	ldrb	w4, [x23, x21]
   a7eb0:	aa1403e0 	orr	x0, xzr, x20
   a7eb4:	92800001 	movn	x1, #0x0
   a7eb8:	97fe726b 	bl	44864 <JNI_OnUnload@@Base+0xf884>
   a7ebc:	910006b5 	add	x21, x21, #0x1
   a7ec0:	91000a94 	add	x20, x20, #0x2
   a7ec4:	f10082bf 	subs	xzr, x21, #0x20
   a7ec8:	54ffff21 	b.ne	a7eac <JNI_OnLoad@@Base+0x3674>  // b.any
   a7ecc:	d503201f 	hint	#0x0
   a7ed0:	10084f00 	adr	x0, b88b0 <DPT_UNKNOWN_DATA@@Base+0x2427>
   a7ed4:	910083e1 	add	x1, sp, #0x20
   a7ed8:	97fe65a2 	bl	41560 <JNI_OnUnload@@Base+0xc580>
   a7edc:	910083e0 	add	x0, sp, #0x20
   a7ee0:	aa1303e1 	orr	x1, xzr, x19
   a7ee4:	52800802 	movz	w2, #0x40
   a7ee8:	97fe76e9 	bl	45a8c <JNI_OnUnload@@Base+0x10aac>
   a7eec:	34000040 	cbz	w0, a7ef4 <JNI_OnLoad@@Base+0x36bc>
   a7ef0:	97fffb09 	bl	a6b14 <JNI_OnLoad@@Base+0x22dc>
   a7ef4:	f9401728 	ldr	x8, [x25, #40]
   a7ef8:	f85f83a9 	ldur	x9, [x29, #-8]
   a7efc:	eb09011f 	subs	xzr, x8, x9
   a7f00:	540012a1 	b.ne	a8154 <JNI_OnLoad@@Base+0x391c>  // b.any
   a7f04:	a94e4ff4 	ldp	x20, x19, [sp, #224]
   a7f08:	f94053fb 	ldr	x27, [sp, #160]
   a7f0c:	a94d57f6 	ldp	x22, x21, [sp, #208]
   a7f10:	a94c5ff8 	ldp	x24, x23, [sp, #192]
   a7f14:	a94b67fa 	ldp	x26, x25, [sp, #176]
   a7f18:	a9497bfd 	ldp	x29, x30, [sp, #144]
   a7f1c:	9103c3ff 	add	sp, sp, #0xf0
   a7f20:	d65f03c0 	ret
   a7f24:	d503201f 	hint	#0x0
   a7f28:	10076c00 	adr	x0, b6ca8 <DPT_UNKNOWN_DATA@@Base+0x81f>
   a7f2c:	97ffd7ac 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7f30:	910083e1 	add	x1, sp, #0x20
   a7f34:	aa0003f5 	orr	x21, xzr, x0
   a7f38:	97fe72fe 	bl	44b30 <JNI_OnUnload@@Base+0xfb50>
   a7f3c:	d503201f 	hint	#0x0
   a7f40:	10ce6460 	adr	x0, 44bcc <JNI_OnUnload@@Base+0xfbec>
   a7f44:	d503201f 	hint	#0x0
   a7f48:	10025a42 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a7f4c:	aa1503e1 	orr	x1, xzr, x21
   a7f50:	97ffd709 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a7f54:	d503201f 	hint	#0x0
   a7f58:	10076b80 	adr	x0, b6cc8 <DPT_UNKNOWN_DATA@@Base+0x83f>
   a7f5c:	97ffd7a0 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7f60:	52800028 	movz	w8, #0x1
   a7f64:	39000008 	strb	w8, [x0]
   a7f68:	17fffe54 	b	a78b8 <JNI_OnLoad@@Base+0x3080>
   a7f6c:	d503201f 	hint	#0x0
   a7f70:	10076bc0 	adr	x0, b6ce8 <DPT_UNKNOWN_DATA@@Base+0x85f>
   a7f74:	97ffd79a 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7f78:	910083e1 	add	x1, sp, #0x20
   a7f7c:	aa0003f7 	orr	x23, xzr, x0
   a7f80:	97fe7316 	bl	44bd8 <JNI_OnUnload@@Base+0xfbf8>
   a7f84:	d503201f 	hint	#0x0
   a7f88:	10ce6c60 	adr	x0, 44d14 <JNI_OnUnload@@Base+0xfd34>
   a7f8c:	d503201f 	hint	#0x0
   a7f90:	10025802 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a7f94:	aa1703e1 	orr	x1, xzr, x23
   a7f98:	97ffd6f7 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a7f9c:	d503201f 	hint	#0x0
   a7fa0:	10076b40 	adr	x0, b6d08 <DPT_UNKNOWN_DATA@@Base+0x87f>
   a7fa4:	97ffd78e 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7fa8:	52800028 	movz	w8, #0x1
   a7fac:	39000008 	strb	w8, [x0]
   a7fb0:	17fffe52 	b	a78f8 <JNI_OnLoad@@Base+0x30c0>
   a7fb4:	d503201f 	hint	#0x0
   a7fb8:	10076b80 	adr	x0, b6d28 <DPT_UNKNOWN_DATA@@Base+0x89f>
   a7fbc:	97ffd788 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7fc0:	b0fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a7fc4:	aa0003e1 	orr	x1, xzr, x0
   a7fc8:	d503201f 	hint	#0x0
   a7fcc:	10025622 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a7fd0:	3dc33900 	ldr	q0, [x8, #3296]
   a7fd4:	3d800000 	str	q0, [x0]
   a7fd8:	d503201f 	hint	#0x0
   a7fdc:	10ce6a40 	adr	x0, 44d24 <JNI_OnUnload@@Base+0xfd44>
   a7fe0:	97ffd6e5 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a7fe4:	d503201f 	hint	#0x0
   a7fe8:	10076b00 	adr	x0, b6d48 <DPT_UNKNOWN_DATA@@Base+0x8bf>
   a7fec:	97ffd77c 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a7ff0:	52800028 	movz	w8, #0x1
   a7ff4:	39000008 	strb	w8, [x0]
   a7ff8:	17fffe67 	b	a7994 <JNI_OnLoad@@Base+0x315c>
   a7ffc:	d503201f 	hint	#0x0
   a8000:	10076b40 	adr	x0, b6d68 <DPT_UNKNOWN_DATA@@Base+0x8df>
   a8004:	97ffd776 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a8008:	910083e1 	add	x1, sp, #0x20
   a800c:	aa0003f8 	orr	x24, xzr, x0
   a8010:	97fe7348 	bl	44d30 <JNI_OnUnload@@Base+0xfd50>
   a8014:	d503201f 	hint	#0x0
   a8018:	10ce6e60 	adr	x0, 44de4 <JNI_OnUnload@@Base+0xfe04>
   a801c:	d503201f 	hint	#0x0
   a8020:	10025382 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a8024:	aa1803e1 	orr	x1, xzr, x24
   a8028:	97ffd6d3 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a802c:	d503201f 	hint	#0x0
   a8030:	10076ac0 	adr	x0, b6d88 <DPT_UNKNOWN_DATA@@Base+0x8ff>
   a8034:	97ffd76a 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a8038:	52800028 	movz	w8, #0x1
   a803c:	39000008 	strb	w8, [x0]
   a8040:	17fffe7d 	b	a7a34 <JNI_OnLoad@@Base+0x31fc>
   a8044:	d503201f 	hint	#0x0
   a8048:	10076b00 	adr	x0, b6da8 <DPT_UNKNOWN_DATA@@Base+0x91f>
   a804c:	97ffd764 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a8050:	90fffb68 	adrp	x8, 14000 <_ZTSNSt6__ndk117bad_function_callE@@Base-0x4eea>
   a8054:	aa0003e1 	orr	x1, xzr, x0
   a8058:	d503201f 	hint	#0x0
   a805c:	100251a2 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a8060:	3dc3a900 	ldr	q0, [x8, #3744]
   a8064:	3d800000 	str	q0, [x0]
   a8068:	d503201f 	hint	#0x0
   a806c:	10ce6c20 	adr	x0, 44df0 <JNI_OnUnload@@Base+0xfe10>
   a8070:	97ffd6c1 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a8074:	d503201f 	hint	#0x0
   a8078:	10076a80 	adr	x0, b6dc8 <DPT_UNKNOWN_DATA@@Base+0x93f>
   a807c:	97ffd758 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a8080:	52800028 	movz	w8, #0x1
   a8084:	39000008 	strb	w8, [x0]
   a8088:	17fffe91 	b	a7acc <JNI_OnLoad@@Base+0x3294>
   a808c:	d503201f 	hint	#0x0
   a8090:	10076ac0 	adr	x0, b6de8 <DPT_UNKNOWN_DATA@@Base+0x95f>
   a8094:	97ffd752 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a8098:	d503201f 	hint	#0x0
   a809c:	70b88968 	adr	x8, 191cb <_ZTSNSt6__ndk117bad_function_callE@@Base+0x2e1>
   a80a0:	5280003b 	movz	w27, #0x1
   a80a4:	ad400500 	ldp	q0, q1, [x8]
   a80a8:	3dc00902 	ldr	q2, [x8, #32]
   a80ac:	f842e108 	ldur	x8, [x8, #46]
   a80b0:	aa0003e1 	orr	x1, xzr, x0
   a80b4:	3900d81b 	strb	w27, [x0, #54]
   a80b8:	3d800802 	str	q2, [x0, #32]
   a80bc:	d503201f 	hint	#0x0
   a80c0:	10024e82 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a80c4:	ad000400 	stp	q0, q1, [x0]
   a80c8:	f802e008 	stur	x8, [x0, #46]
   a80cc:	d503201f 	hint	#0x0
   a80d0:	10ce6960 	adr	x0, 44dfc <JNI_OnUnload@@Base+0xfe1c>
   a80d4:	97ffd6a8 	bl	9db74 <JNI_OnUnload@@Base+0x68b94>
   a80d8:	d503201f 	hint	#0x0
   a80dc:	10076960 	adr	x0, b6e08 <DPT_UNKNOWN_DATA@@Base+0x97f>
   a80e0:	97ffd73f 	bl	9dddc <JNI_OnUnload@@Base+0x68dfc>
   a80e4:	3900001b 	strb	w27, [x0]
   a80e8:	17fffe9b 	b	a7b54 <JNI_OnLoad@@Base+0x331c>
   a80ec:	d503201f 	hint	#0x0
   a80f0:	10083ec8 	adr	x8, b88c8 <DPT_UNKNOWN_DATA@@Base+0x243f>
   a80f4:	aa0003f3 	orr	x19, xzr, x0
   a80f8:	aa0803e0 	orr	x0, xzr, x8
   a80fc:	aa0203f4 	orr	x20, xzr, x2
   a8100:	aa0103f5 	orr	x21, xzr, x1
   a8104:	97ff6959 	bl	82668 <JNI_OnUnload@@Base+0x4d688>
   a8108:	aa1503e1 	orr	x1, xzr, x21
   a810c:	aa1403e2 	orr	x2, xzr, x20
   a8110:	2a0003e8 	orr	w8, wzr, w0
   a8114:	aa1303e0 	orr	x0, xzr, x19
   a8118:	34ffb728 	cbz	w8, a77fc <JNI_OnLoad@@Base+0x2fc4>
   a811c:	b0000040 	adrp	x0, b1000 <_ZTVNSt6__ndk117bad_function_callE@@Base+0x42b8>
   a8120:	d503201f 	hint	#0x0
   a8124:	10083c61 	adr	x1, b88b0 <DPT_UNKNOWN_DATA@@Base+0x2427>
   a8128:	f9460000 	ldr	x0, [x0, #3072]
   a812c:	d503201f 	hint	#0x0
   a8130:	10024b02 	adr	x2, aca90 <pthread_key_delete@plt+0x4010>
   a8134:	94000017 	bl	a8190 <__cxa_atexit@plt>
   a8138:	d503201f 	hint	#0x0
   a813c:	10083c60 	adr	x0, b88c8 <DPT_UNKNOWN_DATA@@Base+0x243f>
   a8140:	97ff6996 	bl	82798 <JNI_OnUnload@@Base+0x4d7b8>
   a8144:	aa1503e1 	orr	x1, xzr, x21
   a8148:	aa1303e0 	orr	x0, xzr, x19
   a814c:	aa1403e2 	orr	x2, xzr, x20
   a8150:	17fffdab 	b	a77fc <JNI_OnLoad@@Base+0x2fc4>
   a8154:	9400001f 	bl	a81d0 <__stack_chk_fail@plt>
