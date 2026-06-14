package com.apnacart.domain.model

data class CartItem(
    val id: String,
    val product: Product,
    val quantity: Int
)
