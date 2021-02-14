package com.olderwold.sliide.data

import com.olderwold.sliide.domain.Pagination
import com.olderwold.sliide.domain.UserList
import com.olderwold.sliide.rx.ImmediateSchedulers
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class NetworkGetLatestUserListTest {
    private companion object {
        const val TOTAL_PAGES = 50
    }

    private val goRestClient = mockk<GoRestClient>()
    private val getUserList = NetworkGetLatestUserList(goRestClient, ImmediateSchedulers())
    private val pagination = Pagination(pages = TOTAL_PAGES, limit = 20, page = 1, total = 1000)
    private val latestUsers = mockk<UserList>()

    @Test
    fun `if pagination not cached performs 2 calls`() {
        every { goRestClient.users } returns Single.just(UserList(pagination = pagination))
        every { goRestClient.users(eq(TOTAL_PAGES)) } returns Single.just(latestUsers)

        getUserList.pagination = null
        getUserList().test().assertValue {
            it shouldBeEqualTo latestUsers
            true
        }
        getUserList.pagination shouldBeEqualTo pagination
    }

    @Test
    fun `if pagination cached performs 1 call`() {
        every { goRestClient.users(eq(TOTAL_PAGES)) } returns Single.just(latestUsers)

        getUserList.pagination = pagination
        getUserList().test().assertValue {
            it shouldBeEqualTo latestUsers
            true
        }

        verify { goRestClient.users wasNot Called }
    }
}
