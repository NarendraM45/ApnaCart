package com.apnacart.data.repository

import com.apnacart.data.remote.dto.BannerDto
import com.apnacart.data.remote.dto.CategoryDto
import com.apnacart.data.remote.dto.ProductDto
import com.apnacart.domain.model.Banner
import com.apnacart.domain.model.Category
import com.apnacart.domain.model.Product
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(
    private val client: SupabaseClient
) : IProductRepository {

    override fun getProducts(categorySlug: String?): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val response = if (categorySlug != null) {
                client.postgrest["products"].select {
                    filter {
                        eq("category_slug", categorySlug)
                    }
                }.decodeList<ProductDto>()
            } else {
                client.postgrest["products"].select().decodeList<ProductDto>()
            }
            emit(Resource.Success(response.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred fetching products"))
        }
    }

    override fun getProductDetail(productId: String): Flow<Resource<Product>> = flow {
        emit(Resource.Loading())
        try {
            val response = client.postgrest["products"].select {
                filter {
                    eq("id", productId)
                }
            }.decodeSingle<ProductDto>()
            emit(Resource.Success(response.toDomain()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred fetching product details"))
        }
    }

    override fun searchProducts(query: String): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val response = client.postgrest["products"].select {
                filter {
                    ilike("name", "%$query%")
                }
            }.decodeList<ProductDto>()
            emit(Resource.Success(response.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred searching products"))
        }
    }

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        try {
            val response = client.postgrest["categories"].select().decodeList<CategoryDto>()
            emit(Resource.Success(response.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred fetching categories"))
        }
    }

    override fun getBanners(): Flow<Resource<List<Banner>>> = flow {
        emit(Resource.Loading())
        try {
            val response = client.postgrest["banners"].select().decodeList<BannerDto>()
            emit(Resource.Success(response.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred fetching banners"))
        }
    }
}
