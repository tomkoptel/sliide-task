package com.olderwold.sliide.data

import com.olderwold.sliide.domain.User

internal class UserMapper {
    fun map(data: List<UsersResponseDTO.Data?>): List<User> {
        return data.filterNotNull()
            .mapNotNull { userDto ->
                val id = userDto.id
                if (id == null) {
                    null
                } else {
                    User(id = id)
                }
            }
    }
}
