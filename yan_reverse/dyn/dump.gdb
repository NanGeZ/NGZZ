set pagination off
set confirm off
handle SIGABRT stop nopass
continue
info sharedlibrary
printf "stopped at: "
x/i $pc
# .bitcode page range within packer: vaddr page 0xa2000-0xa9000, we'll compute from shared lib base
quit
