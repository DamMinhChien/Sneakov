package com.magento.sneakov.data.respository

import com.magento.sneakov.data.mappper.toDomain
import com.magento.sneakov.data.remote.api.SneakovApiService
import com.magento.sneakov.domain.model.Category
import com.magento.sneakov.domain.respository.CategoryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class CategoryRepositoryImpl(private val api: SneakovApiService): CategoryRepository {
    override suspend fun getCategoriesLevel2(): List<Category> = coroutineScope {
        val tree = api.getCategoryTree()

        val level2 = tree.children_data?.filter { it.level == 2 } ?: emptyList()

        val detailed = level2.map { category ->
            async {
                val detail = api.getCategoryDetail(category.id)
                detail.toDomain()
            }
        }

        detailed.awaitAll()
    }
}