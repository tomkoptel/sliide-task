package com.olderwold.sliide.data.gson

import com.google.gson.annotations.SerializedName
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import java.time.ZonedDateTime

class GsonFactoryTest {
    private val gson = GsonFactory().create()

    @Test
    fun `test deserialization of raw date`() {
        val dto = gson.fromJson(
            "{\"created_at\":\"2021-02-13T03:50:04.900+05:30\"}",
            Dto::class.java
        )

        dto.createdAt.shouldNotBeNull()
    }

    @Test
    fun `test deserialization of invalid date`() {
        val dto = gson.fromJson(
            "{\"created_at\":\"2021-0230\"}",
            Dto::class.java
        )

        dto.createdAt.shouldBeNull()
    }

    @Test
    fun `test deserialization of raw date with nullable`() {
        val dto = gson.fromJson("{\"created_at\":null}", Dto::class.java)

        dto.createdAt.shouldBeNull()
    }

    @Test
    fun `test deserialization of raw date with non primitive`() {
        val dto = gson.fromJson("{\"created_at\":{}}", Dto::class.java)

        dto.createdAt.shouldBeNull()
    }

    @Test
    fun `test deserialization of raw date with empty json`() {
        val dto = gson.fromJson("{}", Dto::class.java)

        dto.createdAt.shouldBeNull()
    }

    private class Dto(
        @SerializedName("created_at")
        val createdAt: ZonedDateTime?
    )
}
