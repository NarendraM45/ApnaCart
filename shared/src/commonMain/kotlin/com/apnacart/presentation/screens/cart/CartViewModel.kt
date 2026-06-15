package com.apnacart.presentation.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnacart.domain.model.CartItem
import com.apnacart.domain.repository.ICartRepository
import com.apnacart.util.Resource
import com.apnacart.util.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: ICartRepository
) : ViewModel() {

    private val _cartState = MutableStateFlow<UiState<List<CartItem>>>(UiState.Idle)
    val cartState = _cartState.asStateFlow()

    val totalPrice: StateFlow<Double> = _cartState
        .map { state ->
            if (state is UiState.Success) {
                state.data.sumOf { it.quantity.toDouble() /* multiply by price once ProductRepository is wired */ }
            } else 0.0
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

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

    fun updateQuantity(item: CartItem, newQty: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(item.productId, newQty).collect { }
            loadCart()
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            cartRepository.removeFromCart(productId).collect { }
            loadCart()
        }
    }
}
