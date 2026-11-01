set pagination off
set confirm off
set debuginfod enabled off
target remote :1250
handle SIGABRT stop nopass
continue
dump memory /workspace/yan_reverse/dyn/dump_bitcode.bin 0x400000be2a20 0x400000be8158
dump memory /workspace/yan_reverse/dyn/dump_image.bin 0x400000b40000 0x400000bf2000
info registers pc
quit
