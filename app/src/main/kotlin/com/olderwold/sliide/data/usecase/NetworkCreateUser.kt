package com.olderwold.sliide.data.usecase

import com.olderwold.sliide.data.GoRestClient
import com.olderwold.sliide.domain.usecase.CreateUser
import com.olderwold.sliide.domain.User
import com.olderwold.sliide.rx.Schedulers
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Completable
import javax.inject.Inject

internal class NetworkCreateUser @Inject constructor(
    private val goRestClient: GoRestClient,
    private val schedulers: Schedulers,
) : CreateUser {
    override fun invoke(user: User): Completable {
        return goRestClient.create(user).ignoreElement()
            .subscribeOn(schedulers.io)
    }

    @InstallIn(SingletonComponent::class)
    @dagger.Module
    interface Module {
        @get:Binds
        val NetworkCreateUser.bind: CreateUser
    }
}
