package com.apnacart.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = SaffronOrange,
    onPrimary = OffWhite,
    secondary = DeepNavy,
    onSecondary = OffWhite,
    tertiary = GoldenYellow,
    background = OffWhite,
    surface = OffWhite,
    surfaceVariant = LightGray,
    onBackground = DarkText,
    onSurface = DarkText,
    error = ErrorRed,
    outline = MidGray
)

private val DarkColors = darkColorScheme(
    primary = SaffronLight,
    onPrimary = BackgroundDark,
    secondary = DeepNavy,
    onSecondary = OffWhite,
    tertiary = GoldenYellow,
    background = BackgroundDark,
    surface = SurfaceDark,
    surfaceVariant = CardDark,
    onBackground = OffWhite,
    onSurface = OffWhite,
    error = ErrorRed,
    outline = OutlineDark
)

@Composable
fun ApnaCartTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = getApnaCartTypography(),
        shapes = ApnaCartShapes,
        content = content
    )
}
