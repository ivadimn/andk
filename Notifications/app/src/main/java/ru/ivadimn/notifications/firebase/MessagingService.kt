package ru.ivadimn.notifications.firebase

import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.ivadimn.notifications.MainActivity
import ru.ivadimn.notifications.R
import ru.ivadimn.notifications.presentation.NotificationChannels
import ru.ivadimn.notifications.presentation.NotificationFragment

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val user = message.data[USER]
        val msg = message.data[MESSAGE]

        if (user != null && msg != null) {
            showNotification(user, msg)
        }
    }


    private fun showNotification(user : String, message : String) {

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 999, intent, 0)

        val largeIcon  = BitmapFactory.decodeResource(resources, R.drawable.contact2)

        val notification = NotificationCompat.Builder(this, NotificationChannels.MESSAGE_CHANNEL_ID)
            .setContentTitle("You have new message from $user")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_message)
            .setLargeIcon(largeIcon)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()


        NotificationManagerCompat.from(this)
            .notify(user.hashCode(), notification)
    }

    companion object {
        const val USER = "user"
        const val MESSAGE = "message"
    }
}