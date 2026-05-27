package com.terabyte.busstationclient.domain.model.shop

import java.time.LocalDateTime

data class Voyage(
    val id: Int,
    val name: String,
    val startStationId: Int,
    val endStationId: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val cost: Int,
    val driverId: Int,
    val busId: Int
)

