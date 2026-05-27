package com.terabyte.busstationclient.domain.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormatHelper {

    private val formatterWithTime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    private val formatterNoTime = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    fun formatDateWithTime(date: LocalDateTime): String {
        return date.format(formatterWithTime)
    }

    fun formatDateNoTime(date: LocalDateTime): String {
        return date.format(formatterNoTime)
    }
}