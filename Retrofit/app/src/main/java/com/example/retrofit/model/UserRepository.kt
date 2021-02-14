package com.example.retrofit.model

import com.example.retrofit.network.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class UserRepository {

    fun searchUsers(
        query: String,
        onComplete : (List<RemoteUser>) -> Unit,
        onError : (Throwable) -> Unit
    ){
        Network.githubApi.searchUsers(query).enqueue(
            object : Callback<ServerItemWrapper<RemoteUser>> {
                override fun onResponse(
                    call: Call<ServerItemWrapper<RemoteUser>>,
                    response: Response<ServerItemWrapper<RemoteUser>>
                ) {
                    if (response.isSuccessful) {
                        onComplete(response.body()?.items.orEmpty())
                    }
                    else {
                        onError(RuntimeException("Что-то пошло не так"))
                    }
                }

                override fun onFailure(call: Call<ServerItemWrapper<RemoteUser>>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }
}