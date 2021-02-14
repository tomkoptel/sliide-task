package com.olderwold.sliide.presentation

import com.olderwold.sliide.domain.User

internal data class UserItem(
    val user: User,
    val toBeDeleted: Boolean
)
