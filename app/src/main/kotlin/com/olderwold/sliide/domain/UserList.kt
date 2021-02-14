package com.olderwold.sliide.domain

internal data class UserList(
    val users: List<User> = emptyList(),
    val pagination: Pagination? = null,
)
