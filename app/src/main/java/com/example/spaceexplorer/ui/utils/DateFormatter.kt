package com.example.spaceexplorer.ui.utils

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.time.Instant
import kotlin.time.toJavaInstant

fun Instant?.formatAsLocalizedDate(locale: Locale): String {
    this ?: return ""
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(locale)
        .withZone(ZoneId.systemDefault())
    return formatter.format(toJavaInstant())
}