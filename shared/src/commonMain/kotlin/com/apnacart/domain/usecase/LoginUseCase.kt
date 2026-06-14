package com.apnacart.domain.usecase

import com.apnacart.domain.model.User
import com.apnacart.domain.repository.IAuthRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val repository: IAuthRepository) {
    operator fun invoke(email: String, otp: String): Flow<Resource<User>> {
        return repository.login(email, otp)
    }
}
