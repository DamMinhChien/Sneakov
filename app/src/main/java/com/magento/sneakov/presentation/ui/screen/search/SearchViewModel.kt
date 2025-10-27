package com.magento.sneakov.presentation.ui.screen.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magento.sneakov.domain.model.SearchRequestBuilder
import com.magento.sneakov.domain.model.SearchRespond
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

class SearchViewModel(private val searchProductsUseCase: SearchProductsUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    fun search(keyword: String) {
        if (keyword.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val request = SearchRequestBuilder()
                .filter(field = "name", value = "%${keyword}%", conditionType = "like")
                .page(1, 20)
                .sort(field = "name", direction = "ASC")
                .build()

            when (val result = searchProductsUseCase(request)) {
                is AppResult.Success -> {
                    Log.d("SearchViewModel", "Result data: ${result.data}")
                    _uiState.value = SearchUiState(
                        result = result.data,
                        isLoading = false,
                        errorMessage = null
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