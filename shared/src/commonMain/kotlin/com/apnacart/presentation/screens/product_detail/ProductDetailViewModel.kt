package com.apnacart.presentation.screens.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnacart.domain.model.Product
import com.apnacart.domain.usecase.AddToCartUseCase
import com.apnacart.domain.usecase.GetProductDetailUseCase
import com.apnacart.domain.usecase.ToggleWishlistUseCase
import com.apnacart.util.Resource
import com.apnacart.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val toggleWishlistUseCase: ToggleWishlistUseCase
) : ViewModel() {

    private val _productState = MutableStateFlow<UiState<Product>>(UiState.Idle)
    val productState = _productState.asStateFlow()

    private val _addToCartState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val addToCartState = _addToCartState.asStateFlow()

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            getProductDetailUseCase(productId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _productState.value = UiState.Loading
                    is Resource.Success -> _productState.value = UiState.Success(resource.data!!)
                    is Resource.Error -> _productState.value = UiState.Error(resource.message ?: "Failed")
                }
            }
        }
    }

    fun addToCart(productId: String) {
        viewModelScope.launch {
            addToCartUseCase(productId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _addToCartState.value = UiState.Loading
                    is Resource.Success -> _addToCartState.value = UiState.Success(Unit)
                    is Resource.Error -> _addToCartState.value = UiState.Error(resource.message ?: "Failed")
                }
            }
        }
    }
}
