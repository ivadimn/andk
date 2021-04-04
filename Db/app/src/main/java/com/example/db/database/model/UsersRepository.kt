package com.example.db.database.model

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.db.App
import com.example.db.database.Db
import org.threeten.bp.Instant
import java.io.ByteArrayInputStream
import java.io.File
import java.lang.RuntimeException
import java.nio.ByteBuffer
import java.util.regex.Pattern

class UsersRepository {

    private val userDao = Db.instance.userDao()

    suspend fun getAllUsers() : List<User> {
        return userDao.getAllUsers()
    }

    suspend fun removeUser(user: User) {
        userDao.removeUserById(user.id)
    }







}