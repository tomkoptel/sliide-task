package com.olderwold.sliide.data

import com.google.gson.annotations.SerializedName

internal data class UserListDTO(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("data")
    val data: List<UserDto?> = emptyList(),
    @SerializedName("meta")
    val meta: Meta? = null
) {
    data class Meta(
        @SerializedName("pagination")
        val pagination: Pagination? = null
    ) {
        data class Pagination(
            @SerializedName("limit")
            val limit: Int? = null,
            @SerializedName("page")
            val page: Int? = null,
            @SerializedName("pages")
            val pages: Int? = null,
            @SerializedName("total")
            val total: Int? = null
        )
    }
}
