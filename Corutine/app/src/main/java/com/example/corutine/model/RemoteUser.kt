package com.example.corutine.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteUser(
    val id : Long,
    @Json(name = "login")
    val username : String,
    @Json(name = "avatar_url")
    val avatar : String?
)  {
}