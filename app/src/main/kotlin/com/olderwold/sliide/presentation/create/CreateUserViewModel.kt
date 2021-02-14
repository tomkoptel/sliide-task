package com.olderwold.sliide.presentation.create

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SingleEvent
import androidx.lifecycle.toEvent
import com.olderwold.sliide.R
import com.olderwold.sliide.domain.usecase.CreateUser
import com.olderwold.sliide.domain.User
import com.olderwold.sliide.rx.RxOperators
import com.olderwold.sliide.rx.RxViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
internal class CreateUserViewModel @Inject constructor(
    private val rxOperators: RxOperators,
    private val createUser: CreateUser,
) : RxViewModel() {
    private val _state = MutableLiveData<SingleEvent<State>?>()

    val state: LiveData<SingleEvent<State>?> = _state

    fun create(name: String?, email: String?) {
        val nameError = if (name.isNullOrBlank()) R.string.name_error_empty else null
        val emailError = if (email.isNullOrBlank()) R.string.email_error_empty else null

        if (nameError != null || emailError != null) {
            _state.value = State.Invalid(emailError = emailError, nameError = nameError).toEvent()
        } else {
            createNewUser(email, name)
        }
    }

    // FIXME a temp trick to reset state
    fun reset() {
        _state.value = null
    }

    private fun createNewUser(email: String?, name: String?) {
        rxOperators.onConnected()
            .take(1)
            .doOnSubscribe { _state.postValue(State.Sending.toEvent()) }
            .flatMapCompletable {
                createUser(
                    User.new {
                        it.email = email
                        it.name = name
                    }
                )
            }
            .subscribeBy(
                onComplete = {
                    _state.postValue(State.Success.toEvent())
                },
                onError = { error ->
                    _state.postValue(State.Error(error).toEvent())
                }
            )
            .addTo(compositeDisposable)
    }

    sealed class State {
        object Sending : State()

        data class Invalid(
            @StringRes
            val emailError: Int?,
            @StringRes
            val nameError: Int?
        ) : State()

        object Success : State()
        data class Error(val error: Throwable) : State()
    }
}
