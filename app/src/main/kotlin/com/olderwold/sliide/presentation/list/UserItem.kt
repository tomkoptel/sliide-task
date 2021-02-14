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
        var tempDateTime = ZonedDateTime.from(fromDateTime)

        val years = tempDateTime.until(now, ChronoUnit.YEARS)
        tempDateTime = tempDateTime.plusYears(years)

        val months = tempDateTime.until(now, ChronoUnit.MONTHS)
        tempDateTime = tempDateTime.plusMonths(months)

        val days = tempDateTime.until(now, ChronoUnit.DAYS)
        tempDateTime = tempDateTime.plusDays(days)

        val hours = tempDateTime.until(now, ChronoUnit.HOURS)
        tempDateTime = tempDateTime.plusHours(hours)

        val minutes = tempDateTime.until(now, ChronoUnit.MINUTES)
        tempDateTime = tempDateTime.plusMinutes(minutes)

        val seconds = tempDateTime.until(now, ChronoUnit.SECONDS)

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
