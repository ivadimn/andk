package ru.ivadimn.coroutineotus.network

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import ru.ivadimn.coroutineotus.CatResponse
import java.lang.RuntimeException
import kotlin.coroutines.resumeWithException

interface CatService {
    @GET("meow")
    fun getCat() : Call<CatResponse>
}

suspend fun <T> Call<T>.awaitResult() = suspendCancellableCoroutine<T> { continuation ->
    continuation.invokeOnCancellation { cancel() }
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                continuation.resumeWith(Result.success(response.body()!!))
            }
            else {
                continuation.resumeWith( Result.failure(RuntimeException(response.errorBody().toString())))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWith(Result.failure(t))
        }

    })
}

suspend fun <T> Call<T>.awaitR() = suspendCancellableCoroutine<T> { continuation ->
    continuation.invokeOnCancellation { cancel() }
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                continuation.resume(response.body()!!)  {
                    cancel()
                }
            }
            else {
                continuation.resumeWithException(RuntimeException(response.errorBody().toString()))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWithException(t)
        }

    })
}