cmake_minimum_required(VERSION 3.22.1)
project(nantianmen_guard C)

add_library(nantianmen_guard SHARED guard.c)

target_link_libraries(nantianmen_guard log)
