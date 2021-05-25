package ru.ivadimn.servicestudy.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class DownloadService : Service() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("Service", "DownloadService was created on ${Thread.currentThread().name}")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Service", "DownloadService was started on ${Thread.currentThread().name}")
        downloadFile()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun downloadFile() {
        scope.launch {
            DownloadState.changeDownloadState(true)
            Log.d("Service", "Download file was started")
            val maxProgress = 5
            (0 until  maxProgress).forEach {
                Log.d("Service", "Download file  ${(it + 1)} / $maxProgress")
                delay(1000)
            }
            Log.d("Service", "Download file was finished")
            DownloadState.changeDownloadState(false)
            stopSelf()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Service", "Service was destroy")
        scope.cancel()
    }
}