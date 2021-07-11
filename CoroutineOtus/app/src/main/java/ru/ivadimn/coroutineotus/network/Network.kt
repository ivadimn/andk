package ru.ivadimn.coroutineotus.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {
    val okHttpClient = OkHttpClient.Builder()
        .build()

    fun getRetrofit() =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://aws.random.cat")
            .build()

}