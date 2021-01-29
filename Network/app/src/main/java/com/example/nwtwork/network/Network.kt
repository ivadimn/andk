package com.example.nwtwork.network

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

object Network {

    private val MOVIE_API_KEY = "2e0fc156"

    val client = OkHttpClient()

    fun getSearchMovieCall(text : String) : Call {
        val url = HttpUrl.Builder()
                .scheme("http")
                .host("www.omdbapi.com")
                .addQueryParameter("apikey", MOVIE_API_KEY)
                .addQueryParameter("s", text)
                .build()

        val request = Request.Builder()
                .get()
                .url(url)
                .build()

        return client.newCall(request)
    }
}