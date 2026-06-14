package com.apnacart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.apnacart.presentation.navigation.AppNavigation
import com.apnacart.presentation.theme.ApnaCartTheme

@Composable
fun App() {
    ApnaCartTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppNavigation()
        }
    }
}
