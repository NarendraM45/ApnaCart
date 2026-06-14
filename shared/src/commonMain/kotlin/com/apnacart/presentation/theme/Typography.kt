package com.apnacart.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import apnacart.shared.generated.resources.Res
import apnacart.shared.generated.resources.*

@Composable
fun getPoppinsFamily() = FontFamily(
    Font(Res.font.poppins_regular, FontWeight.Normal),
    Font(Res.font.poppins_medium, FontWeight.Medium),
    Font(Res.font.poppins_semibold, FontWeight.SemiBold),
    Font(Res.font.poppins_bold, FontWeight.Bold),
)

@Composable
fun getInterFamily() = FontFamily(
    Font(Res.font.inter_light, FontWeight.Light),
    Font(Res.font.inter_regular, FontWeight.Normal),
    Font(Res.font.inter_medium, FontWeight.Medium),
    Font(Res.font.inter_semibold, FontWeight.SemiBold),
    Font(Res.font.inter_bold, FontWeight.Bold),
)

@Composable
fun getApnaCartTypography(): Typography {
    val poppins = getPoppinsFamily()
    val inter = getInterFamily()

    return Typography(
        displayLarge  = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Bold, fontSize = 32.sp, letterSpacing = (-0.5).sp),
        displayMedium = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Bold, fontSize = 28.sp),
        headlineLarge = TextStyle(fontFamily = poppins, fontWeight = FontWeight.SemiBold, fontSize = 24.sp),
        headlineMedium= TextStyle(fontFamily = poppins, fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
        titleLarge    = TextStyle(fontFamily = poppins, fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
        titleMedium   = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Medium, fontSize = 16.sp),
        bodyLarge     = TextStyle(fontFamily = inter,   fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
        bodyMedium    = TextStyle(fontFamily = inter,   fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
        bodySmall     = TextStyle(fontFamily = inter,   fontWeight = FontWeight.Light, fontSize = 12.sp, lineHeight = 18.sp),
        labelLarge    = TextStyle(fontFamily = inter,   fontWeight = FontWeight.Medium, fontSize = 14.sp),
        labelMedium   = TextStyle(fontFamily = inter,   fontWeight = FontWeight.Medium, fontSize = 12.sp),
        labelSmall    = TextStyle(fontFamily = inter,   fontWeight = FontWeight.Medium, fontSize = 10.sp, letterSpacing = 0.5.sp),
    )
}
