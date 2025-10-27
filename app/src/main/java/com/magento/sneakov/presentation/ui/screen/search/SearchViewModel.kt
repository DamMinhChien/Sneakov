package com.magento.sneakov.presentation.ui.screen.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magento.sneakov.domain.model.SearchRequestBuilder
import com.magento.sneakov.domain.model.SearchRespond
import com.magento.sneakov.domain.usecase.GetDisplayPriceUseCase
import com.magento.sneakov.domain.usecase.SearchProductsUseCase
import com.magento.sneakov.domain.util.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SearchUiState(
    val result: SearchRespond? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class SearchViewModel(private val searchProductsUseCase: SearchProductsUseCase, private val getDisplayPriceUseCase: GetDisplayPriceUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    fun search(keyword: String) {
        if (keyword.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val request = SearchRequestBuilder()
                .filter(field = "name", value = "%${keyword}%", conditionType = "like")
                .filter(field = "type_id", value = "configurable", conditionType = "eq")
                .page(1, 20)
                .sort(field = "name", direction = "ASC")
                .build()

            when (val result = searchProductsUseCase(request)) {
                is AppResult.Success -> {
                    val searchResult = result.data
                    val updatedItems = searchResult.items?.toMutableList() ?: mutableListOf()

                    //  Tạo danh sách có thêm giá min-max
                    updatedItems.forEachIndexed { index, product ->
                        viewModelScope.launch {
                            when (val priceResult = getDisplayPriceUseCase(product.sku)) {
                                is AppResult.Success -> {
                                    // bạn có thể log hoặc gán thêm field priceRange nếu muốn
                                    val (minPrice, maxPrice) = priceResult.data
                                    updatedItems[index] = product.copy(priceRange = minPrice to maxPrice)

                                    // Cập nhật lại state để UI recompose
                                    _uiState.value = _uiState.value.copy(
                                        result = _uiState.value.result?.copy(items = updatedItems)
                                    )

                                    Log.d(
                                        "SearchViewModel",
                                        "SKU ${product.sku} => Giá ${priceResult.data.first} - ${priceResult.data.second}"
                                    )
                                }
                                is AppResult.Error -> {
                                    Log.e("SearchViewModel", "Lỗi lấy giá: ${priceResult.message}")
                                }
                                else -> Unit
                            }
                        }
                        product
                    }

                    _uiState.value = SearchUiState(
                        result = searchResult.copy(items = updatedItems),
                        isLoading = false
                    )
                }
                is AppResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
                AppResult.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }

        }

    }

    fun resetError(){
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}