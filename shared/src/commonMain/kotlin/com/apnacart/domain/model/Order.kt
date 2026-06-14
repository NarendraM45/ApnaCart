package com.apnacart.domain.model

data class Order(
    val id: String,
    val orderNumber: String,
    val status: String,
    val total: Double,
    val date: String,
    val items: List<OrderItem>
)

data class OrderItem(
    val id: String,
    val productName: String,
    val productImage: String?,
    val quantity: Int,
    val unitPrice: Double
)
