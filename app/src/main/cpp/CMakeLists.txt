cmake_minimum_required(VERSION 3.22.1)
project(ngzz)

add_library(ngzz SHARED
    ngzz_engine.cpp
    ngzz_anti_hook.cpp
)

add_library(ngzzpack SHARED ngzz_pack.cpp)

find_library(log-lib log)
find_library(z-lib z)

target_link_libraries(ngzz ${log-lib})
target_link_libraries(ngzzpack ${log-lib} ${z-lib})

target_compile_options(ngzz PRIVATE
    -fvisibility=hidden
    -O2
    -fno-strict-aliasing
)

target_compile_options(ngzzpack PRIVATE
    -fvisibility=hidden
    -O2
)

target_compile_definitions(ngzz PRIVATE
    NGZZ_OBFUSCATE_STRINGS=1
)
