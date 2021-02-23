package com.example.corutine.model

import com.example.corutine.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserListRepository {

    suspend fun searchUsers(query: String) : List<RemoteUser> {
        return withContext(Dispatchers.IO) {
            Network.githubApi.searchUsers(query).items
        }
    }

}