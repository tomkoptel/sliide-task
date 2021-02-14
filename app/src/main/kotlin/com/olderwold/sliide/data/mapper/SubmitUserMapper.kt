package com.olderwold.sliide.data.mapper

import com.olderwold.sliide.data.dto.SubmitUserDTO
import com.olderwold.sliide.data.mapper.GenderStatusMapper
import com.olderwold.sliide.data.mapper.UserStatusMapper
import com.olderwold.sliide.domain.User

internal class SubmitUserMapper(
    private val genderStatusMapper: GenderStatusMapper,
    private val userStatusMapper: UserStatusMapper,
) {
    fun map(user: User): SubmitUserDTO {
        val (_, name, email, status, gender) = user
        return SubmitUserDTO(
            name = name,
            email = email,
            gender = genderStatusMapper.map(gender),
            status = userStatusMapper.map(status),
        )
    }
}
