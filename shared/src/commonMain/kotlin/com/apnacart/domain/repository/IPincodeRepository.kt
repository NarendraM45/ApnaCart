package com.apnacart.domain.repository

import com.apnacart.domain.model.PincodeInfo
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

interface IPincodeRepository {
    fun checkPincode(pincode: String): Flow<Resource<PincodeInfo>>
}
