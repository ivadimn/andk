package com.example.viewmodeltest.network

import android.util.Log
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object Network {

    private const val API_KEY = "Nhb4tMm7FPGWkdRX"
    val eventful_url = "http://api.eventful.com/json/"

    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(ApiKeyInterceptor(API_KEY))
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private lateinit var request: Request

    fun getCategoryList() : Call {
        val categoryUrl = HttpUrl.Builder()
            .scheme("http")
            .host("api.eventful.com")
            .addPathSegment("json")
            .addPathSegment("categories")
            .addPathSegment("list")
           // .addQueryParameter("app_key", API_KEY)
            .build()

        request = Request.Builder()
            .get()
            .url(categoryUrl)
            .build()

        return client.newCall(request)
    }

    fun getEventList(category : String, type : String, country : String) : Call {
        val eventUrl = HttpUrl.Builder()
            .scheme("http")
            .host("api.eventful.com")
            .addPathSegment("json")

        if (type.isNotEmpty()) {
            eventUrl.addPathSegment(type)
            eventUrl.addPathSegment("search")

        }
        if (category.isNotEmpty()){
            eventUrl.addQueryParameter("keywords", category)
        }

        if (country.isNotEmpty()) {
            eventUrl.addQueryParameter("l", country)
        }
        eventUrl.addQueryParameter("date", "Future")

        request = Request.Builder()
            .get()
            .url(eventUrl.build())
            .build()
        Log.d("Eventfull Server", "request now executing")
        return client.newCall(request)
    }
}
