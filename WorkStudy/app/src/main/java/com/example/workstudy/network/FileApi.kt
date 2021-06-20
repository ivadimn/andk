package com.example.workstudy.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface FileApi {
    @GET
    suspend fun get(
        @Url url : String
    ) : ResponseBody
}