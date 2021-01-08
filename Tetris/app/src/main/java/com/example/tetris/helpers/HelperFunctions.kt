package com.example.tetris.helpers

fun array2dOfByte(sizeOuter : Int, sizeInner : Int) : Array<ByteArray> {
    return Array(sizeOuter) { ByteArray(sizeInner)}
}