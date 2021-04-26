package ru.ivadimn.notifications.recivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.ivadimn.notifications.R
import ru.ivadimn.notifications.presentation.NotificationChannels
import ru.ivadimn.notifications.presentation.NotificationFragment

class BatteryBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        val isLow = intent?.action == Intent.ACTION_BATTERY_LOW
        if (isLow) {
            showLowBatteryNotification(context)
        }
        else {
            hideLowBatteryNotification(context)
        }
    }

    private fun showLowBatteryNotification(context: Context) {
        val notification = NotificationCompat.Builder(context, NotificationChannels.MESSAGE_CHANNEL_ID)
            .setContentTitle("Battery is low")
            .setSmallIcon(R.drawable.ic_notifications)
            .build()


        NotificationManagerCompat.from(context)
            .notify(BATTERY_NOTIFICATION_ID, notification)
    }

    private fun hideLowBatteryNotification(context: Context) {
        NotificationManagerCompat.from(context)
            .cancel(BATTERY_NOTIFICATION_ID)
    }

    companion object {
        const val BATTERY_NOTIFICATION_ID = 12345
    }
}