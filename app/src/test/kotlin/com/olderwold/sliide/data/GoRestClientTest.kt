package com.olderwold.sliide.data

import okreplay.OkReplay
import okreplay.OkReplayConfig
import okreplay.OkReplayInterceptor
import okreplay.RecorderRule
import okreplay.TapeMode
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GoRestClientTest {
    private val okReplayInterceptor = OkReplayInterceptor()
    private val configuration = OkReplayConfig.Builder()
        .defaultMode(TapeMode.READ_ONLY)
        .sslEnabled(true)
        .interceptor(okReplayInterceptor)
        .build()
    private val api = GoRestClient { addInterceptor(okReplayInterceptor) }

    @get:Rule
    val testRule: TestRule = RecorderRule(configuration)

    @Test
    @OkReplay
    fun get_list_of_users() {
        api.users.blockingGet()
    }
}
