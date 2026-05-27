package com.terabyte.busstationclient.data.storage.remote

import com.terabyte.busstationclient.data.storage.remote.model.Bus
import com.terabyte.busstationclient.data.storage.remote.model.CreateTicketRequest
import com.terabyte.busstationclient.data.storage.remote.model.GetVoyagesByStationsAndDateRequest
import com.terabyte.busstationclient.data.storage.remote.model.RequestError
import com.terabyte.busstationclient.data.storage.remote.model.Station
import com.terabyte.busstationclient.data.storage.remote.model.User
import com.terabyte.busstationclient.data.storage.remote.model.UserAuthResponse
import com.terabyte.busstationclient.data.storage.remote.model.UserLoginRequest
import com.terabyte.busstationclient.data.storage.remote.model.UserRegisterRequest
import com.terabyte.busstationclient.data.storage.remote.model.Voyage

interface NetworkStorage {

    suspend fun login(userLoginRequest: UserLoginRequest): Result<UserAuthResponse>

    suspend fun register(userRegisterRequest: UserRegisterRequest): Result<UserAuthResponse>

    suspend fun getUserById(userId: Int): Result<User>

    suspend fun getAllStations(): Result<List<Station>>

    suspend fun getBusById(busId: Int): Result<Bus>

    suspend fun createTicket(createTicketRequest: CreateTicketRequest): RequestError?

    suspend fun getVoyagesByStationsAndDate(request: GetVoyagesByStationsAndDateRequest): Result<List<Voyage>>

}
