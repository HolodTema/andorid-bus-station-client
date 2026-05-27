package com.terabyte.busstationclient.domain.usecase.voyage

import com.terabyte.busstationclient.domain.model.shop.Station
import com.terabyte.busstationclient.domain.model.shop.Voyage
import com.terabyte.busstationclient.domain.model.shop.VoyageWithStationPair
import javax.inject.Inject

class MatchVoyageWithStationPairUseCase @Inject constructor(

) {

    fun invoke(voyages: List<Voyage>, stations: List<Station>): List<VoyageWithStationPair> {
        return voyages.map { voyage ->
            VoyageWithStationPair(
                voyage = voyage,
                startStation = stations.find { it.id == voyage.startStationId }!!,
                endStation =  stations.find { it.id == voyage.endStationId }!!
            )
        }
    }
}