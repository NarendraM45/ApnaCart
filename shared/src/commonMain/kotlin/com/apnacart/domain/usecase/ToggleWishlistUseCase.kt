package com.apnacart.domain.usecase

import com.apnacart.domain.repository.IWishlistRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ToggleWishlistUseCase(private val repository: IWishlistRepository) {
    operator fun invoke(productId: String, isCurrentlyInWishlist: Boolean): Flow<Resource<Unit>> {
        return if (isCurrentlyInWishlist) {
            repository.removeFromWishlist(productId)
        } else {
            repository.addToWishlist(productId)
        }
    }
}
