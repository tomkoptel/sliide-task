package com.olderwold.sliide.data.dto

import com.google.gson.annotations.SerializedName
import com.olderwold.sliide.data.mapper.GenderStatusMapper
import com.olderwold.sliide.data.mapper.UserStatusMapper
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
)
