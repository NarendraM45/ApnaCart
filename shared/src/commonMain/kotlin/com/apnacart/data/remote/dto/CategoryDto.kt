package com.apnacart.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import com.apnacart.domain.model.Category

@Serializable
data class CategoryDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String,
    @SerialName("icon_url") val iconUrl: String? = null,
    @SerialName("banner_url") val bannerUrl: String? = null
) {
    fun toDomain(): Category {
        return Category(
            id = id,
            name = name,
            slug = slug,
            iconUrl = iconUrl,
            bannerUrl = bannerUrl
        )
    }
}
