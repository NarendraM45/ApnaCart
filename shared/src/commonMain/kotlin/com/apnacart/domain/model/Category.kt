package com.apnacart.domain.model

data class Category(
    val id: String,
    val name: String,
    val slug: String,
    val iconUrl: String?,
    val bannerUrl: String?
)
