package com.apnacart.data.repository

import com.apnacart.data.local.db.ApnaCartDatabase
import com.apnacart.domain.model.Product
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.domain.repository.IWishlistRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first

class WishlistRepositoryImpl(
    private val database: ApnaCartDatabase,
    private val productRepository: IProductRepository
) : IWishlistRepository {
    
    private val queries = database.apnaCartDatabaseQueries

    override fun getWishlist(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val entities = queries.selectAllWishlistItems().executeAsList()
            val products = entities.mapNotNull { entity ->
                val productResource = productRepository.getProductDetail(entity.productId).first()
                if (productResource is Resource.Success && productResource.data != null) {
                    productResource.data
                } else null
            }
            emit(Resource.Success(products))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error fetching wishlist"))
        }
    }

    override fun addToWishlist(productId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            queries.insertWishlistItem(productId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error adding to wishlist"))
        }
    }

    override fun removeFromWishlist(productId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            queries.deleteWishlistItem(productId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error removing from wishlist"))
        }
    }

    override fun checkWishlistStatus(productId: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val count = queries.checkWishlistStatus(productId).executeAsOne()
            emit(Resource.Success(count > 0))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error checking wishlist status"))
        }
    }
}
