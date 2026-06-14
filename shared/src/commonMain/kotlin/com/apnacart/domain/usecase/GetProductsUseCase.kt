package com.apnacart.domain.usecase

import com.apnacart.domain.model.Product
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(private val repository: IProductRepository) {
    operator fun invoke(categorySlug: String? = null): Flow<Resource<List<Product>>> {
        return repository.getProducts(categorySlug)
    }
}
