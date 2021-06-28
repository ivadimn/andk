package ru.ivadimn.material.ui.images

import android.content.ContentUris
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ivadimn.material.App
import ru.ivadimn.material.haveQ
import ru.ivadimn.material.model.Image

class ImageListRepository {

    private suspend fun getImages() : List<Image> {

        val images = mutableListOf<Image>()
        withContext(Dispatchers.IO) {
            App.context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null
            )?.use {
                //Log.d( "Media",  DatabaseUtils.dumpCursorToString(it))
                while (it.moveToNext()) {
                    if (haveQ()) {
                        val pending = it.getInt(it.getColumnIndex(MediaStore.MediaColumns.IS_PENDING))
                        if (pending == 1) continue
                    }
                    val id = it.getLong(it.getColumnIndex(MediaStore.MediaColumns._ID))
                    val name = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
                    val size = it.getLong(it.getColumnIndex(MediaStore.MediaColumns.SIZE))
                    val width = it.getInt(it.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH))
                    val height = it.getInt(it.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT))

                    val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    val description = "Description"
                    images += Image(id, uri, name, description, size, width, height)
                }
            }
        }
        return images
    }
}