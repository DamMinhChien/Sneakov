package com.magento.sneakov.domain.respository

import com.magento.sneakov.domain.model.UserToken
import com.magento.sneakov.domain.util.AppResult

interface AuthRepository {
    suspend fun login(username: String, password: String): UserToken
    suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): AppResult<Unit>
}