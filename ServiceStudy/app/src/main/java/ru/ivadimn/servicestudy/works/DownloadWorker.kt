package ru.ivadimn.servicestudy.works

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay


class DownloadWorker(
    private val context: Context,
    private val params : WorkerParameters
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {
        delay(1000)
        return Result.success()
    }
}