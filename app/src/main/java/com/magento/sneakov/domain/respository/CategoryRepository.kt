package com.magento.sneakov.domain.respository

import com.magento.sneakov.domain.model.Category

interface CategoryRepository {
    suspend fun getCategoriesLevel2(): List<Category>
}
