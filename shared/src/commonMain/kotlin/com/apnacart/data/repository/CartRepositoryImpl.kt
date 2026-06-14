package com.apnacart.data.repository

import com.apnacart.data.local.db.ApnaCartDatabase
import com.apnacart.domain.model.CartItem
import com.apnacart.domain.repository.ICartRepository
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first

class CartRepositoryImpl(
    private val database: ApnaCartDatabase,
    private val productRepository: IProductRepository
) : ICartRepository {
    
    private val queries = database.apnaCartDatabaseQueries

    override fun getCartItems(): Flow<Resource<List<CartItem>>> = flow {
        emit(Resource.Loading())
        try {
            val entities = queries.selectAllCartItems().executeAsList()
            val cartItems = entities.mapNotNull { entity ->
                val productResource = productRepository.getProductDetail(entity.productId).first()
                if (productResource is Resource.Success && productResource.data != null) {
                    CartItem(
                        id = entity.id,
                        product = productResource.data,
                        quantity = entity.quantity.toInt()
                    )
                } else null
            }
            emit(Resource.Success(cartItems))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error fetching cart items"))
        }
    }

    override fun addToCart(productId: String, quantity: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            // Using productId as id for simplicity in local DB
            queries.insertCartItem(
                id = productId,
                productId = productId,
                quantity = quantity.toLong()
            )
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error adding to cart"))
        }
    }

    override fun updateQuantity(cartItemId: String, quantity: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            queries.updateCartQuantity(quantity.toLong(), cartItemId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error updating cart quantity"))
        }
    }

    override fun removeFromCart(cartItemId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            queries.deleteCartItem(cartItemId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error removing from cart"))
        }
    }

    override fun clearCart(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            queries.clearCart()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error clearing cart"))
        }
    }
}
