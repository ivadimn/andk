package com.example.workstudy.works

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.workstudy.App
import com.example.workstudy.network.Network
import kotlinx.coroutines.delay
import java.io.File

class Downloader(
    val context: Context,
    val params : WorkerParameters
) : CoroutineWorker(context, params) {

    private val fileApi = Network.fileApi

    override suspend fun doWork(): Result {

        Log.d("Work", "DoWork running ...")
        val url = inputData.getString(DOWNLOAD_URL_KEY)
        val fileName = inputData.getString(DOWNLOAD_FILENAME_KEY)
        if (url == null || fileName == null) {
            return Result.failure()
        }

        return try {
            download(url, fileName)
            Result.success()
        }
        catch (t : Throwable) {
            Log.d("Work", "DoWork exception - ${t.message} ")
            val errorData = workDataOf(DOWNLOAD_RESULT_KEY to t.message)
            Result.retry()
        }
        finally {
            Log.d("Work", "DoWork was finishing ... ")
        }
    }


    private suspend fun download(url : String, fileName : String) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            throw RuntimeException("External storage unavailable")
        }
        val folder = App.context.getExternalFilesDir(FILES_DATASTORE_NAME)
        val file = File(folder, fileName)
        file.outputStream().use { outStream ->
            fileApi.get(url)
                .byteStream().use { inStream ->
                    delay(7000)
                    inStream.copyTo(outStream)
                }
        }
    }

    companion object {
        private const val FILES_DATASTORE_NAME = "FilesDatastore"
        const val DOWNLOAD_URL_KEY = "DownloadUrl"
        const val DOWNLOAD_FILENAME_KEY = "DownloadFileName"
        const val DOWNLOAD_RESULT_KEY = "DownloadResult"
        const val DOWNLOAD_WORK_ID = "DownloadWorkId"
    }

}