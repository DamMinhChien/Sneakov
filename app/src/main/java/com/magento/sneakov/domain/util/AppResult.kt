package com.magento.sneakov.domain.util

// Result<T> là một sealed class bao bọc kết quả thành công hoặc lỗi
sealed class AppResult<out T> {

    // Thành công, có thể chứa dữ liệu kiểu T
    data class Success<out T>(val data: T) : AppResult<T>()

    // Lỗi, chứa thông báo và mã lỗi tùy chọn
    data class Error(val message: String, val code: Int? = null) : AppResult<Nothing>()

    // Đang tải (tuỳ chọn)
    object Loading : AppResult<Nothing>()
}