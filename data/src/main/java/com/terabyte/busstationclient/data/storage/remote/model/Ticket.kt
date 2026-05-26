package com.terabyte.busstationclient.data.storage.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val id: Int,
    val voyageId: Int,
    val userId: Int,
    val placeNumber: Int,
    val hasBaggage: Boolean
)

@Serializable
data class CreateTicketRequest(
    val voyageId: Int,
    val userId: Int,
    val placeNumber: Int,
    val hasBaggage: Boolean
)

@Serializable
data class UpdateTicketRequest(
    val id: Int,
    val voyageId: Int,
    val userId: Int,
    val placeNumber: Int,
    val hasBaggage: Boolean
)
