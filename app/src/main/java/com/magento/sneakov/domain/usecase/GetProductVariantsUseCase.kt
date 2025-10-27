package com.magento.sneakov.domain.usecase

import com.magento.sneakov.domain.model.ProductVariant
import com.magento.sneakov.domain.respository.ProductRepository
import com.magento.sneakov.domain.util.AppResult

class GetProductVariantsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(parentSku: String): AppResult<List<ProductVariant>> {
        return repository.getProductVariants(parentSku)
    }
}
