package com.olderwold.sliide.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.olderwold.sliide.domain.usecase.DeleteUser
import com.olderwold.sliide.domain.usecase.GetLatestUserList
import com.olderwold.sliide.rx.RxOperators
import com.olderwold.sliide.rx.RxViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

@HiltViewModel
internal class UserListViewModel @Inject constructor(
    private val getLatestUserList: GetLatestUserList,
    private val deleteUser: DeleteUser,
    private val rxOperators: RxOperators,
) : RxViewModel() {
    @get:TestOnly
    val mutableState = MutableLiveData<State>()

    val state: LiveData<State> = mutableState

    fun load() {
        val value = state.value
        if (value is State.Loaded) return

        mutableState.value = State.Loading
        loadData()
    }

    fun delete(item: UserItem) {
        val value = mutableState.value
        if (value is State.Loaded) {
            deleteUser(item.user)
                .onErrorComplete()
                .doOnSubscribe { markItemAsToBeDeleted(value, item) }
                .andThen(getLatestUsers())
                .subscribeBy(
                    onSuccess = { userList ->
                        mutableState.postValue(State.Loaded(userList))
                    },
                    onError = { error ->
                        mutableState.postValue(State.Error(error))
                    }
                )
                .addTo(compositeDisposable)
        }
    }

    private fun markItemAsToBeDeleted(
        value: State.Loaded,
        user: UserItem
    ) {
        val modifiedUsers = value.users.toMutableList()
        val position = modifiedUsers.indexOf(user)
        modifiedUsers.removeAt(position)
        modifiedUsers.add(position, user.copy(toBeDeleted = true))

        mutableState.postValue(State.Loaded(modifiedUsers))
    }

    fun reload() {
        mutableState.value = State.Loading
        loadData()
    }

    private fun loadData() {
        getLatestUsers()
            .subscribeBy(
                onSuccess = { userList ->
                    mutableState.postValue(State.Loaded(userList))
                },
                onError = { error ->
                    mutableState.postValue(State.Error(error))
                }
            )
            .addTo(compositeDisposable)
    }

    private fun getLatestUsers(): Single<List<UserItem>> = getLatestUserList().map { result ->
        result.users.map { UserItem(it, toBeDeleted = false) }
    }

    sealed class State {
        object Loading : State()
        data class Loaded(val users: List<UserItem>) : State()
        data class Error(val error: Throwable) : State()
    }
}
