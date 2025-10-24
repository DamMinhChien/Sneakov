package com.magento.sneakov.domain.usecase

import com.magento.sneakov.domain.respository.AuthRepository
import com.magento.sneakov.domain.util.AppResult

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): AppResult<Unit> {
        return repository.register(email, password, firstName, lastName)
    }
}