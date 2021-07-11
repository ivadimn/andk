package ru.ivadimn.MaterialDesign.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

object Network {
    private val httpClient = OkHttpClient.Builder()
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .client(httpClient)
        .build()


    val fileApi : FileApi
        get() = retrofit.create()
}