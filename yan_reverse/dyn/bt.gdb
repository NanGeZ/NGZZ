set pagination off
set confirm off
set debuginfod enabled off
target remote :1250
handle SIGABRT stop nopass
continue
bt full
info symbol $pc
x/8gx $sp
quit
