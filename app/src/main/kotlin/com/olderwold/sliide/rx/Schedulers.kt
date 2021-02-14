package com.olderwold.sliide.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers as RxSchedulers

internal interface Schedulers {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler

    companion object : Schedulers {
        override fun io(): Scheduler = RxSchedulers.io()

        override fun ui(): Scheduler = AndroidSchedulers.mainThread()

        override fun computation(): Scheduler = RxSchedulers.computation()
    }
}
