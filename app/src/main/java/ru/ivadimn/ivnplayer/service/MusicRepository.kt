package ru.ivadimn.ivnplayer.service

import android.content.ContentUris
import android.database.Cursor
import android.provider.MediaStore
import kotlinx.coroutines.*
import ru.ivadimn.ivnplayer.App
import ru.ivadimn.ivnplayer.haveQ
import ru.ivadimn.ivnplayer.haveR
import kotlin.coroutines.suspendCoroutine

class MusicRepository {

    private var tracks = mutableListOf<Track>()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO )

    private var currentPosition = 0

    init {
        scope.launch {
            getTracks()
        }
    }

    private suspend fun getTracks() {
        withContext(Dispatchers.IO) {
            tracks.addAll(getListTrackFromCursor(
                App.context.contentResolver.query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    null, null, null, null
                ))
            )
        }
    }


    private fun getListTrackFromCursor(cursor: Cursor?) : List<Track> {
        val audios = mutableListOf<Track>()
        cursor?.use {
            while (it.moveToNext()) {
                if (haveQ()) {
                    val pending = it.getInt(it.getColumnIndex(MediaStore.MediaColumns.IS_PENDING))
                    if (pending == 1) continue
                }
                val id = it.getLong(it.getColumnIndex(MediaStore.MediaColumns._ID))
                val name = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
                val title = it.getString(it.getColumnIndex(MediaStore.MediaColumns.TITLE))
                val size = it.getLong(it.getColumnIndex(MediaStore.MediaColumns.SIZE))
                val album = if (haveR())
                    it.getString(it.getColumnIndex(MediaStore.MediaColumns.ALBUM))
                else
                    "Unknown"
                val duration = if (haveR())
                    it.getLong(it.getColumnIndex(MediaStore.MediaColumns.DURATION))
                else
                    0
                val artist = if (haveR())
                    it.getString(it.getColumnIndex(MediaStore.MediaColumns.ARTIST))
                else
                    "Unknown"

                val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                audios.add(
                    Track(id = id, name = name, title = title, size = size,
                uri = uri, artist = artist, duration = duration, album = album)
                )
            }
        }
        return audios
    }

    fun getCurrent() : Track = tracks[currentPosition]

    fun getNext() : Track {
        currentPosition++
        if (currentPosition == tracks.size) {
            currentPosition = 0
        }
        return tracks[currentPosition]
    }

    fun getPrevious() : Track {
        currentPosition--
        if (currentPosition < 0) {
            currentPosition = tracks.size - 1
        }
        return tracks[currentPosition]
    }

}