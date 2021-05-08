package ru.ivadimn.progressfile

import okhttp3.Interceptor
import okhttp3.Response

class ProgressInterceptor(
    private val progressListener: ProgressListener
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .body(originalResponse.body?.let { ProgressResponseBody(it, progressListener) })
            .build()
    }
}