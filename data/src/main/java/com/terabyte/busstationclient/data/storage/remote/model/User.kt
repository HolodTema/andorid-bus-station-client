package com.terabyte.busstationclient.data.storage.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
)

@Serializable
data class UserRegisterRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)

@Serializable
data class UserLoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class UserAuthResponse(
    val token: String,
    val userId: Int
)
