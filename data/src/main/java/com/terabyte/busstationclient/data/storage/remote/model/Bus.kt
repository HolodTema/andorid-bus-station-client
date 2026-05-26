package com.terabyte.busstationclient.data.storage.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Bus(
    val id: Int,
    val registrationNumber: String,
    val model: String,
    val color: String,
    val amountPlaces: Int
)
