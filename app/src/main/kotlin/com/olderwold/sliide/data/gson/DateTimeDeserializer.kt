package com.olderwold.sliide.data.gson

import android.annotation.SuppressLint
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException

@SuppressLint("NewApi")
object DateTimeDeserializer : JsonDeserializer<ZonedDateTime?> {
    @Suppress("ReturnCount")
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ZonedDateTime? {
        if (json == null) return null
        if (json.isJsonNull) return null
        if (!json.isJsonPrimitive) return null
        val rawDate = json.asJsonPrimitive.asString
        return try {
            ZonedDateTime.parse(rawDate)
        } catch (ex: DateTimeParseException) {
            null
        }
    }
}
