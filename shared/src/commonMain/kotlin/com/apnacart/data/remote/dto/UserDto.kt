package com.apnacart.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import com.apnacart.domain.model.User

@Serializable
data class UserDto(
    @SerialName("id") val id: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("email") val email: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null
) {
    fun toDomain(): User {
        return User(
            id = id,
            fullName = fullName,
            email = email,
            phone = phone,
            avatarUrl = avatarUrl
        )
    }
}
