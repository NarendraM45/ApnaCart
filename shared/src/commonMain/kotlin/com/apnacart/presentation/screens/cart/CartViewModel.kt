package com.apnacart.presentation.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnacart.domain.model.CartItem
import com.apnacart.domain.repository.ICartRepository
import com.apnacart.util.Resource
import com.apnacart.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: ICartRepository
) : ViewModel() {

    private val _cartState = MutableStateFlow<UiState<List<CartItem>>>(UiState.Idle)
    val cartState = _cartState.asStateFlow()

    fun loadCart() {
        viewModelScope.launch {
            cartRepository.getCartItems().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _cartState.value = UiState.Loading
                    is Resource.Success -> _cartState.value = UiState.Success(resource.data ?: emptyList())
                    is Resource.Error -> _cartState.value = UiState.Error(resource.message ?: "Failed to load cart")
                }
            }
        }
    }
}
