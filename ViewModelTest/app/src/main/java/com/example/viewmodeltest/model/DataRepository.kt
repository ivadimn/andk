package com.example.viewmodeltest.model

import android.util.Log
import com.example.viewmodeltest.network.Network
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import org.threeten.bp.Instant
import java.io.IOException

class DataRepository  {

    private val types_info = mapOf("События" to "events", "Места" to "venues",
        "Исполнители" to "performes", "Требования" to "demands")

    private val countries = listOf("Argentina", "United States", "Canada", "Germany", "France",
        "Spain", "Brazil", "China")

    private var categories : List<String> = emptyList()

    inner class CallbackHandler(
        val callback: (List<Event>, Boolean, String?) -> Unit
    ) : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("Eventfull Server", "Error - ${e.message}")
            callback(emptyList(), true, e.message)
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val responseText = response.body?.string().orEmpty()
                val events = parseEvents(responseText)
                callback(events, false, null)
                Log.d("Eventfull Server", "response successful = ${response.isSuccessful}")
            } else {
                Log.d("Eventfull Server", "response failuer = ${response.message}")
                callback(emptyList(), false, response.message)
            }
        }
    }


    fun getCategoryList(callback : (List<String>) -> Unit) {
        if (categories.isEmpty()) {
            Thread {
                val response = Network.getCategoryList().execute()
                val responseBody = response.body?.string().orEmpty()
                categories = parseCategories(responseBody)
                callback(categories)
            }.start()
        }
        else {
            callback(categories)
        }

    }

    fun getTypeInfoList() : List<String> = types_info.keys.toList()
    fun getCountries() : List<String> = countries

    fun getEvents(category : String, type : String, country : String,
        callback: (List<Event>, Boolean, String?) -> Unit) : Call {
        val typePath = types_info[type].orEmpty()
        Log.d("Eventfull Server", "repository getEvets")
        return Network.getEventList(category, typePath, country).apply {
            enqueue(CallbackHandler(callback))
        }

    }

    fun repeat(callback: (List<Event>, Boolean, String?) -> Unit) : Call {
        Log.d("Eventfull Server", "repository repeat")
        return Network.repateCall().apply {
            enqueue(CallbackHandler(callback))
        }
    }


    //{'category':
    // [
    // {'name': 'Concerts &amp; Tour Dates', 'event_count': None, 'id': 'music'},
    // {'name': 'Conferences &amp; Tradeshows', 'event_count': None, 'id': 'conference'},
    // {'name': 'Comedy', 'event_count': None, 'id': 'comedy'},
    // {'name': 'Education', 'event_count': None, 'id': 'learning_education'}
    // ]
    // }

    fun parseCategories(responseBody: String) : List<String>{
        return try {
            val rootObject = JSONObject(responseBody)
            val array  = rootObject.getJSONArray("category")
            (0 until array.length()).map { index -> array.getJSONObject(index) }
                .map { category ->
                        category.getString("id")
                }
        }
        catch (e : JSONException) {
            Log.d("Eventfull Server", "Failure parsing category - ${e.message}")
            emptyList<String>()
        }
    }

    fun parseEvents(responseBody: String) : List<Event> {
        return try {
            val rootObject = JSONObject(responseBody)
            val events = rootObject.getJSONObject("events")
            val array = events.getJSONArray("event")
            (0 until array.length()).map { index -> array.getJSONObject(index) }
                .map { eventObject ->
                    val id = eventObject.getString("id")
                    val title = eventObject.getString("title")
                    val country = eventObject.getString("country_name")
                    val rawDate = eventObject.getString("start_time")
                    var image = ""
                    try {
                        val imageObject = eventObject.getJSONObject("image")
                        if (imageObject != null) {
                            image = imageObject.getJSONObject("small").getString("url")
                            Log.d("Eventfull Server", "image - $image")
                        }
                    }
                    catch (e : JSONException) {
                        Log.d("Eventfull Server", "image is null")
                    }

                    Event(id, title, country, rawDate, image)

                }
        }
        catch (e : JSONException) {
            Log.d("Eventfull Server", "error parsing Events - ${e.message}")
            emptyList()
        }
    }
}