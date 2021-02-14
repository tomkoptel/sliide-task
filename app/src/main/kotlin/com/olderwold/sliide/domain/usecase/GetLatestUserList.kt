package com.olderwold.sliide.domain.usecase

import com.olderwold.sliide.domain.UserList
import io.reactivex.Single

internal interface GetLatestUserList {
    operator fun invoke(): Single<UserList>
}
