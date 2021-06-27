package ru.ivadimn.ivnplayer.service

import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.media.session.MediaButtonReceiver
import ru.ivadimn.ivnplayer.MainActivity
import ru.ivadimn.ivnplayer.NotifyChannels
import ru.ivadimn.ivnplayer.R
import ru.ivadimn.ivnplayer.haveO

class PlayService : Service() {

    //builder метаданных трека
    private val metadataBuilder = MediaMetadataCompat.Builder()

    // ...состояния плеера
    // Здесь мы указываем действия, которые собираемся обрабатывать в коллбэках.
    // Например, если мы не укажем ACTION_PAUSE,
    // то нажатие на паузу не вызовет onPause.
    // ACTION_PLAY_PAUSE обязателен, иначе не будет работать
    // управление с Android Wear!
    private val stateBuilder = PlaybackStateCompat.Builder()
        .setActions(
            PlaybackStateCompat.ACTION_PLAY
                or PlaybackStateCompat.ACTION_STOP
                or PlaybackStateCompat.ACTION_PAUSE
                or PlaybackStateCompat.ACTION_PLAY_PAUSE
                or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            )

    private lateinit var mediaSession : MediaSessionCompat
    private lateinit var audioManager : AudioManager
    private lateinit var audioFocusRequest: AudioFocusRequest
    private var audioFocusRequested : Boolean = false

    private var currentState = PlaybackStateCompat.STATE_STOPPED

    private lateinit var mediaPlayer : MediaPlayer

    private val musicRepository = MusicRepository()

    override fun onCreate() {
        super.onCreate()

        if (haveO()) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setOnAudioFocusChangeListener(audioFocusChangeListener)
                .setAcceptsDelayedFocusGain(false)
                .setWillPauseWhenDucked(true)
                .setAudioAttributes(audioAttributes)
                .build()
        }
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        mediaSession = MediaSessionCompat(this, "PlayService")
        // FLAG_HANDLES_MEDIA_BUTTONS - хотим получать события от аппаратных кнопок
        // (например, гарнитуры)
        // FLAG_HANDLES_TRANSPORT_CONTROLS - хотим получать события от кнопок
        // на окне блокировки

        mediaSession.setFlags(
            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
        or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS )
        mediaSession.setCallback(mediaSessionCallback)

        val activityIntent = PendingIntent.getActivity(applicationContext, 0,
            Intent(applicationContext, MainActivity::class.java), 0)
        mediaSession.setSessionActivity(activityIntent)

        val mediaButtonIntent = Intent(Intent.ACTION_MEDIA_BUTTON, null, applicationContext,
            MediaButtonReceiver::class.java  )
        mediaSession.setMediaButtonReceiver(PendingIntent.getBroadcast(applicationContext, 0, mediaButtonIntent, 0))

