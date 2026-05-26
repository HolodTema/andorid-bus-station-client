package com.terabyte.busstationclient.data.storage.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Station(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val hasBuilding: Boolean,
    val hasRestroom: Boolean,
    val hasCaffe: Boolean,
    val hasTown: Boolean
)
