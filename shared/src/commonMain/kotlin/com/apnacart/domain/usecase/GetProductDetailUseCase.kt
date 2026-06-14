package com.apnacart.domain.usecase

import com.apnacart.domain.model.Product
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class GetProductDetailUseCase(private val repository: IProductRepository) {
    operator fun invoke(productId: String): Flow<Resource<Product>> {
        return repository.getProductDetail(productId)
    }
}
