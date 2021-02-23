package com.example.corutine.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    //https://api.github.com/search/users
    @GET("/search/users")
    suspend fun searchUsers(
        @Query(value = "q")
        query: String
    ) : ServerItemWrapper<RemoteUser>
}