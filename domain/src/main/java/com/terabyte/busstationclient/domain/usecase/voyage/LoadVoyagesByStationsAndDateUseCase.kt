package com.terabyte.busstationclient.domain.usecase.voyage

import com.terabyte.busstationclient.domain.model.shop.Voyage
import com.terabyte.busstationclient.domain.repository.VoyageRepository
import java.time.LocalDateTime
import javax.inject.Inject


class LoadVoyagesByStationsAndDateUseCase @Inject constructor(
    private val voyageRepository: VoyageRepository
) {

    suspend operator fun invoke(
        startStationId: Int,
        endStationId: Int,
        date: LocalDateTime
    ): Result<List<Voyage>> {
        return voyageRepository.getVoyagesByStationsAndDate(startStationId, endStationId, date)
    }

}