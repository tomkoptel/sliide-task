package com.olderwold.sliide.data

import android.annotation.SuppressLint
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.ZonedDateTime

@SuppressLint("NewApi")
object LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime?> {
    @Suppress("ReturnCount")
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime? {
        if (json == null) return null
        if (json.isJsonNull) return null
        if (!json.isJsonPrimitive) return null
        val rawDate = json.asJsonPrimitive.asString
        return ZonedDateTime.parse(rawDate).toLocalDateTime()
    }
}
