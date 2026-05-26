package com.terabyte.busstationclient.data.storage.remote

import com.terabyte.busstationclient.data.storage.remote.model.Bus
import com.terabyte.busstationclient.data.storage.remote.model.CreateTicketRequest
import com.terabyte.busstationclient.data.storage.remote.model.RequestError
import com.terabyte.busstationclient.data.storage.remote.model.Station
import com.terabyte.busstationclient.data.storage.remote.model.User
import com.terabyte.busstationclient.data.storage.remote.model.UserAuthResponse
import com.terabyte.busstationclient.data.storage.remote.model.UserLoginRequest
import com.terabyte.busstationclient.data.storage.remote.model.UserRegisterRequest
import retrofit2.Response
import javax.inject.Inject


class NetworkStorageImpl @Inject constructor(
    private val retrofitService: RetrofitService
): NetworkStorage {

    override suspend fun login(userLoginRequest: UserLoginRequest): Result<UserAuthResponse> {
        val response = retrofitService.login(userLoginRequest)
        return wrapResponseInResultObject(response)
    }

    override suspend fun register(userRegisterRequest: UserRegisterRequest): Result<UserAuthResponse> {
        val response = retrofitService.register(userRegisterRequest)
        return wrapResponseInResultObject(response)
    }

    override suspend fun getUserById(userId: Int): Result<User> {
        val response = retrofitService.getUserById(userId)
        return wrapResponseInResultObject(response)
    }

    override suspend fun getAllStations(): Result<List<Station>> {
        val response = retrofitService.getAllStations()
        return wrapResponseInResultObject(response)
    }

    override suspend fun getBusById(busId: Int): Result<Bus> {
        val response = retrofitService.getBusById(busId)
        return wrapResponseInResultObject(response)
    }

    override suspend fun createTicket(createTicketRequest: CreateTicketRequest): RequestError? {
        val response = retrofitService.createTicket(createTicketRequest)
        return if (response.isSuccessful) {
            null
        }
        else {
            if (response.code() == 401) {
                RequestError.TokenExpiredError()
            }
            else {
                RequestError.UnknownError()
            }
        }
    }

    private fun<T> wrapResponseInResultObject(response: Response<T>): Result<T> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.failure(RequestError.UnknownError())
            }
            else {
                Result.success(body)
            }
        }
        else {
            if (response.code() == 401) {
                Result.failure(RequestError.TokenExpiredError())
            }
            else {
                Result.failure(RequestError.UnknownError())
            }
        }
    }
}