package com.apnacart.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _navigateToNext = MutableStateFlow(false)
    val navigateToNext = _navigateToNext.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000) // Simulate splash delay or auth check
            _navigateToNext.value = true
        }
    }
}
