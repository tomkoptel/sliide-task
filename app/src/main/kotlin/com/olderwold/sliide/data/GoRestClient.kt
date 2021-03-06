package com.olderwold.sliide.data

import android.annotation.SuppressLint
import com.olderwold.sliide.data.dto.DeleteUserDto
import com.olderwold.sliide.data.dto.NewUserDTO
import com.olderwold.sliide.data.dto.SubmitUserDTO
import com.olderwold.sliide.data.dto.UserListDTO
import com.olderwold.sliide.data.dto.UserMapper
import com.olderwold.sliide.data.gson.GsonFactory
import com.olderwold.sliide.data.mapper.PaginationMapper
import com.olderwold.sliide.data.mapper.SubmitUserMapper
import com.olderwold.sliide.data.mapper.UserGenderMapper
import com.olderwold.sliide.data.mapper.UserListMapper
import com.olderwold.sliide.data.mapper.UserStatusMapper
import com.olderwold.sliide.domain.User
import com.olderwold.sliide.domain.UserList
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface GoRestClient {
    val users: Single<UserList>

    fun users(page: Int): Single<UserList>

    fun create(user: User): Single<User>

    fun delete(userId: String): Completable

    companion object {
        @SuppressLint("NewApi")
        operator fun invoke(
            retrofitBuilder: Retrofit.Builder.() -> Unit = {},
            clientBuilder: OkHttpClient.Builder.() -> Unit = {}
        ): GoRestClient {
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
                .apply(retrofitBuilder)
                .build()
                .create(Api::class.java)

            val genderStatusMapper = UserGenderMapper()
            val userStatusMapper = UserStatusMapper()
            val userDtoMapper = UserMapper(genderStatusMapper, userStatusMapper)
            return Impl(
                api = api,
                userListMapper = UserListMapper(PaginationMapper(), userDtoMapper),
                submitMapper = SubmitUserMapper(genderStatusMapper, userStatusMapper),
                userDTOMapper = userDtoMapper,
            )
        }
    }

    private class Impl(
        private val api: Api,
        private val userListMapper: UserListMapper,
        private val submitMapper: SubmitUserMapper,
        private val userDTOMapper: UserMapper,
    ) : GoRestClient {
        override fun users(page: Int): Single<UserList> {
            return api.users(page).map(userListMapper::map)
        }

        override val users: Single<UserList>
            get() {
                return api.users().map(userListMapper::map)
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
        fun users(@Query("page") page: Int): Single<UserListDTO>

        @GET("/public-api/users")
        fun users(): Single<UserListDTO>

        @POST("/public-api/users")
        fun create(
            @Body dto: SubmitUserDTO
        ): Single<NewUserDTO>

        @DELETE("/public-api/users/{userId}")
        fun delete(
            @Path("userId") userId: String
        ): Single<DeleteUserDto>
    }

    @InstallIn(SingletonComponent::class)
    @dagger.Module
    class Module {
        @Provides
        fun client(): GoRestClient {
            return GoRestClient {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
        }
    }
}
