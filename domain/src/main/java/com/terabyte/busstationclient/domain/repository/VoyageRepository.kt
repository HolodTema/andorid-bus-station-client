package com.terabyte.busstationclient.domain.repository

import com.terabyte.busstationclient.domain.model.shop.Voyage
import java.time.LocalDateTime

interface VoyageRepository {

    suspend fun getVoyagesByStationsAndDate(
        startStationId: Int,
        endStationId: Int,
        date: LocalDateTime
    ): Result<List<Voyage>>

}