package ru.ivadimn.ipdf.data

import android.R.attr
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import ru.ivadimn.ipdf.App
import ru.ivadimn.ipdf.utils.haveQ
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap.CompressFormat

import android.R.attr.bitmap
import android.graphics.pdf.PdfDocument


class ScanRepository {

    private var image : Bitmap? = null
    private var pendingUri : Uri? = null
    private val outputStream = ByteArrayOutputStream()


    fun registerImage(img: Bitmap) {
        image = img
    }


    fun getImageUri() : Uri {
        val imageFile = createImageFile()

        if (!imageFile.isRooted) {
            Log.d("IPDF", "File was not create")
        }
        val uri: Uri = FileProvider.getUriForFile(
            App.context,
            "ru.ivadimn.ipdf.provider",
            imageFile
        )
        Log.d("IPDF", "Collection uri - $uri")
        return uri
    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd-HHmmss").format(Date())
        val storageDir = App.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    suspend fun publicImage(imageUri: Uri) {
        pendingUri = getPendingUri()
        download(imageUri, App.context.contentResolver.openOutputStream(pendingUri!!))
        clearPending()
    }

    private suspend fun download(imageUri: Uri, outStream : OutputStream?) {
        val inStream = App.context.contentResolver.openInputStream(imageUri)
        outStream?.use { outputStream ->
            inStream?.use { inputStream ->
                inputStream.copyTo(outputStream)
                Log.d("IPDF", "media downloaded")
            }
        }
    }


    private fun getPendingUri() : Uri {
        val tempFilename = "${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}.jpg"
        val volume = if (haveQ()) {
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        } else {
            MediaStore.VOLUME_EXTERNAL
        }
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, tempFilename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/*")

            if (haveQ()) {
                put(MediaStore.MediaColumns.IS_PENDING, 1)
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures")
            }
        }
        val collectionUri = MediaStore.Images.Media.getContentUri(volume)
        val uri = App.context.contentResolver.insert(collectionUri, values)

        return uri!!
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
    fun toPdf() {
        val doc = PdfDocument.
    }
}