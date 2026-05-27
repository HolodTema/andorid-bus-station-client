package com.terabyte.busstationclient.domain.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormatHelper {

    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

    fun formatDate(date: LocalDateTime): String {
        return date.format(formatter)
    }
}