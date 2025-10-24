package com.magento.sneakov.data.respository

import com.magento.sneakov.data.remote.api.SneakovApiService
import com.magento.sneakov.data.remote.dto.LoginRequestDto
import com.magento.sneakov.domain.model.UserToken
import com.magento.sneakov.domain.respository.AuthRepository

class AuthRepositoryImpl(private val api: SneakovApiService): AuthRepository {
    override suspend fun login(
        username: String,
        password: String
    ): UserToken {
        val response = api.login(LoginRequestDto(username, password))
        val token = response.body() ?: ""
        return UserToken(token = token)
    }

}