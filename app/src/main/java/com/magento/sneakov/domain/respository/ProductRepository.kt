package com.magento.sneakov.domain.respository

import com.magento.sneakov.domain.model.Product
import com.magento.sneakov.domain.model.ProductVariant
import com.magento.sneakov.domain.model.SearchRequest
import com.magento.sneakov.domain.model.SearchRespond
import com.magento.sneakov.domain.util.AppResult

interface ProductRepository{
    suspend fun searchProducts(request: SearchRequest) : AppResult<SearchRespond>
    suspend fun getProductById(id: Int): Product
    suspend fun getProductVariants(parentSku: String): AppResult<List<ProductVariant>>
}