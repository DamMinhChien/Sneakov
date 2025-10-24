package com.magento.sneakov.domain.respository

import com.magento.sneakov.domain.model.UserToken

interface AuthRepository {
    suspend fun login(username: String, password: String): UserToken
}