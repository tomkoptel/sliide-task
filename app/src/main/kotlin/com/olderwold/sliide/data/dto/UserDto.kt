package com.olderwold.sliide.data.dto

import com.google.gson.annotations.SerializedName
import com.olderwold.sliide.data.mapper.GenderStatusMapper
import com.olderwold.sliide.data.mapper.UserStatusMapper
import com.olderwold.sliide.domain.User
import java.time.LocalDateTime

internal data class UserDto(
    @SerializedName("created_at")
    val createdAt: LocalDateTime? = null,
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
    val updatedAt: LocalDateTime? = null
)
