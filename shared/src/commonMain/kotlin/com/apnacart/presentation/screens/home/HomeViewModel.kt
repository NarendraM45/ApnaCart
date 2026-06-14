package com.apnacart.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnacart.domain.model.Banner
import com.apnacart.domain.model.Category
import com.apnacart.domain.model.Product
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.util.Resource
import com.apnacart.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeState(
    val banners: UiState<List<Banner>> = UiState.Idle,
    val categories: UiState<List<Category>> = UiState.Idle,
    val featuredProducts: UiState<List<Product>> = UiState.Idle
)

class HomeViewModel(
    private val productRepository: IProductRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            productRepository.getBanners().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _state.value = _state.value.copy(banners = UiState.Loading)
                    is Resource.Success -> _state.value = _state.value.copy(banners = UiState.Success(resource.data ?: emptyList()))
                    is Resource.Error -> _state.value = _state.value.copy(banners = UiState.Error(resource.message ?: "Error"))
                }
            }
        }
        viewModelScope.launch {
            productRepository.getCategories().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _state.value = _state.value.copy(categories = UiState.Loading)
                    is Resource.Success -> _state.value = _state.value.copy(categories = UiState.Success(resource.data ?: emptyList()))
                    is Resource.Error -> _state.value = _state.value.copy(categories = UiState.Error(resource.message ?: "Error"))
                }
            }
        }
        viewModelScope.launch {
            productRepository.getProducts().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _state.value = _state.value.copy(featuredProducts = UiState.Loading)
                    is Resource.Success -> _state.value = _state.value.copy(featuredProducts = UiState.Success(resource.data ?: emptyList()))
                    is Resource.Error -> _state.value = _state.value.copy(featuredProducts = UiState.Error(resource.message ?: "Error"))
                }
            }
        }
    }
}
