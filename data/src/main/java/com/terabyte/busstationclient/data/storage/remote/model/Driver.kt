package com.terabyte.busstationclient.data.storage.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
//    val licenseCategoryId: Int,
    val experienceYears: Int
)
