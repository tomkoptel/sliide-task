package com.olderwold.sliide.domain.usecase

import com.olderwold.sliide.domain.User
import io.reactivex.Completable

internal interface CreateUser {
    operator fun invoke(user: User): Completable
}
