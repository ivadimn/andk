package com.example.nwtwork.network

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object Network {

    private val MOVIE_API_KEY = "2e0fc156"

    val flipperNetworkPlugin = NetworkFlipperPlugin()

    val client = OkHttpClient.Builder()
            .addNetworkInterceptor(CustomHeaderInterceptor("Header1", "Value1"))
            .addNetworkInterceptor(CustomHeaderInterceptor("Header2", "Value2"))
            .addNetworkInterceptor(HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
        .addNetworkInterceptor(FlipperOkhttpInterceptor(flipperNetworkPlugin))
            .build()

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