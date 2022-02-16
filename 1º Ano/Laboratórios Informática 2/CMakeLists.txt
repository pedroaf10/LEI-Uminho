cmake_minimum_required(VERSION 3.13)
project(projetp_reversi C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(projetp_reversi
        estado.c
        estado.h
        main.c)
