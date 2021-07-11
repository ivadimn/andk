package ru.ivadimn.MaterialDesign.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface FileApi {
    @GET
    suspend fun getImage(
        @Url url : String
    ) : ResponseBody
}