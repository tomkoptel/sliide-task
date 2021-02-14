package com.olderwold.sliide.rx

import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import io.reactivex.schedulers.Schedulers as RxSchedulers

internal interface Schedulers {
    val io: Scheduler
    val ui: Scheduler
    val computation: Scheduler

    class Impl @Inject constructor() : Schedulers {
        override val io: Scheduler = RxSchedulers.io()

        override val ui: Scheduler = AndroidSchedulers.mainThread()

        override val computation: Scheduler = RxSchedulers.computation()
    }

    @InstallIn(SingletonComponent::class)
    @dagger.Module
    interface Module {
        @get:Binds
        val Impl.bind: Schedulers
    }
}
