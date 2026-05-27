package com.terabyte.busstationclient.domain.model.shop

data class VoyageWithStationPair(
    val voyage: Voyage,
    val startStation: Station,
    val endStation: Station
)

