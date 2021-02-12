package com.example.retrofit.model

class UserRepository {

    fun searchUsers(
        query: String,
        onComplete : (List<RemoteUser>) -> Unit,
        onError : (Throwable) -> Unit
    ){

    }
}