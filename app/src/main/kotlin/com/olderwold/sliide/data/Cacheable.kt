package com.olderwold.sliide.data

import java.util.concurrent.TimeUnit

/**
 * Marks a request for caching
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Cacheable(
    val until: Int = 0,
    val unit: TimeUnit = TimeUnit.SECONDS
)
