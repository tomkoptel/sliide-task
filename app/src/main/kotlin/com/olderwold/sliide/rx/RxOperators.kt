package com.olderwold.sliide.rx

import android.app.Application
import android.net.NetworkInfo
import android.util.Log
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ConnectivityPredicate
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Single

internal class RxOperators(
    private val application: Application
) {
    fun onConnected(): Single<Connectivity> {
        return ReactiveNetwork
            .observeNetworkConnectivity(application)
            .doOnNext { Log.d("RxOperators", "onConnected($it)") }
            .filter(ConnectivityPredicate.hasState(NetworkInfo.State.CONNECTED))
            .take(1)
            .first(Connectivity.available(false).build())
    }
}
