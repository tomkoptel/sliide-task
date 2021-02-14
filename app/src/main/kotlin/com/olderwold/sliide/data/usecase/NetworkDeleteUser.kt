package com.olderwold.sliide.data.usecase

import com.olderwold.sliide.data.GoRestClient
import com.olderwold.sliide.domain.User
import com.olderwold.sliide.domain.usecase.DeleteUser
import com.olderwold.sliide.rx.Schedulers
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Completable
import javax.inject.Inject

internal class NetworkDeleteUser @Inject constructor(
    private val goRestClient: GoRestClient,
    private val schedulers: Schedulers
) : DeleteUser {
    override fun invoke(user: User): Completable {
        return goRestClient.delete(userId = user.id)
            .subscribeOn(schedulers.io)
    }

    @InstallIn(SingletonComponent::class)
    @dagger.Module
    interface Module {
        @get:Binds
        val NetworkDeleteUser.bind: DeleteUser
    }
}
