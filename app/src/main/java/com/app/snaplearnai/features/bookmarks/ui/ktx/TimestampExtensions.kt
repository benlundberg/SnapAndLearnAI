package com.app.snaplearnai.features.bookmarks.ui.ktx

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.convertTimestampToDateTimeString(): String {
    // 1. Convert the timestamp to an Instant
    val instant = Instant.ofEpochMilli(this)

    // 2. Convert the Instant to LocalDateTime using the system's default time zone
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    // 3. Define formatters for time and date
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    // 4. Format the LocalDateTime object
    return localDateTime.format(dateFormatter)
}
