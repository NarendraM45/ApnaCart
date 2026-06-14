package com.apnacart.domain.model

data class Banner(
    val id: String,
    val title: String,
    val subtitle: String?,
    val imageUrl: String,
    val actionUrl: String?
)
