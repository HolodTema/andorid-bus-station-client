package com.terabyte.busstationclient.data.repository

import com.terabyte.busstationclient.data.storage.remote.NetworkStorage
import com.terabyte.busstationclient.domain.model.shop.Station
import com.terabyte.busstationclient.domain.repository.StationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StationRepositoryImpl @Inject constructor(
    private val networkStorage: NetworkStorage
) : StationRepository {

    override suspend fun getAllStations(): Result<List<Station>> {
        return networkStorage.getAllStations().map {
            it.map { station ->
                mapToStationModel(station)
            }
        }
    }

    private fun mapToStationModel(station: com.terabyte.busstationclient.data.storage.remote.model.Station): Station {
        return Station(
            id = station.id,
            name = station.name,
            latitude = station.latitude,
            longitude = station.longitude,
            hasBuilding = station.hasBuilding,
            hasRestroom = station.hasRestroom,
            hasCaffe = station.hasCaffe,
            hasTown = station.hasTown
        )
    }
}