package com.magento.sneakov.domain.usecase

import com.magento.sneakov.domain.model.SearchRequest
import com.magento.sneakov.domain.model.SearchRespond
import com.magento.sneakov.domain.respository.ProductRepository
import com.magento.sneakov.domain.util.AppResult

class SearchProductsUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(request: SearchRequest): AppResult<SearchRespond>{
        if(request.filters.isEmpty()){
            return AppResult.Error("Tìm kiếm cần ít nhất 1 trường dữ liệu")
        }
        return productRepository.searchProducts(request)
    }
}