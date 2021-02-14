package com.olderwold.sliide.domain

import io.reactivex.Single

internal interface GetLatestUserList {
    operator fun invoke(): Single<UserList>
}
