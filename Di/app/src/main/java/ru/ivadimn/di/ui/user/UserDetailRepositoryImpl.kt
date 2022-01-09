package ru.ivadimn.di.ui.user

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.os.FileUriExposedException
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import org.threeten.bp.Instant
import ru.ivadimn.di.App
import ru.ivadimn.di.data.UserDao
import ru.ivadimn.di.data.entities.User
import ru.ivadimn.di.exceptions.IncorrectFormException
import ru.ivadimn.di.haveQ
import ru.ivadimn.di.interfaces.UserDetailRepository
import java.io.File
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*

class UserDetailRepositoryImpl (
    private val dao: UserDao
) : UserDetailRepository {


    private var userPhoto : Bitmap? = null
    private var tmpUser : User? = null

    lateinit var currentPhotoPath: String

    private fun savePhoto(bitmap : Bitmap, filename : String) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            throw RuntimeException("External storage unavailable")
        }

        val folder = App.context.getExternalFilesDir(App.MEDIA_EXTERNAL_DIR)
        val file = File(folder, filename)

        file.outputStream().use { outStrema ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outStrema)
        }
    }

    private fun isUserValid(user: User) : Boolean {
        return user.name.isNotBlank() && user.family.isNotBlank()
                && Patterns.EMAIL_ADDRESS.matcher(user.email).matches()
                && Patterns.PHONE.matcher(user.phone).matches()
    }

    override suspend fun save(data: Array<String>) {
        val user = User(0, data[NAME], data[FAMILY], data[PHONE], data[EMAIL], "", Instant.now(), Instant.now())
        Log.d("DINJ", "repository save $user")

        if (!isUserValid(user)) throw IncorrectFormException()
        user.apply {
            if (userPhoto != null) {
                photo = "$family$name${phone.substring(1)}"
                savePhoto(userPhoto!!, photo)
            }
        }
        dao.insert(listOf(user))
    }

    override suspend fun update(data: Array<String>) {
        val user = User(tmpUser!!.id, data[NAME], data[FAMILY], data[PHONE], data[EMAIL], tmpUser!!.photo,
            tmpUser!!.createdAt, Instant.now())
        Log.d("DINJ", "repository update - $user")
        val isValid = isUserValid(user)
        Log.d("DINJ", "repository update is Valid- $isValid")
        if (!isValid) throw IncorrectFormException()
        user.apply {
            if (userPhoto != null) {
                if (photo.isEmpty()) {
                    photo = "$family$name${phone.substring(1)}"
                    Log.d("DINJ", "repository update photo- $photo")
                }
                savePhoto(userPhoto!!, photo)
            }
        }
        dao.update(user)
    }

    override suspend fun remove(userId: Long) {
        dao.deleteById(userId)
    }

    override suspend fun select(userId: Long): User {
        tmpUser = dao.selectUser(userId)
        return  tmpUser!!
    }

    override fun registerPhoto(bmp: Bitmap) {
        userPhoto = bmp
    }

   /*override fun getPhotoUri() : Uri {
        val volume = if (haveQ()) {
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        }
        else {
            MediaStore.VOLUME_EXTERNAL
        }
        val tempFilename = "${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}.jpg"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, tempFilename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (haveQ()) {
                put(MediaStore.MediaColumns.IS_PENDING, 1)
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures")
            }
        }
        val collectionUri = MediaStore.Images.Media.getContentUri(volume)
         Log.d("DINJ", "Collection uri - $collectionUri")
        val uri = App.context.contentResolver.insert(collectionUri, contentValues)
        Log.d("DINJ", "Photo uri - $uri")
        if (haveQ()) {
            uri?.let {
                App.context.contentResolver.update(
                    it,
                    ContentValues().apply {
                        put(MediaStore.MediaColumns.IS_PENDING, 0)
                    }, null, null
                )
            }
        }
        return uri!!
    }*/

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = App.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun getPhotoUri() : Uri {
        val photoFile = createImageFile()

        if (!photoFile.isRooted) {
            Log.d("DINJ", "File was not create")
        }
        val photoURI: Uri = FileProvider.getUriForFile(
            App.context,
            "ru.ivadimn.di.file_provider",
            photoFile
        )
        Log.d("DINJ", "Collection uri - $photoURI")
        return photoURI
    }

    /*override suspend fun getPhotoUri() : Uri {
        val folder = App.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        //val file = File(folder, fileName)
        val file = createImageFile()
        //if (!file.exists()) return null
        val uri = FileProvider.getUriForFile(
            App.context,
            "ru.ivadimn.di.file_provider",
            file
        )
        Log.d("LoadFile", "file uri- $uri")
        return uri
    } */

    companion object {
        const val DATA_LEN = 4
        const val NAME = 0
        const val FAMILY = 1
        const val PHONE = 2
        const val EMAIL = 3
    }

}