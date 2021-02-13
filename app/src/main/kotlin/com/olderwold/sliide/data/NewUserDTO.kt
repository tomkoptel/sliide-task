package com.olderwold.sliide.data

import com.google.gson.annotations.SerializedName
import com.olderwold.sliide.domain.User

internal data class NewUserDTO(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("data")
    val data: Data? = null,
    @SerializedName("meta")
    val meta: Any? = null
) {
    data class Data(
        @SerializedName("created_at")
        val createdAt: String? = null,
        @SerializedName("email")
        val email: String? = null,
        @SerializedName("gender")
        val gender: String? = null,
        @SerializedName("id")
        val id: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("status")
        val status: String? = null,
        @SerializedName("updated_at")
        val updatedAt: String? = null
    )

    class Mapper(
        private val genderStatusMapper: GenderStatusMapper,
        private val userStatusMapper: UserStatusMapper,
    ) {
        @Suppress("ReturnCount")
        fun map(dto: NewUserDTO): User? {
            val data = dto.data ?: return null
            val id = data.id ?: return null
            val gender = genderStatusMapper.map(data.gender)
            val status = userStatusMapper.map(data.status)
            return User(
                id = id,
                email = data.email,
                gender = gender,
                status = status
            )
        }
    }
}
