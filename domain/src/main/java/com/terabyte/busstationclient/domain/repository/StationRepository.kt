package com.terabyte.busstationclient.domain.repository

import com.terabyte.busstationclient.domain.model.shop.Station

interface StationRepository {

    suspend fun getAllStations(): Result<List<Station>>

}