package ru.ivadimn.ivnplayer.service

import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.media.session.MediaButtonReceiver
import ru.ivadimn.ivnplayer.NotifyChannels

object Notification {

    fun from(context: Context, mediaSession: MediaSessionCompat) : NotificationCompat.Builder {
        val controller = mediaSession.controller
        val mediaMetadata = mediaSession.controller.metadata
        val description = mediaMetadata.description

        val builder = NotificationCompat.Builder(context, NotifyChannels.PLAYER_SERVICE_CHANNEL_ID)
        builder
            .setContentTitle(description.title)
            .setContentText(description.subtitle)
            .setSubText(description.description)
            .setContentIntent(controller.sessionActivity)
            .setDeleteIntent(
                MediaButtonReceiver.buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_STOP)
            )
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        return builder
    }
}