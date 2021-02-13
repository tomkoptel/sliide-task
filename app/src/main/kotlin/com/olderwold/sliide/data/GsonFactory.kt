package com.olderwold.sliide.data

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDateTime

@SuppressLint("NewApi")
internal class GsonFactory {
    fun create(): Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer)
        .create()
}
