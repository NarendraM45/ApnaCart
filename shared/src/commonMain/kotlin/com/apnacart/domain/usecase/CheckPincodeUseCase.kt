package com.apnacart.domain.usecase

import com.apnacart.domain.model.PincodeInfo
import com.apnacart.domain.repository.IPincodeRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class CheckPincodeUseCase(private val repository: IPincodeRepository) {
    operator fun invoke(pincode: String): Flow<Resource<PincodeInfo>> {
        return repository.checkPincode(pincode)
    }
}
