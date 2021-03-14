package com.example.conprovider.custom_content_provider

import android.app.backup.BackupAgent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val id : Long,
    val name : String,
    val age: Int
) {
}