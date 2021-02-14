package com.olderwold.sliide.presentation.list

import android.content.Context
import android.content.res.Resources
import com.olderwold.sliide.R
import com.olderwold.sliide.domain.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import java.time.ZonedDateTime

class UserItemTest {
    private val mockResources = mockk<Resources>(relaxed = true)
    private val context = mockk<Context>() {
        every { resources } returns mockResources
    }

    @Test
    fun `should map to years`() {
        val past = ZonedDateTime.now().minusYears(1)

        userItem(past).relativeTime(context)

        verify { mockResources.getQuantityString(R.plurals.year, 1, 1L) }
    }

    @Test
    fun `should map to months`() {
        val past = ZonedDateTime.now().minusMonths(11)

        userItem(past).relativeTime(context)

        verify { mockResources.getQuantityString(R.plurals.month, 11, 11L) }
    }

    @Test
    fun `should map to days`() {
        val past = ZonedDateTime.now().minusDays(12)

        userItem(past).relativeTime(context)

        verify { mockResources.getQuantityString(R.plurals.day, 12, 12L) }
    }

    @Test
    fun `should map to minutes`() {
        val past = ZonedDateTime.now().minusMinutes(13)

        userItem(past).relativeTime(context)

        verify { mockResources.getQuantityString(R.plurals.minute, 13, 13L) }
    }

    @Test
    fun `should map to seconds`() {
        val past = ZonedDateTime.now().minusSeconds(14)

        userItem(past).relativeTime(context)

        verify { mockResources.getQuantityString(R.plurals.second, 14, 14L) }
    }

    private fun userItem(createdAt: ZonedDateTime): UserItem {
        return UserItem(user = User(id = "??", createdAt = createdAt))
    }
}
