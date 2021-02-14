package com.olderwold.sliide.domain

import io.reactivex.Completable

internal interface DeleteUser {
    operator fun invoke(user: User): Completable
}
