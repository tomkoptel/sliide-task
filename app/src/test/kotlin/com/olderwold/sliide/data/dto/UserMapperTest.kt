package com.olderwold.sliide.data.dto

import com.olderwold.sliide.data.mapper.UserGenderMapper
import com.olderwold.sliide.data.mapper.UserStatusMapper
import com.olderwold.sliide.domain.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import java.time.ZonedDateTime

class UserMapperTest {
    private val userGenderMapper = mockk<UserGenderMapper>() {
        every { map(any<String>()) } returns User.Gender.MALE
    }
    private val userStatusMapper = mockk<UserStatusMapper>() {
        every { map(any<String>()) } returns User.Status.ACTIVE
    }
    private val mapper = UserMapper(userGenderMapper, userStatusMapper)
    private val dto = UserDto(id = "?", gender = "gender", status = "status")
    private val user = User(id = "?")

    @Test
    fun `should map name field`() {
        val userDto = dto.copy(name = "Gandalf")
        mapper.map(userDto) shouldBeEqualTo user.copy(name = "Gandalf")
    }

    @Test
    fun `should map email field`() {
        val userDto = dto.copy(email = "gandalf@mail.com")
        mapper.map(userDto) shouldBeEqualTo user.copy(email = "gandalf@mail.com")
    }

    @Test
    fun `should map email createdAt`() {
        val createdAt = ZonedDateTime.now()
        val userDto = dto.copy(createdAt = createdAt)
        mapper.map(userDto) shouldBeEqualTo user.copy(createdAt = createdAt)
    }

    @Test
    fun `should map email updatedAt`() {
        val updatedAt = ZonedDateTime.now()
        val userDto = dto.copy(updatedAt = updatedAt)
        mapper.map(userDto) shouldBeEqualTo user.copy(updatedAt = updatedAt)
    }

    @Test
    fun `should map gender field`() {
        mapper.map(dto)
        verify { userGenderMapper.map("gender") }
    }

    @Test
    fun `should map status field`() {
        mapper.map(dto)
        verify { userStatusMapper.map("status") }
    }
}
