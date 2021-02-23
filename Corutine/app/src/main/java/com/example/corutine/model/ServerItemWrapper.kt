package com.example.corutine.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerItemWrapper<T>(
    val items : List<T>
) {
}