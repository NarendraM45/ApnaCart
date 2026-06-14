package com.apnacart.domain.repository

import com.apnacart.domain.model.Product
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

interface IWishlistRepository {
    fun getWishlist(): Flow<Resource<List<Product>>>
    fun addToWishlist(productId: String): Flow<Resource<Unit>>
    fun removeFromWishlist(productId: String): Flow<Resource<Unit>>
    fun checkWishlistStatus(productId: String): Flow<Resource<Boolean>>
}
