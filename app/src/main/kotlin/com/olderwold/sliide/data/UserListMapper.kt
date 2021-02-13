package com.olderwold.sliide.data

import com.olderwold.sliide.domain.UserList

internal class UserListMapper(
    private val paginationMapper: PaginationMapper,
    private val userDtoMapper: UserDto.Mapper,
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
