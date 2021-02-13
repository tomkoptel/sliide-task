package com.olderwold.sliide

import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.ZonedDateTime

class Java8TimeSupportTest {
    @Test
    fun test_out_the_java_8_time_compatibility() {
        // Check compatibility with the desugared API on Android 21
        assertNotNull(ZonedDateTime.parse("2021-02-13T03:50:04.900+05:30").toLocalDateTime())
    }
}
