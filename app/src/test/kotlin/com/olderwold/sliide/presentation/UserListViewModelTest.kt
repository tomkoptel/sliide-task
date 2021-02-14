package com.olderwold.sliide.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.olderwold.sliide.data.GoRestClient
import com.olderwold.sliide.domain.User
import com.olderwold.sliide.domain.UserList
import com.olderwold.sliide.rx.ImmediateSchedulers
import com.olderwold.sliide.rx.RxOperators
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.Single
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test

class UserListViewModelTest {
    private val goRestClient: GoRestClient = mockk()
    private val rxOperators: RxOperators = mockk()
    private val viewModel = UserListViewModel(goRestClient, ImmediateSchedulers(), rxOperators)
    private val mockUser = mockk<User>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `loads data only once`() {
        givenConnectedToNetwork()
        givenApiReturnsResult()

        viewModel.load()
        viewModel.load()

        verify(exactly = 1) { goRestClient.users }
    }

    @Test
    fun `when connected and API returns result`() {
        givenConnectedToNetwork()
        givenApiReturnsResult()

        viewModel.load()

        viewModel.state.value shouldBeEqualTo UserListViewModel.State.Loaded(listOf(mockUser))
    }

    @Test
    fun `when waiting for connection`() {
        every { rxOperators.onConnected() } returns Observable.never()

        viewModel.load()

        viewModel.state.value shouldBeEqualTo UserListViewModel.State.Loading
    }

    @Test
    fun `when API returns an error`() {
        val nullPointerException = NullPointerException("Snap!")
        givenConnectedToNetwork()
        every { goRestClient.users } returns Single.error(nullPointerException)

        viewModel.load()

        viewModel.state.value shouldBeEqualTo UserListViewModel.State.Error(nullPointerException)
    }

    private fun givenApiReturnsResult() {
        every { goRestClient.users } returns Single.just(
            UserList(
                users = listOf(mockUser),
                pagination = mockk()
            )
        )
    }

    private fun givenConnectedToNetwork() {
        every { rxOperators.onConnected() } returns Observable.just(mockk())
    }
}
