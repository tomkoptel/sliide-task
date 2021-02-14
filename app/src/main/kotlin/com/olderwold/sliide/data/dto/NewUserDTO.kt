package com.olderwold.sliide.data.dto

import com.google.gson.annotations.SerializedName

internal data class NewUserDTO(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("data")
    val data: UserDto? = null,
    @SerializedName("meta")
    val meta: Any? = null
)
