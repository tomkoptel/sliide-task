package com.olderwold.sliide.domain

internal data class Pagination(
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int,
)
