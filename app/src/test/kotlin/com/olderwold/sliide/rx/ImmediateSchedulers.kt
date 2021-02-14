package com.olderwold.sliide.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers as RxSchedulers

class ImmediateSchedulers(
    override val io: Scheduler = RxSchedulers.trampoline(),
    override val ui: Scheduler = RxSchedulers.trampoline(),
    override val computation: Scheduler = RxSchedulers.trampoline(),
) : Schedulers
