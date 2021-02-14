package com.olderwold.sliide.domain

import java.time.LocalDateTime
import java.util.UUID

internal data class User(
    val id: String,
    val name: String? = null,
    val email: String? = null,
    val status: Status = Status.ACTIVE,
    val gender: Gender = Gender.MALE,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
) {
    companion object {
        fun new(build: (Builder) -> Unit): User {
            return Builder().apply(build).new()
        }
    }

    class Builder {
        var name: String? = null
        var email: String? = null
        var status: Status = Status.ACTIVE
        var gender: Gender = Gender.MALE

        fun new() = User(
            id = UUID.randomUUID().toString(),
            name = name,
            email = email,
            status = status,
            gender = gender
        )
    }

    enum class Status {
        ACTIVE,
        INACTIVE;
    }

    enum class Gender {
        FEMALE,
        MALE;
    }
}
