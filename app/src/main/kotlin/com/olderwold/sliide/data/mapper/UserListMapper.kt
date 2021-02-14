package com.olderwold.sliide.data.mapper

import com.olderwold.sliide.data.dto.UserListDTO
import com.olderwold.sliide.data.dto.UserMapper
import com.olderwold.sliide.domain.UserList

internal class UserListMapper(
    private val paginationMapper: PaginationMapper,
    private val userDtoMapper: UserMapper,
) {
    fun map(dto: UserListDTO): UserList {
        val users = dto.data.filterNotNull()
            .mapNotNull(userDtoMapper::map)
        val pagination = paginationMapper.map(dto.meta)

        return UserList(
            users = users,
            pagination = pagination
        )
    }
}
