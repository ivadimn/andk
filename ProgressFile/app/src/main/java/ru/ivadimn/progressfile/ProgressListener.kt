package ru.ivadimn.progressfile


interface ProgressListener {
    fun update(bytesRead: Long, contentLength: Long, done: Boolean)
}