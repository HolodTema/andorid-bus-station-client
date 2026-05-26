package com.terabyte.busstationclient.data.storage.remote

import com.terabyte.busstationclient.data.storage.remote.model.Bus
import com.terabyte.busstationclient.data.storage.remote.model.CreateTicketRequest
import com.terabyte.busstationclient.data.storage.remote.model.Station
import com.terabyte.busstationclient.data.storage.remote.model.User
import com.terabyte.busstationclient.data.storage.remote.model.UserAuthResponse
import com.terabyte.busstationclient.data.storage.remote.model.UserLoginRequest
import com.terabyte.busstationclient.data.storage.remote.model.UserRegisterRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {

    @POST("api/auth/user/login")
    suspend fun login(@Body userLoginRequest: UserLoginRequest): Response<UserAuthResponse>

    @POST("api/auth/user/register")
    suspend fun register(@Body userRegisterRequest: UserRegisterRequest): Response<UserAuthResponse>

    @GET("api/user/{id}")
    suspend fun getUserById(@Query("id") userId: Int): Response<User>

    @GET("api/station")
    suspend fun getAllStations(): Response<List<Station>>

    @GET("api/bus/{id}")
    suspend fun getBusById(@Query("id") busId: Int): Response<Bus>

    // actually we do not handle body of this POST request. That is why we use ResponseBody type
    @POST("api/ticket")
    suspend fun createTicket(@Body createTicketRequest: CreateTicketRequest): Response<ResponseBody>
}
