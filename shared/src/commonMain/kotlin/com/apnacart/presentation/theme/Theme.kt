package com.apnacart.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = AmazonNavy,
    onPrimary = Color.White,
    secondary = AmazonOrange,
    onSecondary = Color.White,
    tertiary = AmazonYellow,
    background = LightGray,
    surface = Color.White,
    surfaceVariant = Color.White,
    onBackground = DarkText,
    onSurface = DarkText,
    error = ErrorRed,
    outline = MidGray
)

private val DarkColors = darkColorScheme(
    primary = AmazonNavy,
    onPrimary = Color.White,
    secondary = AmazonOrange,
    onSecondary = Color.White,
    tertiary = AmazonYellow,
    background = BackgroundDark,
    surface = SurfaceDark,
    surfaceVariant = CardDark,
    onBackground = Color.White,
    onSurface = Color.White,
    error = ErrorRed,
    outline = OutlineDark
)

@Composable
fun ApnaCartTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = getApnaCartTypography(),
        shapes = ApnaCartShapes,
        content = content
    )
}
