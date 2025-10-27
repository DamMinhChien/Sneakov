package com.magento.sneakov.data.respository

import com.magento.sneakov.data.mappper.toDomain
import com.magento.sneakov.data.remote.api.SneakovApiService
import com.magento.sneakov.domain.model.SearchRequest
import com.magento.sneakov.domain.model.SearchRespond
import com.magento.sneakov.domain.respository.ProductRepository
import com.magento.sneakov.domain.util.AppResult
import okio.IOException
import retrofit2.HttpException

class ProductRepositoryImpl(val api: SneakovApiService): ProductRepository {
    override suspend fun searchProducts(request: SearchRequest): AppResult<SearchRespond> {
        return try {
            val queryMap = request.toMagentoQueryMap()
            val response = api.searchProducts(queryMap)
            AppResult.Success(response.toDomain())
        } catch (e: HttpException){
            AppResult.Error("Lỗi HTTP: ${e.message()}", e.code())
        } catch (e: IOException){
            AppResult.Error("Lỗi mạng: ${e.message}")
        } catch (e: Exception){
            AppResult.Error("Lỗi không xác định: ${e.message}")
        }
    }
}

fun SearchRequest.toMagentoQueryMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()

    // Filters
    filters.forEachIndexed { groupIndex, filter ->
        map["searchCriteria[filter_groups][$groupIndex][filters][0][field]"] = filter.field
        map["searchCriteria[filter_groups][$groupIndex][filters][0][value]"] = filter.value
        map["searchCriteria[filter_groups][$groupIndex][filters][0][condition_type]"] = filter.conditionType
    }

    // Sorts
    sorts.forEachIndexed { index, sort ->
        map["searchCriteria[sortOrders][$index][field]"] = sort.field
        map["searchCriteria[sortOrders][$index][direction]"] = sort.direction
    }

    // Paging
    map["searchCriteria[currentPage]"] = currentPage.toString()
    map["searchCriteria[pageSize]"] = pageSize.toString()

    // 👇 Hard-code field trả về
    map["fields"] = "items[id,sku,name,price,custom_attributes],total_count"

    return map
}