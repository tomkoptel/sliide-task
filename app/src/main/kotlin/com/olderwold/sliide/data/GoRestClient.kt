package com.olderwold.sliide.data

import com.olderwold.sliide.domain.User
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

internal interface GoRestClient {
    val users: Single<List<User>>

    companion object {
        operator fun invoke(clientBuilder: OkHttpClient.Builder.() -> Unit = {}): GoRestClient {
            val api = Retrofit.Builder()
                .baseUrl("https://gorest.co.in")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(AuthenticationInterceptor)
                        .apply(clientBuilder)
                        .build()
                )
                .build()
                .create(Api::class.java)
            return Impl(api, UserMapper())
        }
    }

    private class Impl(
        private val api: Api,
        private val mapper: UserMapper,
    ) : GoRestClient {
        override val users: Single<List<User>>
            get() {
                return api.users().map { dto ->
                    mapper.map(dto.data)
                }
            }
    }

    private interface Api {
        @GET("/public-api/users")
        fun users(): Single<UsersResponseDTO>
    }
}
