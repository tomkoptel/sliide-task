package com.olderwold.sliide.data

import com.olderwold.sliide.domain.User
import okreplay.OkReplay
import okreplay.OkReplayConfig
import okreplay.OkReplayInterceptor
import okreplay.RecorderRule
import okreplay.TapeMode
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
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
        api.users.blockingGet().shouldNotBeEmpty()
    }

    @Test
    @OkReplay
    fun crud() {
        val newUser = User.new {
            name = "Sliide"
            email = "sliide.sliide@gmail.com"
            gender = User.Gender.MALE
            status = User.Status.ACTIVE
        }.let { api.create(it).blockingGet() }
        newUser.shouldNotBeNull()

        api.delete(newUser.id).blockingGet()
    }
}
