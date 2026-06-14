package com.apnacart.domain.repository

import com.apnacart.domain.model.User
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    fun login(email: String, otp: String): Flow<Resource<User>>
    fun requestOtp(email: String): Flow<Resource<Unit>>
    fun logout(): Flow<Resource<Unit>>
    fun getCurrentUser(): Flow<Resource<User>>
}
