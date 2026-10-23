cmake_minimum_required(VERSION 3.22.1)
project(ngzz)

add_library(ngzz SHARED ngzz_engine.cpp)
add_library(ngzzpack SHARED ngzz_pack.cpp)

find_library(log-lib log)
find_library(z-lib z)

target_link_libraries(ngzz ${log-lib})
target_link_libraries(ngzzpack ${log-lib} ${z-lib})
