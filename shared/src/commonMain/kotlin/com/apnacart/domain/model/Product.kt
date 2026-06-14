package com.apnacart.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String?,
    val brand: String,
    val price: Double,
    val originalPrice: Double?,
    val discountPercent: Int,
    val rating: Double,
    val reviewCount: Int,
    val images: List<String>,
    val tags: List<String>
)
