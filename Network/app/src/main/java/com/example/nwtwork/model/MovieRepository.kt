package com.example.nwtwork.model

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MovieRepository  {

    private val MOVIE_API_KEY = "2e0fc156"
    //http://www.omdbapi.com/?apikey=[yourkey]&

    fun searchMovie(text : String, callback : (List<RemoteMovie>) -> Unit) {

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

        val client = OkHttpClient()

        Thread {
            try {
                val response = client.newCall(request).execute()
                Log.d("Server", "response successful = ${response.isSuccessful}")
            }
            catch (e : IOException) {
                Log.e("Server", "Error - ${e.message}")
            }

            callback(emptyList())
        }.start()
    }
}