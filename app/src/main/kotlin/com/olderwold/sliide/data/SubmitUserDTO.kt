package com.olderwold.sliide.data

import com.google.gson.annotations.SerializedName
import com.olderwold.sliide.domain.User

internal data class SubmitUserDTO(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("status")
    val status: String? = null
) {
    class Mapper(
        private val genderStatusMapper: GenderStatusMapper,
        private val userStatusMapper: UserStatusMapper,
    ) {
        fun map(user: User): SubmitUserDTO {
            val (_, name, email, status, gender) = user
            return SubmitUserDTO(
                name = name,
                email = email,
                gender = genderStatusMapper.map(gender),
                status = userStatusMapper.map(status),
            )
        }
    }
}
