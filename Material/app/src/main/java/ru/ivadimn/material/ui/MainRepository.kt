package ru.ivadimn.material.ui

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ivadimn.material.App
import ru.ivadimn.material.haveQ
import ru.ivadimn.material.network.Network
import java.io.OutputStream

class MainRepository  {


    private val urls = listOf(
        "https://images.pexels.com/photos/2534475/pexels-photo-2534475.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/3803593/pexels-photo-3803593.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.freeimages.com/images/large-previews/5b8/starfish-1377326.jpg",
        "https://images.freeimages.com/images/large-previews/fa8/swimming-pool-3-1378269.jpg",
        "https://images.freeimages.com/images/large-previews/f5d/butterfly-1378183.jpg",
        "https://images.freeimages.com/images/small-previews/25d/eagle-1523807.jpg",
        "https://images.freeimages.com/images/small-previews/0db/tropical-bird-1390996.jpg",
        "https://images.freeimages.com/images/small-previews/1c1/blue-water-sailing-1-1437302.jpg",
        "https://images.pexels.com/photos/7456416/pexels-photo-7456416.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
    )

    private val sharedPref : SharedPreferences
        = App.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    private var pendingUri : Uri? = null

    fun isFirstRunning() : Boolean{
        val count = sharedPref.getInt(COUNT_RUN, 0)
        sharedPref.edit()
            .putInt(COUNT_RUN, count + 1)
            .apply()
        return count == 0
    }

    suspend fun loadImages() {
        urls.forEach {
            saveImage(it)
        }
    }

    private suspend fun saveImage(url : String) {
        val fileName = buildFileName(url)
        withContext(Dispatchers.IO) {
            pendingUri = saveImageDetail(fileName, url)
            if (pendingUri != null) {
                download(
                    url,
                    App.context.contentResolver.openOutputStream(pendingUri!!)
                )
                clearPending()
            }
        }
    }

    private fun saveImageDetail(name: String, url: String): Uri? {
        val mimeType = getMimeType(url)
        val volume = if (haveQ()) {
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        } else {
            MediaStore.VOLUME_EXTERNAL
        }
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            if (haveQ()) {
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }
        if (mimeType.substringBefore("/") != "image")
            return null

        if (haveQ())
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures")
        val collectionUri = MediaStore.Images.Media.getContentUri(volume)
        val pUri = App.context.contentResolver.insert(collectionUri, values)
        return pUri
    }

    private suspend fun download(url: String, stream : OutputStream?) {
        stream?.use { outStream ->
            Network.fileApi
                .getImage(url)
                .byteStream().use { inputStream ->
                    inputStream.copyTo(outStream)
                    Log.d("Material", "media downloaded $url")
                }
        }
    }

    private fun clearPending() {
        if (haveQ()) {
            pendingUri?.let {
                App.context.contentResolver.update(it,
                    ContentValues().apply {
                        put(MediaStore.MediaColumns.IS_PENDING, 0)
                    }, null, null
                )
            }
            pendingUri = null
        }
    }



    private fun buildFileName(url : String) : String{
        val pattern ="[\\W\\d]".toRegex()
        val fullname = url.substring(url.lastIndexOf("/") + 1).substringBefore("?")
        val name = fullname.substringBefore(".")
        return "${name.replace(pattern, "")}.${fullname.substringAfter(".")}"
    }

    fun getMimeType(url : String) : String {
        val ext = MimeTypeMap.getFileExtensionFromUrl(url)
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext) ?: "*/*"
    }



    companion object {
        const val SHARED_PREFERENCES_NAME = "MaterialSharedPref"
        const val COUNT_RUN = "CountRun"
    }
}