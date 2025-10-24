package com.magento.sneakov.data.remote.dto

data class CustomerInfoDto(
    val email: String,
    val firstname: String,
    val lastname: String
)

data class RegisterRequestDto(
    val customer: CustomerInfoDto,
    val password: String
)
