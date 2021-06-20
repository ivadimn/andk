package com.example.workstudy.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Network {
    val httpClient = OkHttpClient.Builder()
        .build()



    val retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(httpClient)
        .build()

    val fileApi : FileApi
        get() = retrofit.create()
}