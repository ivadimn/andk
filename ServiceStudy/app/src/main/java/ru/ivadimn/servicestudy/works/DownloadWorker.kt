package ru.ivadimn.servicestudy.works

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay


class DownloadWorker(
    private val context: Context,
    private val params : WorkerParameters
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {
        val urlToDownload = inputData.getString(DOWNLOAD_URL_KEY)

        Log.d("Services", "Start work")
        delay(1000)
        Log.d("Services", "Finish work")
        return when(urlToDownload) {
            "1" -> Result.retry()
            "2" -> Result.failure()
            else -> Result.success()
        }

    }

    companion object {
        const val DOWNLOAD_URL_KEY = "DownloadUrl"
    }
}