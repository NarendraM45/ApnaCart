package com.apnacart.presentation.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apnacart.presentation.components.CustomTextField
import com.apnacart.presentation.components.PrimaryButton
import com.apnacart.util.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val email by viewModel.email.collectAsState()
    val otp by viewModel.otp.collectAsState()
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is UiState.Success) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to ApnaCart", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))

        CustomTextField(
            value = email,
            onValueChange = viewModel::updateEmail,
            label = "Email Address"
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        CustomTextField(
            value = otp,
            onValueChange = viewModel::updateOtp,
            label = "OTP"
        )
        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = if (loginState is UiState.Loading) "Loading..." else "Login",
            onClick = viewModel::login,
            enabled = loginState !is UiState.Loading && email.isNotEmpty() && otp.isNotEmpty()
        )

        if (loginState is UiState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = (loginState as UiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
