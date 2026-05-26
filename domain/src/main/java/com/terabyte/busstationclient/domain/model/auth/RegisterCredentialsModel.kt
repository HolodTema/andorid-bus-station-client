package com.terabyte.busstationclient.domain.model.auth

data class RegisterCredentialsModel(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)