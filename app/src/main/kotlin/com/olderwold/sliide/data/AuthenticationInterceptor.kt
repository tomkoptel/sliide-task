package com.olderwold.sliide.data

import com.olderwold.sliide.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal object AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter("access-token", BuildConfig.GOREST_API_TOKEN)
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
