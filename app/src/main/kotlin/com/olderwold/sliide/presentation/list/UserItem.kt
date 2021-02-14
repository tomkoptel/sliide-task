package com.olderwold.sliide.presentation.list

import android.content.Context
import com.olderwold.sliide.R
import com.olderwold.sliide.domain.User
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

internal data class UserItem(
    val user: User,
    val toBeDeleted: Boolean = false
) {
    fun relativeTime(context: Context, now: ZonedDateTime = ZonedDateTime.now()): String? {
        val fromDateTime = user.createdAt ?: return null
        val toDateTime = now

        var tempDateTime = ZonedDateTime.from(fromDateTime)

        val years = tempDateTime.until(toDateTime, ChronoUnit.YEARS)
        tempDateTime = tempDateTime.plusYears(years)

        val months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS)
        tempDateTime = tempDateTime.plusMonths(months)

        val days = tempDateTime.until(toDateTime, ChronoUnit.DAYS)
        tempDateTime = tempDateTime.plusDays(days)

        val hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS)
        tempDateTime = tempDateTime.plusHours(hours)

        val minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES)
        tempDateTime = tempDateTime.plusMinutes(minutes)

        val seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS)

        return when {
            years >= 1 -> {
                context.resources.getQuantityString(R.plurals.year, years.toInt(), years)
            }
            months >= 1 -> {
                context.resources.getQuantityString(R.plurals.month, months.toInt(), months)
            }
            hours >= 1 -> {
                context.resources.getQuantityString(R.plurals.hour, hours.toInt(), hours)
            }
            days >= 1 -> {
                context.resources.getQuantityString(R.plurals.day, days.toInt(), days)
            }
            minutes >= 1 -> {
                context.resources.getQuantityString(R.plurals.minute, minutes.toInt(), minutes)
            }
            seconds >= 1 -> {
                context.resources.getQuantityString(R.plurals.second, seconds.toInt(), seconds)
            }
            else -> null
        }
    }
}
