package com.olderwold.sliide.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.olderwold.sliide.domain.GetLatestUserList
import com.olderwold.sliide.domain.User
import com.olderwold.sliide.rx.RxOperators
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
internal class UserListViewModel @Inject constructor(
    private val getLatestUserList: GetLatestUserList,
    private val rxOperators: RxOperators,
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _state = MutableLiveData<State>()

    val state: LiveData<State> = _state

    fun load() {
        val value = state.value
        if (value is State.Loaded) return

        loadData()
    }

    private fun loadData() {
        rxOperators.onConnected()
            .take(1)
            .doOnSubscribe { _state.postValue(State.Loading) }
            .flatMapSingle { getLatestUserList() }
            .singleOrError()
            .subscribeBy(
                onSuccess = { userList ->
                    _state.postValue(State.Loaded(userList.users))
                },
                onError = { error ->
                    _state.postValue(State.Error(error))
                }
            )
            .addTo(compositeDisposable)
    }

    sealed class State {
        object Loading : State()
        data class Loaded(val users: List<User>) : State()
        data class Error(val error: Throwable) : State()
    }
}
