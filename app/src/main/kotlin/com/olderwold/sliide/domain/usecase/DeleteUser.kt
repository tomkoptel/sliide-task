package com.olderwold.sliide.domain.usecase

import com.olderwold.sliide.domain.User
import io.reactivex.Completable

internal interface DeleteUser {
    operator fun invoke(user: User): Completable
}
