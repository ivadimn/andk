package com.example.db.database.model.user

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.example.db.App
import com.example.db.database.Db
import org.threeten.bp.Instant
import java.io.ByteArrayInputStream
import java.io.File
import java.lang.RuntimeException
import java.nio.ByteBuffer
import java.util.regex.Pattern

class UserDetailRepository {

    private val phonePattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{6,12}\$")
    private val emailPattern = Pattern.compile("^.+@.+\\..{2,5}\$")

    private val userDao = Db.instance.userDao()

    private var tmpUser : User? = null
    private var userPhoto : Bitmap? = null


    suspend fun insertUser(name : String, family : String, phone : String, email : String) : Int {
        if (isDataValid(name, family, phone, email)) {
            var fileName = ""
            if (userPhoto != null) {
                fileName = "$family$name${phone.substring(1)}"
                savePhoto(userPhoto!!, fileName)
            }
            val user = User(0, name, family, phone, email, fileName,
                Instant.now(), Instant.now())
            userDao.insertUsers(listOf(user))
            return  1
        }
        else {
            return 0
        }
    }

    suspend fun getUserById(userId : Long) : User? {
        tmpUser = userDao.getUserById(userId)
        return  tmpUser ?: throw RuntimeException("User not found")
    }

    suspend fun updateUser(name : String, family : String, phone : String, email : String) : Int {
        if (isDataValid(name, family, phone, email)) {
            if (userPhoto != null) {
                savePhoto(userPhoto!!, tmpUser!!.photo)
            }
            val user = User(tmpUser!!.id, name, family, phone, email, tmpUser!!.photo,
                tmpUser!!.createdAt, Instant.now())
            userDao.updateUser(user)
            return  1
        }
        else {
            return 0
        }
    }

    private suspend fun savePhoto(bmp : Bitmap, fileName : String) {

        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            error(RuntimeException("External storage unavailable"))
        }

        val buffer = ByteBuffer.allocate(bmp.byteCount)
        bmp.copyPixelsToBuffer(buffer)
        val bmpStream  = ByteArrayInputStream(buffer.array())

        val folder = App.context.getExternalFilesDir(CHAT_EXTERNAL_DIR)
        val file = File(folder, fileName)

        file.outputStream().use { outStream ->
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, outStream)
        }
        Log.d("Add user", "Path result - ${file.absolutePath}")
    }

    fun registerPhoto(bmp: Bitmap) {
        userPhoto = bmp
    }

    private fun isDataValid(name : String, family : String, phone : String, email : String) : Boolean {
        return name.isNotEmpty() && family.isNotEmpty()
                && phonePattern.matcher(phone).matches()
                && emailPattern.matcher(email).matches()
    }

    companion object {
        const val CHAT_EXTERNAL_DIR = "chat_users_photo"
    }
}