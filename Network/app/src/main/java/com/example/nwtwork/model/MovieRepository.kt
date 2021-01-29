package com.example.nwtwork.model

import android.util.Log
import com.example.nwtwork.network.Network
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MovieRepository  {


    //http://www.omdbapi.com/?apikey=[yourkey]&

    fun searchMovie(text : String, callback : (List<RemoteMovie>) -> Unit) : Call {

        return Network.getSearchMovieCall(text).apply {
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Server", "Error - ${e.message}")
                    callback(emptyList())
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseText = response.body?.string().orEmpty()
                        val movies = parseMovieResponse(responseText)
                        callback(movies)
                        Log.d("Server", "response successful = ${response.isSuccessful}")
                    } else {
                        callback(emptyList())
                    }
                }
            })
        }

        /*Thread {
            try {
                val response = Network.getSearchMovieCall(text).execute()
                val responseText = response.body?.string().orEmpty()
                val movies = parseMovieResponse(responseText)
                callback(movies)
                Log.d("Server", "response successful = ${response.isSuccessful}")
            }
            catch (e : IOException) {
                Log.e("Server", "Error - ${e.message}")
                callback(emptyList())
            }
        }.start() */
    }


    private fun parseMovieResponse(responseBody : String) : List<RemoteMovie> {
        return try {
            val rootObject = JSONObject(responseBody)
            val movieArray = rootObject.getJSONArray("Search")
            (0 until movieArray.length()).map { index -> movieArray.getJSONObject(index) }
                    .map { movieJson ->
                        val title = movieJson.getString("Title")
                        val year = movieJson.getString("Year")
                        val id = movieJson.getString("imdbID")
                        RemoteMovie(id, title, year, "Unknown" )
                    }
        }
        catch (e : JSONException) {
            Log.e("Server", "Parse response error - ${e.message}")
            emptyList()
        }
    }
}