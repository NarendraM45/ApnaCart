package com.apnacart.domain.usecase

import com.apnacart.domain.repository.ICartRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class AddToCartUseCase(private val repository: ICartRepository) {
    operator fun invoke(productId: String, quantity: Int = 1): Flow<Resource<Unit>> {
        return repository.addToCart(productId, quantity)
    }
}
