package com.olderwold.sliide.rx

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

internal open class RxViewModel: ViewModel() {
    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
