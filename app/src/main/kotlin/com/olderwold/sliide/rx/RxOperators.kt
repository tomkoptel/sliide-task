@file:Suppress("DEPRECATION")

package com.olderwold.sliide.rx

import android.app.Application
import android.net.NetworkInfo
import android.util.Log
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ConnectivityPredicate
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable
import javax.inject.Inject

internal class RxOperators @Inject constructor(
    private val application: Application,
    private val schedulers: Schedulers
) {
    // TODO hide Connectivity API from the consumer
    fun onConnected(): Observable<Connectivity> {
        return ReactiveNetwork
            .observeNetworkConnectivity(application)
            .subscribeOn(schedulers.io)
            .doOnNext { Log.d("RxOperators", "onConnected($it)") }
            .filter(ConnectivityPredicate.hasState(NetworkInfo.State.CONNECTED))
    }
}
