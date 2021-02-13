package com.olderwold.sliide.data

import com.google.gson.annotations.SerializedName

data class UsersResponseDTO(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("data")
    val data: List<Data?> = emptyList(),
    @SerializedName("meta")
    val meta: Meta? = null
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
