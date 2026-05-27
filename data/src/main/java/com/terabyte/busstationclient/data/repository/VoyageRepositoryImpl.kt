package com.terabyte.busstationclient.data.repository

import com.terabyte.busstationclient.data.storage.remote.NetworkStorage
import com.terabyte.busstationclient.data.storage.remote.model.GetVoyagesByStationsAndDateRequest
import com.terabyte.busstationclient.data.storage.remote.model.Voyage
import com.terabyte.busstationclient.domain.repository.VoyageRepository
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoyageRepositoryImpl @Inject constructor(
    private val networkStorage: NetworkStorage
): VoyageRepository {

    override suspend fun getVoyagesByStationsAndDate(
        startStationId: Int,
        endStationId: Int,
        date: LocalDateTime
    ): Result<List<com.terabyte.busstationclient.domain.model.shop.Voyage>> {
        val request = GetVoyagesByStationsAndDateRequest(startStationId, endStationId, date)
        return networkStorage.getVoyagesByStationsAndDate(request).map {
            it.map { voyage ->
                mapToVoyageModel(voyage)
            }
        }
    }

    private fun mapToVoyageModel(voyage: Voyage): com.terabyte.busstationclient.domain.model.shop.Voyage {
        return com.terabyte.busstationclient.domain.model.shop.Voyage(
            id = voyage.id,
            name = voyage.name,
            startStationId = voyage.startStationId,
            endStationId = voyage.endStationId,
            startTime = voyage.startTime,
            endTime = voyage.endTime,
            cost = voyage.cost,
            driverId = voyage.driverId,
            busId = voyage.busId
        )
    }
}