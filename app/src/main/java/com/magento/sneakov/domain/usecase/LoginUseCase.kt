package com.magento.sneakov.domain.usecase

import com.magento.sneakov.data.local.DataStoreManager
import com.magento.sneakov.domain.model.UserToken
import com.magento.sneakov.domain.respository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) {
    val passwordRegex = Regex("^(?=.*\\d).{6,}$")
    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    suspend operator fun invoke(username: String, password: String): UserToken {

        // Validate email
        if (!emailRegex.matches(username)) {
            throw IllegalArgumentException("Email không hợp lệ")
        }

        // Validate password
        if (!passwordRegex.matches(password)) {
            throw IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự, gồm chữ hoa và số")
        }
        // 1. Gọi repository để login
        val result = repository.login(username, password)

        // 2. Lưu token vào DataStore
        dataStoreManager.saveToken(result.token)

        // 3. Trả về token
        return result
    }
}