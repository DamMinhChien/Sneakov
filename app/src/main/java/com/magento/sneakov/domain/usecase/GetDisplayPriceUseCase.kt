package com.magento.sneakov.domain.usecase

import com.magento.sneakov.domain.model.Product
import com.magento.sneakov.domain.respository.ProductRepository
import com.magento.sneakov.domain.util.AppResult

class GetDisplayPriceUseCase(
    private val getProductVariantsUseCase: GetProductVariantsUseCase
) {
    suspend operator fun invoke(parentSku: String): AppResult<Pair<Double, Double>> {
        return when (val result = getProductVariantsUseCase(parentSku)) {
            is AppResult.Success -> {
                val variants = result.data
                val min = variants.minOfOrNull { it.price } ?: 0.0
                val max = variants.maxOfOrNull { it.price } ?: 0.0
                AppResult.Success(min to max)
            }
            is AppResult.Error -> result
            AppResult.Loading -> AppResult.Loading
        }
    }
}

