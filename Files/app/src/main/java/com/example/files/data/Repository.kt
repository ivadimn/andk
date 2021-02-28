package com.example.files.data

import android.app.Application
import android.content.Context
import android.os.Environment
import android.util.Log
import com.example.files.TestApplication
import java.io.File

class Repository  {

    suspend fun getFile(url : String) : Boolean {

        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED)
            return false
        val folder = TestApplication.context.getExternalFilesDir("testFolder")
        val file = File(folder, "net_file.txt")
        try {
            file.outputStream().use { outStream ->
                Networking.filesApi
                    .getFile(url)
                    .byteStream()
                    .use { inputStream ->
                        inputStream.copyTo(outStream)
                    }
            }
            Log.d("ExternalStorageFragment", "File was loaded")
            return true
        }
        catch (t : Throwable) {
            Log.d("ExternalStorageFragment", "Error: ${t.message}")
            return false
        }
    }
}