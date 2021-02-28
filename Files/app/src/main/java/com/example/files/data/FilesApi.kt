package com.example.files.data

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface FilesApi {
    @GET
    suspend fun getFile(
        @Url url : String
    ) : ResponseBody
}