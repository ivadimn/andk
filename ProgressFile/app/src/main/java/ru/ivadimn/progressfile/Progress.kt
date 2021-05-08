package ru.ivadimn.progressfile

import okhttp3.OkHttpClient
import okhttp3.Request

class Progress {

    val progressListener: ProgressListener = object : ProgressListener {
        var firstUpdate = true
        override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
            if (done) {
                println("completed")
            } else {
                if (firstUpdate) {
                    firstUpdate = false
                    if (contentLength == -1L) {
                        println("content-length: unknown")
                    } else {
                        println("content-length: $contentLength\n")
                    }
                }
                println(bytesRead)
                if (contentLength != -1L) {
                    println("${100 * bytesRead / contentLength}% done\n")
                }
            }
        }
    }

    fun run() {
        val request = Request.Builder()
            //.url("https://raw.githubusercontent.com/square/okhttp/master/README.md")
            .url("https://images.pexels.com/photos/6608313/pexels-photo-6608313.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
            .build()

        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(ProgressInterceptor(progressListener))
            .build()

        try {
            val response = client.newCall(request).execute()
            val bytes = response.body?.bytes()
            //println(response.body?.)
            println("Response body was came")
        }
        catch (t : Throwable) {
            println(t.message)
        }

    }
}

fun main() {
    Progress().run()
}


