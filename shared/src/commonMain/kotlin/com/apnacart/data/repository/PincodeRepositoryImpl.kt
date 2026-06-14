package com.apnacart.data.repository

import com.apnacart.domain.model.PincodeInfo
import com.apnacart.domain.repository.IPincodeRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PincodeRepositoryImpl() : IPincodeRepository {
    override fun checkPincode(pincode: String): Flow<Resource<PincodeInfo>> = flow {
        emit(Resource.Loading())
        // Dummy implementation
        emit(Resource.Success(PincodeInfo(pincode, "City", "State", true, 3, true)))
    }
}
