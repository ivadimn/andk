package ru.ivadimn.mediafiles.presentantion

import android.content.ContentUris
import android.database.DatabaseUtils
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.ivadimn.mediafiles.App
import ru.ivadimn.mediafiles.Media
import java.lang.Exception
import kotlin.random.Random

class ImageRepository  {

    /*private val images = listOf(
    "https://images.freeimages.com/images/large-previews/5b8/starfish-1377326.jpg",
    "https://images.freeimages.com/images/large-previews/fa8/swimming-pool-3-1378269.jpg",
    "https://images.freeimages.com/images/large-previews/f5d/butterfly-1378183.jpg",
    "https://images.freeimages.com/images/small-previews/25d/eagle-1523807.jpg",
    "https://images.freeimages.com/images/small-previews/0db/tropical-bird-1390996.jpg",
    "https://images.freeimages.com/images/small-previews/1c1/blue-water-sailing-1-1437302.jpg"
    )*/


    suspend fun getImages() : List<Media> {
        val images = mutableListOf<Media.Image>()
        withContext(Dispatchers.IO) {
        App.context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, null, null, null
        )?.use {
           it.moveToFirst()
           Log.d( "Media",  DatabaseUtils.dumpCursorToString(it))
           do {

              val id = it.getLong(it.getColumnIndex(MediaStore.MediaColumns._ID))
              val name = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
              val size = it.getInt(it.getColumnIndex(MediaStore.MediaColumns.SIZE))
              val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                       images += Media.Image(id, uri, name, size)
                    }
                    while (it.moveToNext())
                }
        }
        return images
    }

    suspend fun saveImage(name : String, url : String) {
        delay(1000)
    }

    suspend fun deleteImage(id : Long) {

    }
}