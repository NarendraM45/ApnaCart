package com.apnacart.data.repository

import com.apnacart.data.remote.dto.UserDto
import com.apnacart.domain.model.User
import com.apnacart.domain.repository.IAuthRepository
import com.apnacart.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val client: SupabaseClient
) : IAuthRepository {

    override fun login(email: String, otp: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            client.auth.verifyEmailOtp(
                type = io.github.jan.supabase.auth.OtpType.Email.MAGIC_LINK,
                email = email,
                token = otp
            )
            // Just return a dummy user after successful OTP for now
            emit(Resource.Success(User(id = "dummy", fullName = "User", email = email, phone = null, avatarUrl = null)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Login failed"))
        }
    }

    override fun requestOtp(email: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            client.auth.signInWith(OTP) {
                this.email = email
            }
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "OTP Request failed"))
        }
    }

    override fun logout(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            client.auth.signOut()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Logout failed"))
        }
    }

    override fun getCurrentUser(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val user = client.auth.currentUserOrNull()
            if (user != null) {
                emit(Resource.Success(User(id = user.id, fullName = "User", email = user.email, phone = user.phone, avatarUrl = null)))
            } else {
                emit(Resource.Error("No user logged in"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error getting current user"))
        }
    }
}
