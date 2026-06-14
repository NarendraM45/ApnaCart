package com.apnacart.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import com.apnacart.domain.model.Banner

@Serializable
data class BannerDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String? = null,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("action_url") val actionUrl: String? = null
) {
    fun toDomain(): Banner {
        return Banner(
            id = id,
            title = title,
            subtitle = subtitle,
            imageUrl = imageUrl,
            actionUrl = actionUrl
        )
    }
}
