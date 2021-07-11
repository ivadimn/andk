package ru.ivadimn.di.interfaces

import android.graphics.Bitmap
import android.net.Uri
import ru.ivadimn.di.data.entities.User

interface UserDetailRepository {

    suspend fun save(data: Array<String>)
    suspend fun update(data: Array<String>)
    suspend fun remove(userId: Long)
    suspend fun select(userId: Long) : User
    fun registerPhoto(bmp : Bitmap)
    fun getPhotoUri() : Uri
}