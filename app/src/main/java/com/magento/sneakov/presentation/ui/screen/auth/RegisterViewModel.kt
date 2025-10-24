package com.magento.sneakov.presentation.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magento.sneakov.domain.usecase.RegisterUseCase
import com.magento.sneakov.domain.util.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//trạng thái ban đầu
data class RegisterUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)
class RegisterViewModel(private val registerUseCase: RegisterUseCase): ViewModel() {
    val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
    fun register(email: String, password: String, fullName: String){
        val parts = fullName.trim().split(Regex("\\s+"))
        val firstName = parts.firstOrNull() ?: ""
        val lastName = parts.drop(1).joinToString(" ")

        viewModelScope.launch {
            _uiState.value = RegisterUiState(isLoading = true)

            when(val result = registerUseCase(email, password, firstName, lastName)){
                is AppResult.Success -> _uiState.value = RegisterUiState(isSuccess = true)
                is AppResult.Error -> _uiState.value = RegisterUiState(error = result.message)
                else -> Unit
            }
        }
    }
}