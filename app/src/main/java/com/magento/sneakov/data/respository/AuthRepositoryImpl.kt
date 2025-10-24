package com.magento.sneakov.data.respository

import com.magento.sneakov.data.remote.api.SneakovApiService
import com.magento.sneakov.data.remote.dto.CustomerInfoDto
import com.magento.sneakov.data.remote.dto.LoginRequestDto
import com.magento.sneakov.data.remote.dto.RegisterRequestDto
import com.magento.sneakov.domain.model.UserToken
import com.magento.sneakov.domain.respository.AuthRepository
import com.magento.sneakov.domain.util.AppResult
import retrofit2.HttpException

class AuthRepositoryImpl(private val api: SneakovApiService) : AuthRepository {
    override suspend fun login(
        username: String,
        password: String
    ): UserToken {
        val response = api.login(LoginRequestDto(username, password))
        val token = response.body() ?: ""
        return UserToken(token = token)
    }

    override suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): AppResult<Unit> {
        return try {
            val request =
                RegisterRequestDto(customer = CustomerInfoDto(email, firstName, lastName), password)
            api.register(request)
            AppResult.Success(Unit)
        } catch (e: HttpException) {
            // Lỗi HTTP (ví dụ: 400 - email đã tồn tại)
            val message = when (e.code()) {
                400, 409 -> "Email đã tồn tại hoặc dữ liệu không hợp lệ"
                else -> "Lỗi máy chủ (${e.code()})"
            }
            AppResult.Error(message)
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Đăng ký thất bại")
        }
    }
}