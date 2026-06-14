package com.apnacart.domain.model

data class Review(
    val id: String,
    val productId: String,
    val userId: String,
    val rating: Int,
    val title: String?,
    val body: String?,
    val isVerified: Boolean,
    val helpfulCount: Int,
    val createdAt: String
)
