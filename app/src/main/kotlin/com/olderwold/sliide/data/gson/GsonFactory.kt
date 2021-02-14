package com.olderwold.sliide.data.gson

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDateTime
import java.time.ZonedDateTime

@SuppressLint("NewApi")
internal class GsonFactory {
    fun create(): Gson = GsonBuilder()
        .registerTypeAdapter(ZonedDateTime::class.java, DateTimeDeserializer)
        .create()
}
