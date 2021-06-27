package ru.ivadimn.ivnplayer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

object NotifyChannels {

    const val PLAYER_SERVICE_CHANNEL_ID = "PlayerServiceChannel"

    fun create(context: Context) {
        if (haveO()) {
            createPlayerServiceChannel(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createPlayerServiceChannel(context: Context) {
        val name = "IVNPlayer"
        val description = "Player service messages"
        val priority = NotificationManager.IMPORTANCE_LOW

        val channel = NotificationChannel(PLAYER_SERVICE_CHANNEL_ID, name, priority).apply {
            this.description = description
            vibrationPattern = longArrayOf(300, 300, 500, 500)
            enableVibration(true)
        }

        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }
}