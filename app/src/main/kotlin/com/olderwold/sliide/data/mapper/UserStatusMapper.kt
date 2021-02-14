package com.olderwold.sliide.data.mapper

import com.olderwold.sliide.domain.User
import java.util.Locale

internal class UserStatusMapper(
    private val expectedLocale: Locale = Locale.ENGLISH
) {
    fun map(status: User.Status): String {
        return status.toString()
            .toLowerCase(expectedLocale)
            .capitalize(expectedLocale)
    }

    fun map(status: String?): User.Status {
        return if (status == null) {
            User.Status.INACTIVE
        } else {
            try {
                User.Status.valueOf(status.toUpperCase(expectedLocale))
            } catch (ex: IllegalArgumentException) {
                User.Status.INACTIVE
            }
        }
    }
}
