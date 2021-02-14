package com.olderwold.sliide.data.mapper

import com.olderwold.sliide.data.dto.UserListDTO
import com.olderwold.sliide.domain.Pagination

internal class PaginationMapper {
    @Suppress("ComplexCondition", "ReturnCount")
    fun map(dto: UserListDTO.Meta?): Pagination? {
        if (dto == null) return null
        val pagination = dto.pagination ?: return null
        val (limit, page, pages, total) = pagination

        return if (limit != null && page != null && pages != null && total != null) {
            Pagination(
                limit = limit,
                page = page,
                pages = pages,
                total = total,
            )
        } else {
            null
        }
    }
}
