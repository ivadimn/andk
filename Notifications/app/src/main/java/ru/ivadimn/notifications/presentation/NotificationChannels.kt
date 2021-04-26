package ru.ivadimn.notifications.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import ru.ivadimn.notifications.BuildConfig
import ru.ivadimn.notifications.haveO

object NotificationChannels {

    const val MESSAGE_CHANNEL_ID = "Messages"
    const val NEWS_CHANNEL_ID = "News"

    fun create(context: Context) {
        if (haveO()) {
            createMessageChannel(context)
            createNewsChannel(context)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createMessageChannel(context: Context) {
        val name = "Message"
        val descriptionChannel = "Urgent messages"
        val priority  = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(MESSAGE_CHANNEL_ID, name, priority).apply {
            description = descriptionChannel
            enableVibration(true)
            vibrationPattern = longArrayOf(300, 200, 500, 500)
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNewsChannel(context: Context) {
        val name = "News"
        val channelDescription = "Last news"
        val priority  = NotificationManager.IMPORTANCE_LOW

        val channel = NotificationChannel(NEWS_CHANNEL_ID, name, priority).apply {
            description = channelDescription
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 1000, 1000)
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), null)
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }
}