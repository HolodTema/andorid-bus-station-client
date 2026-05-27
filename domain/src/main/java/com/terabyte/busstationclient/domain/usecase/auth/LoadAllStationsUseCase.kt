package com.terabyte.busstationclient.domain.usecase.auth

import com.terabyte.busstationclient.domain.model.shop.Station
import com.terabyte.busstationclient.domain.repository.StationRepository
import javax.inject.Inject


class LoadAllStationsUseCase @Inject constructor(
    private val stationRepository: StationRepository
) {

    suspend operator fun invoke(): Result<List<Station>> {
        return stationRepository.getAllStations()
    }
}