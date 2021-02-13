package com.olderwold.sliide.data

import com.google.gson.annotations.SerializedName

internal data class DeleteUserDto(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("data")
    val data: Any? = null,
    @SerializedName("meta")
    val meta: Any? = null
)
