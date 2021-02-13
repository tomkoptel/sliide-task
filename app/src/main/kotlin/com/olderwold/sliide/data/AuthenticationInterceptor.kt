package com.olderwold.sliide.data

import com.olderwold.sliide.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal object AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.GOREST_API_TOKEN}")
            .build()
            .let { chain.proceed(it) }
    }
}
