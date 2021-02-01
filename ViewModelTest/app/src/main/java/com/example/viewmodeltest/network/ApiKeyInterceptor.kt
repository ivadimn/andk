package com.example.viewmodeltest.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(
    val apiKey : String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentRequest = chain.request()
        val newUrl = currentRequest.url.newBuilder()
            .addQueryParameter("app_key", apiKey)
            .build()

        val newRequest = currentRequest.newBuilder()
            .url(newUrl)
            .build()
        Log.d("Eventfull Server", "url = ${newUrl.toString()}")
        return chain.proceed(newRequest)
    }
}