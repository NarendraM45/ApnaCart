package com.apnacart.domain.repository

import com.apnacart.domain.model.Product
import com.apnacart.domain.model.Category
import com.apnacart.domain.model.Banner
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    fun getProducts(categorySlug: String? = null): Flow<Resource<List<Product>>>
    fun getProductDetail(productId: String): Flow<Resource<Product>>
    fun searchProducts(query: String): Flow<Resource<List<Product>>>
    fun getCategories(): Flow<Resource<List<Category>>>
    fun getBanners(): Flow<Resource<List<Banner>>>
}
