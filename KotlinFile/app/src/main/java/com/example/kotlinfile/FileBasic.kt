package com.example.kotlinfile

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun main() {
    inputStreamExample()
}

fun basicFile() {
    val file = File("/home/vadim/Рабочий стол/textfile.txt")
    if (!file.exists()) {
        val result = file.createNewFile()
        println("file length ${file.length()}")
        println("file abs path ${file.absolutePath}")
        println("file name ${file.name}")
        //println("file created $result")
    }
    else {
        val result = file.delete()
        println("file deleted $result")
    }
}

fun basicFolder() {
    val folder = File("/home/vadim/Рабочий стол/textfiles")
    folder.mkdir()
    println("file.isFile = ${folder.isFile}")
    println("file.isDirectory = ${folder.isDirectory}")
}

fun relativePathExample() {
    val folder = File("testfolder/innerfolder/anyfiles")
    folder.mkdirs()
    val file = File(folder, "file2.txt")
    file.createNewFile()
    println("file abs path = ${file.absolutePath}")

    folder.listFiles()?.forEachIndexed { index, file ->
        println("file $index = ${file.absolutePath}")
    }
}

fun outputStreamExample() {
    val folder = File("testfolder/innerfolder")
    folder.mkdirs()
    val file = File(folder, "file0.txt")
    file.createNewFile()

    try {
        val outputStream = file.outputStream()
        outputStream.use {
            it.write("Какая-то строка текста".toByteArray())
        }
    }
    catch (e : IOException) {
        println(e.message)
    }

}

fun outputStreamBufferedExample() {
    val folder = File("testfolder/innerfolder")
    folder.mkdirs()
    val file = File(folder, "file0.txt")
    file.createNewFile()

    try {
        val start = System.currentTimeMillis()
        val outputStream = file.outputStream()
        outputStream.buffered().use {stream ->

            (0 .. 100_000).forEach {
                stream.write("$it\n".toByteArray())
            }
        }
        println("Total write time = ${System.currentTimeMillis() - start}")
    }
    catch (t : Throwable) {
        println(t.message)
    }

}

fun inputStreamExample() {
    val folder = File("testfolder/innerfolder")
    folder.mkdirs()
    val file = File(folder, "file0.txt")

    try {
        val text = file.inputStream()
                .bufferedReader()
                .use {
                    it.readText()
                }
        println(text)
    }
    catch (t : Throwable) {
        println(t.message)
    }
}