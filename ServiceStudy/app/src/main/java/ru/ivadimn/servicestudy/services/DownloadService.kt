package ru.ivadimn.servicestudy.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
        startForeground(NOTIFICATION_ID, createNotification(0, 0))
        scope.launch {
            DownloadState.changeDownloadState(true)
            Log.d("Service", "Download file was started")
            val maxProgress = 10
            (0 until  maxProgress).forEach {
                Log.d("Service", "Download file  ${(it + 1)} / $maxProgress")
                val updateNotification = createNotification(it + 1, maxProgress)
                NotificationManagerCompat.from(this@DownloadService)
                    .notify(NOTIFICATION_ID, updateNotification)
                delay(1000)
            }
            Log.d("Service", "Download file was finished")
            DownloadState.changeDownloadState(false)
            stopForeground(true)
            stopSelf()
        }
    }

    private fun createNotification(progress : Int, maxProgress : Int) : Notification {
        return NotificationCompat.Builder(this, NotificationChannels.DOWNLOAD_NOTIFICATION)
            .setContentTitle("Download progress")
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setProgress(maxProgress, progress, false)
            .setOnlyAlertOnce(true)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Service", "Service was destroy")
        scope.cancel()
    }

    companion object {
        const val NOTIFICATION_ID = 4321
    }
}