package ru.ivadimn.servicestudy.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import ru.ivadimn.servicestudy.haveO

object NotificationChannels {
    const val DOWNLOAD_NOTIFICATION = "Download_channel"


    fun create(context: Context) {
        if (haveO()) {
            createDownloadChannel(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createDownloadChannel(context: Context) {
        val name = "Download"
        val description = "Download channel"
        val priority = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(DOWNLOAD_NOTIFICATION, name, priority).apply {
            this.description = description
            enableVibration(false)
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }
}