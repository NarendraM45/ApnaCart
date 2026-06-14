package com.apnacart.domain.model

data class PincodeInfo(
    val pincode: String,
    val city: String,
    val state: String,
    val isServiceable: Boolean,
    val deliveryDays: Int,
    val isCodAvailable: Boolean
)
