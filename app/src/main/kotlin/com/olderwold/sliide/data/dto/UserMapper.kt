package com.olderwold.sliide.data.dto

import com.olderwold.sliide.data.mapper.UserGenderMapper
import com.olderwold.sliide.data.mapper.UserStatusMapper
import com.olderwold.sliide.domain.User

internal class UserMapper(
    private val userGenderMapper: UserGenderMapper,
    private val userStatusMapper: UserStatusMapper,
) {
    @Suppress("ReturnCount")
    fun map(dto: UserDto?): User? {
        val data = dto ?: return null
        val id = data.id ?: return null
        val gender = userGenderMapper.map(data.gender)
        val status = userStatusMapper.map(data.status)
        return User(
            id = id,
            email = data.email,
            name = data.name,
            gender = gender,
            status = status,
            createdAt = data.createdAt,
            updatedAt = data.updatedAt,
        )
    }
}
