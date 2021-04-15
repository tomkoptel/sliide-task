@file:Suppress("DEPRECATION")

package com.olderwold.sliide.data

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test
import retrofit2.adapter.rxjava2.HttpException

class GoRestClientMockTest {
    @get:Rule
    val server = MockWebServer()

    @Test
    fun `retrofit call adapter should rethrow 404 as HttpException`() {
        server.enqueue(MockResponse().setResponseCode(404))

        val api = GoRestClient(retrofitBuilder = {
            baseUrl(server.url("."))
        })

        api.users(page = 10).test()
            .assertError { throwable ->
                with(throwable as HttpException) {
                    code() shouldBeEqualTo 404
                    message() shouldBeEqualTo "Client Error"
                }
                true
            }
    }
}
