package ru.ivadimn.MaterialDesign.ui.images

import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ivadimn.MaterialDesign.App
import ru.ivadimn.material.haveQ
import ru.ivadimn.material.model.Image
import ru.ivadimn.material.utils.Lorem
import kotlin.random.Random

class ImageListRepository {


    private var isShownSnackbar : Boolean = false
    val showSnackbar
    get() = isShownSnackbar


    fun showSnackbar() {
        isShownSnackbar = true
    }

    suspend fun getImages() : List<Image> {

        val images = mutableListOf<Image>()
        withContext(Dispatchers.IO) {
            ru.ivadimn.MaterialDesign.App.context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null
            )?.use {
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
                    val description = Lorem.generate(Random.nextInt(5, 10))
                    val longDescription = Lorem.generate(Random.nextInt(40, 60))
                    images += Image(id, uri, name, description,longDescription, size, width, height)
                }
            }
        }
        return images
    }
}