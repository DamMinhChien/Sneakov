package com.magento.sneakov.presentation.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magento.sneakov.data.local.DataStoreManager
import com.magento.sneakov.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val token: String? = null
)

class LoginViewModel(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(username: String, password: String){
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)
            try {
                val result = loginUseCase(username, password)
                _uiState.value = LoginUiState(token = result.token, isSuccess = true)
            } catch (e: Exception){
                _uiState.value = LoginUiState(error = e.message)
            }
        }
    }
}