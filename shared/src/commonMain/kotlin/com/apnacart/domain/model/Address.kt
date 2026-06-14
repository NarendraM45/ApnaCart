package com.apnacart.domain.model

data class Address(
    val id: String,
    val label: String,
    val fullName: String,
    val phone: String,
    val line1: String,
    val line2: String?,
    val city: String,
    val state: String,
    val pincode: String,
    val isDefault: Boolean
)
