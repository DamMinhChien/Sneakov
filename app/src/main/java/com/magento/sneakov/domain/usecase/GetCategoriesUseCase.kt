package com.magento.sneakov.domain.usecase

import com.magento.sneakov.domain.model.Category
import com.magento.sneakov.domain.respository.CategoryRepository
import com.magento.sneakov.domain.util.AppResult

class GetCategoriesUseCase(private val repository: CategoryRepository) {
    suspend operator fun invoke(): AppResult<List<Category>>{
        return try {
            val resonse = repository.getCategoriesLevel2()
            AppResult.Success(resonse)
        } catch (e: Exception){
            AppResult.Error(e.message ?: "Lỗi khi lấy danh mục")
        }
    }
}