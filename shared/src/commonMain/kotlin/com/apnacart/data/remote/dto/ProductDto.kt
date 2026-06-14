package com.apnacart.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import com.apnacart.domain.model.Product

@Serializable
data class ProductDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String? = null,
    @SerialName("brand") val brand: String,
    @SerialName("price") val price: Double,
    @SerialName("original_price") val originalPrice: Double? = null,
    @SerialName("discount_percent") val discountPercent: Int = 0,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("review_count") val reviewCount: Int = 0,
    @SerialName("images") val images: List<String> = emptyList(),
    @SerialName("tags") val tags: List<String> = emptyList()
) {
    fun toDomain(): Product {
        return Product(
            id = id,
            name = name,
            description = description,
            brand = brand,
            price = price,
            originalPrice = originalPrice,
            discountPercent = discountPercent,
            rating = rating,
            reviewCount = reviewCount,
            images = images,
            tags = tags
        )
    }
}
