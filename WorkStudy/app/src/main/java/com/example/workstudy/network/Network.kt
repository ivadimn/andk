package com.example.workstudy.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

object Network {
    val httpClient = OkHttpClient.Builder()
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .client(httpClient)
        .build()

    val fileApi : FileApi
        get() = retrofit.create()
}