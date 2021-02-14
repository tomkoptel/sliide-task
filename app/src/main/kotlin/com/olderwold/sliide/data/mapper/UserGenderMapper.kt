package com.olderwold.sliide.data.mapper

import com.olderwold.sliide.domain.User
import java.util.Locale

internal class UserGenderMapper(
    private val expectedLocale: Locale = Locale.ENGLISH
) {
    fun map(status: User.Gender): String {
        return status.toString()
            .toLowerCase(expectedLocale)
            .capitalize(expectedLocale)
    }

    fun map(gender: String?): User.Gender {
        return if (gender == null) {
            User.Gender.MALE
        } else {
            try {
                User.Gender.valueOf(gender.toUpperCase(expectedLocale))
            } catch (ex: IllegalArgumentException) {
                User.Gender.MALE
            }
        }
    }
}
