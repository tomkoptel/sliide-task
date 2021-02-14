package com.olderwold.sliide.presentation.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.olderwold.sliide.domain.User
import com.olderwold.sliide.domain.UserList
import com.olderwold.sliide.domain.usecase.DeleteUser
import com.olderwold.sliide.domain.usecase.GetLatestUserList
import com.olderwold.sliide.rx.RxOperators
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test

class UserListViewModelTest {
    private val getLatestUserList = mockk<GetLatestUserList>()
    private val rxOperators = mockk<RxOperators>()
    private val deleteUser = mockk<DeleteUser>()
    private val mockUser = mockk<User>()
    private val userItem = UserItem(mockUser, toBeDeleted = false)

    private val viewModel = UserListViewModel(getLatestUserList, deleteUser, rxOperators)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `if delete fails should proceed with updating UI`() {
        givenConnectedToNetwork()
        givenApiReturnsListOfUsers()
        givenViewModelInLoadedState()
        every { deleteUser(eq(mockUser)) } returns Completable.error(NullPointerException("Aw"))

        viewModel.delete(userItem)

        verify { getLatestUserList() }
    }

    @Test
    fun `delete should mark item as deleted`() {
        givenConnectedToNetwork()
        givenApiReturnsListOfUsers()
        givenViewModelInLoadedState()
        every { deleteUser(eq(mockUser)) } returns Completable.complete()

        viewModel.delete(userItem)

        verify { getLatestUserList() }
    }

    @Test
    fun `loads data only once`() {
        givenConnectedToNetwork()
        givenApiReturnsListOfUsers()

        viewModel.load()
        viewModel.load()

        verify(exactly = 1) { getLatestUserList() }
    }

    @Test
    fun `when connected and API returns result`() {
        givenConnectedToNetwork()
        givenApiReturnsListOfUsers()

        viewModel.load()

        viewModel.state.value shouldBeEqualTo UserListViewModel.State.Loaded(listOf(userItem))
    }

    private fun givenViewModelInLoadedState() {
        viewModel.mutableState.value = UserListViewModel.State.Loaded(listOf(userItem))
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
        every { getLatestUserList() } returns Single.error(nullPointerException)

        viewModel.load()

        viewModel.state.value shouldBeEqualTo UserListViewModel.State.Error(nullPointerException)
    }

    private fun givenApiReturnsListOfUsers() {
        every { getLatestUserList() } returns Single.just(
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
