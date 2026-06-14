package com.apnacart.domain.repository

import com.apnacart.domain.model.CartItem
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

interface ICartRepository {
    fun getCartItems(): Flow<Resource<List<CartItem>>>
    fun addToCart(productId: String, quantity: Int): Flow<Resource<Unit>>
    fun updateQuantity(cartItemId: String, quantity: Int): Flow<Resource<Unit>>
    fun removeFromCart(cartItemId: String): Flow<Resource<Unit>>
    fun clearCart(): Flow<Resource<Unit>>
}
