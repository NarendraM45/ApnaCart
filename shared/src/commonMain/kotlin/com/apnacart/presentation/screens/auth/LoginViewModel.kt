package com.apnacart.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnacart.domain.usecase.LoginUseCase
import com.apnacart.util.Resource
import com.apnacart.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _otp = MutableStateFlow("")
    val otp = _otp.asStateFlow()

    private val _loginState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val loginState = _loginState.asStateFlow()

    fun updateEmail(email: String) { _email.value = email }
    fun updateOtp(otp: String) { _otp.value = otp }

    fun login() {
        viewModelScope.launch {
            loginUseCase(email.value, otp.value).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _loginState.value = UiState.Loading
                    is Resource.Success -> _loginState.value = UiState.Success(Unit)
                    is Resource.Error -> _loginState.value = UiState.Error(resource.message ?: "Login Failed")
                }
            }
        }
    }
}
