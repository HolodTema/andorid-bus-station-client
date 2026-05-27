package com.terabyte.busstationclient.data.storage.remote.model

import com.terabyte.busstationclient.data.storage.remote.model.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Voyage(
    val id: Int,
    val name: String,
    val startStationId: Int,
    val endStationId: Int,
    @Serializable(with = LocalDateTimeSerializer::class)
    val startTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val endTime: LocalDateTime,
    val cost: Int,
    val driverId: Int,
    val busId: Int
)

@Serializable
data class GetVoyagesByStationsAndDateRequest(
    val startStationId: Int,
    val endStationId: Int,
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDateTime
)
