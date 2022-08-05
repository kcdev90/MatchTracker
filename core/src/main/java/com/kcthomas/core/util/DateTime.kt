package com.kcthomas.core.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun currentDateTimeString(): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy hh:mm aaa", Locale.getDefault())
    val currentTime = Calendar.getInstance().time
    return sdf.format(currentTime)
}

// Minute as a unit time to save computation power
fun String.toEpochMinutes(): Long {
    val month = monthToLong(substring(0, 3))
    val day = substring(4, 6).toLong()
    val year = substring(8, 12).toLong()
    val hour = substring(13, 15).toLong()
    val minute = substring(16, 18).toLong()
    val ampm = substring(19, 21)
    return minute +
            60 * (hour + if (ampm == "AM") 0 else 12) +
            60 * 24 * day +
            60 * 24 * 30 * month +
            60 * 24 * 30 * 12 * (year - 1970)
}

private fun monthToLong(month: String) = when (month) {
    "Jan" -> 1
    "Feb" -> 2
    "Mar" -> 3
    "Apr" -> 4
    "May" -> 5
    "Jun" -> 6
    "Jul" -> 7
    "Aug" -> 8
    "Sep" -> 9
    "Oct" -> 10
    "Nov" -> 11
    "Dec" -> 12
    else -> -1
}