        mediaPlayer = MediaPlayer()

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        MediaButtonReceiver.handleIntent(mediaSession, intent)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.release()
        mediaPlayer.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return PlayServiceBinder()
    }

    inner class PlayServiceBinder : Binder() {
        fun getMediaSessionToken() : MediaSessionCompat.Token  {
            return mediaSession.sessionToken
        }
    }

    private val mediaSessionCallback = object : MediaSessionCompat.Callback() {

        private var currentUri : Uri? = null


        override fun onPlay() {
            if (!mediaPlayer.isPlaying) {
                startService(Intent(applicationContext, PlayService::class.java))
                val track = musicRepository.getCurrent()
                updateMetadataFromTrack(track)

                prepareToPlay(track.uri)
                if (!audioFocusRequested) {
                    audioFocusRequested = true

                    var audioFocusResult = AudioManager.AUDIOFOCUS_REQUEST_GRANTED
                    if (haveO()) {
                        audioFocusResult = audioManager.requestAudioFocus(audioFocusRequest)
                    }
                    if (audioFocusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                        return
                }

                mediaSession.isActive = true
                registerReceiver(becomingNoisyReceiver, IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY))

                mediaPlayer.start()

            }

            mediaSession.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1.0F).build())
            currentState = PlaybackStateCompat.STATE_PLAYING

            refreshNotificationAndForegroundStatus(currentState)

        }

        override fun onPause() {
            pause()
        }

        override fun onStop() {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                unregisterReceiver(becomingNoisyReceiver)
            }
            if (audioFocusRequested) {
                audioFocusRequested = false

                if (haveO())
                    audioManager.abandonAudioFocusRequest(audioFocusRequest)
            }
            mediaSession.isActive = false
            mediaSession.setPlaybackState(stateBuilder.setState(
                PlaybackStateCompat.STATE_STOPPED,
                PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1.0F
            ).build())

            currentState = PlaybackStateCompat.STATE_STOPPED

            refreshNotificationAndForegroundStatus(currentState)
            stopSelf()
        }


        override fun onSkipToNext() {
            val track= musicRepository.getNext()
            updateMetadataFromTrack(track)

            refreshNotificationAndForegroundStatus(currentState)
            prepareToPlay(track.uri)
        }

        override fun onSkipToPrevious() {
            val track= musicRepository.getPrevious()
            updateMetadataFromTrack(track)

            refreshNotificationAndForegroundStatus(currentState)
            prepareToPlay(track.uri)
        }

        private fun prepareToPlay(uri : Uri) {
            if (uri != currentUri) {
                currentUri = uri
                mediaPlayer.setDataSource(applicationContext, uri)
                mediaPlayer.prepare()
            }
        }

        private fun updateMetadataFromTrack(track : Track) {
            metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, track.title)
            metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, track.artist)
            metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, track.album)
            metadataBuilder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, track.duration)
            mediaSession.setMetadata(metadataBuilder.build())
        }
    }

    private val audioFocusChangeListener =
        AudioManager.OnAudioFocusChangeListener { focusChange ->
            when(focusChange) {
                AudioManager.AUDIOFOCUS_GAIN -> mediaSessionCallback.onPlay()
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ->mediaSessionCallback.onPause()
                else -> mediaSessionCallback.onPause()
            }
        }

    private val becomingNoisyReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY == intent?.action) {
                pause()
            }
        }
    }


    private fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            unregisterReceiver(becomingNoisyReceiver)
        }
        mediaSession.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
            PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build())
        currentState = PlaybackStateCompat.STATE_PAUSED

        refreshNotificationAndForegroundStatus(currentState);
    }

    private fun refreshNotificationAndForegroundStatus(playbackState: Int) {
        when(playbackState) {
            PlaybackStateCompat.STATE_PLAYING ->
                startForeground(NOTIFICATION_ID, getNotification(playbackState))
            PlaybackStateCompat.STATE_PAUSED -> {
                NotificationManagerCompat.from(this@PlayService)
                    .notify(NOTIFICATION_ID, getNotification(playbackState))
                stopForeground(false)
            }
            else -> stopForeground(true)
        }
    }

    private fun getNotification(playbackState : Int) : android.app.Notification {
        val builder = Notification.from(this, mediaSession)
        builder.addAction(NotificationCompat.Action(android.R.drawable.ic_media_previous, "Previous",
            MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS )))

        if (playbackState == PlaybackStateCompat.STATE_PLAYING) {
            builder.addAction(NotificationCompat.Action(android.R.drawable.ic_media_pause, "Pause",
                MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY_PAUSE )))
        }
        else {
            builder.addAction(NotificationCompat.Action(android.R.drawable.ic_media_play, "Play",
                MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY)))
        }

        builder.addAction(NotificationCompat.Action(android.R.drawable.ic_media_next, "Next",
            MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_NEXT )))

        builder.setStyle(androidx.media.app.NotificationCompat.MediaStyle()
           .setShowActionsInCompactView(1)
           .setShowCancelButton(true)
           .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_STOP))
           .setMediaSession(mediaSession.sessionToken))

        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.color = ContextCompat.getColor(this, R.color.purple_700)
        builder.setShowWhen(false)
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setOnlyAlertOnce(true)
        builder.setChannelId(NotifyChannels.PLAYER_SERVICE_CHANNEL_ID)

        return builder.build()
    }


    companion object {
        const val NOTIFICATION_ID = 4040
        const val NOTIFICATION_CHANNEL_ID = "PlayerServiceChannel"
    }

}