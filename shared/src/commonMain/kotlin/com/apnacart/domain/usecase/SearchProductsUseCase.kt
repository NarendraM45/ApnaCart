package com.apnacart.domain.usecase

import com.apnacart.domain.model.Product
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class SearchProductsUseCase(private val repository: IProductRepository) {
    operator fun invoke(query: String): Flow<Resource<List<Product>>> {
        return repository.searchProducts(query)
    }
}
