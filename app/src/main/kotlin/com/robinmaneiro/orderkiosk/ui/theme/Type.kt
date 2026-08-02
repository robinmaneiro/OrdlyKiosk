package com.robinmaneiro.orderkiosk.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.robinmaneiro.orderkiosk.R

private val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val manrope = GoogleFont("Manrope")

val ManropeFontFamily = FontFamily(
    Font(googleFont = manrope, fontProvider = fontProvider, weight = FontWeight.Normal),
    Font(googleFont = manrope, fontProvider = fontProvider, weight = FontWeight.Medium),
    Font(googleFont = manrope, fontProvider = fontProvider, weight = FontWeight.SemiBold),
    Font(googleFont = manrope, fontProvider = fontProvider, weight = FontWeight.Bold),
    Font(googleFont = manrope, fontProvider = fontProvider, weight = FontWeight.ExtraBold),
)

val Typography = Typography().run {
    copy(
        displayLarge = displayLarge.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.ExtraBold),
        displayMedium = displayMedium.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.ExtraBold),
        displaySmall = displaySmall.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold),
        headlineLarge = headlineLarge.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold),
        headlineMedium = headlineMedium.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold),
        headlineSmall = headlineSmall.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.SemiBold),
        titleLarge = titleLarge.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold),
        titleMedium = titleMedium.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.SemiBold),
        titleSmall = titleSmall.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.SemiBold),
        bodyLarge = bodyLarge.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.Normal),
        bodyMedium = bodyMedium.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.Normal),
        bodySmall = bodySmall.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.Normal),
        labelLarge = labelLarge.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.SemiBold),
        labelMedium = labelMedium.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.Medium),
        labelSmall = labelSmall.copy(fontFamily = ManropeFontFamily, fontWeight = FontWeight.Medium),
    )
}
