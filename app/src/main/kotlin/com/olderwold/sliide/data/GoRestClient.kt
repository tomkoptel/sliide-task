package com.olderwold.sliide.data

import android.annotation.SuppressLint
import com.olderwold.sliide.domain.User
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface GoRestClient {
    val users: Single<List<User>>

    fun create(user: User): Single<User>

    fun delete(userId: String): Completable

    companion object {
        @SuppressLint("NewApi")
        operator fun invoke(clientBuilder: OkHttpClient.Builder.() -> Unit = {}): GoRestClient {
            val gson = GsonFactory().create()

            val api = Retrofit.Builder()
                .baseUrl("https://gorest.co.in")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(AuthenticationInterceptor)
                        .apply(clientBuilder)
                        .build()
                )
                .build()
                .create(Api::class.java)

            val genderStatusMapper = GenderStatusMapper()
            val userStatusMapper = UserStatusMapper()
            return Impl(
                api = api,
                submitMapper = SubmitUserDTO.Mapper(genderStatusMapper, userStatusMapper),
                userDTOMapper = UserDto.Mapper(genderStatusMapper, userStatusMapper),
            )
        }
    }

    private class Impl(
        private val api: Api,
        private val submitMapper: SubmitUserDTO.Mapper,
        private val userDTOMapper: UserDto.Mapper,
    ) : GoRestClient {
        override val users: Single<List<User>>
            get() {
                return api.users().map { dto ->
                    dto.data.filterNotNull()
                        .mapNotNull(userDTOMapper::map)
                }
            }

        override fun create(user: User): Single<User> {
            return Single.fromCallable { submitMapper.map(user) }
                .flatMap { submitData ->
                    api.create(submitData).map { response ->
                        userDTOMapper.map(response.data)
                    }
                }
        }

        override fun delete(userId: String): Completable {
            return api.delete(userId).ignoreElement()
        }
    }

    private interface Api {
        @GET("/public-api/users")
        fun users(): Single<UsersResponseDTO>

        @POST("/public-api/users")
        fun create(
            @Body dto: SubmitUserDTO
        ): Single<NewUserDTO>

        @DELETE("/public-api/users/{userId}")
        fun delete(
            @Path("userId") userId: String
        ): Single<DeleteUserDto>
    }
}
