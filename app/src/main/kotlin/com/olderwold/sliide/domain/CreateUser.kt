package com.olderwold.sliide.domain

import io.reactivex.Completable

internal interface CreateUser {
    operator fun invoke(user: User): Completable
}
