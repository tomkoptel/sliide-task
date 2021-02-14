package com.olderwold.sliide.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers as RxSchedulers

internal interface Schedulers {
    val io: Scheduler
    val ui: Scheduler
    val computation: Scheduler

    companion object : Schedulers {
        override val io: Scheduler = RxSchedulers.io()

        override val ui: Scheduler = AndroidSchedulers.mainThread()

        override val computation: Scheduler = RxSchedulers.computation()
    }
}
