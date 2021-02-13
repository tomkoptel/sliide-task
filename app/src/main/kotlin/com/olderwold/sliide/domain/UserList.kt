package com.olderwold.sliide.domain

internal data class UserList(
    val users: List<User>,
    val pagination: Pagination?,
)
