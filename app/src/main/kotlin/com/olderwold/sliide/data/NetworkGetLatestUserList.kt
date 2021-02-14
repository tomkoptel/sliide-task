package com.olderwold.sliide.data

import com.olderwold.sliide.domain.GetLatestUserList
import com.olderwold.sliide.domain.Pagination
import com.olderwold.sliide.domain.UserList
import com.olderwold.sliide.rx.Schedulers
import io.reactivex.Single
import org.jetbrains.annotations.TestOnly

/**
 * Naive implementation that caches the value of pagination to avoid additional hit to the network.
 */
internal class NetworkGetLatestUserList(
    private val goRestClient: GoRestClient,
    private val schedulers: Schedulers
) : GetLatestUserList {
    @set:TestOnly
    var pagination: Pagination? = null

    override fun invoke(): Single<UserList> {
        val pagination = pagination
        return if (pagination == null) {
            goRestClient.users
                .subscribeOn(schedulers.io)
                .doOnSuccess { result ->
                    this.pagination = result.pagination
                }
                .flatMap { result ->
                    val resultPagination = result.pagination
                    // Looking into API specs there is no such case.
                    // We are doing check anyway as it is a good rule of thumb to treat everything as null.
                    if (resultPagination == null) {
                        Single.just(result)
                    } else {
                        getUsersForLastPage(result.pagination)
                    }
                }
        } else {
            getUsersForLastPage(pagination)
        }
    }

    private fun getUsersForLastPage(pagination: Pagination): Single<UserList> {
        return goRestClient.users(page = pagination.pages)
    }
}
