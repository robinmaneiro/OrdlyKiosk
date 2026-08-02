package com.robinmaneiro.orderkiosk.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val ColorScheme = lightColorScheme(
    primary = BrandAmber,
    onPrimary = Color.White,
    primaryContainer = BrandAmberContainer,
    onPrimaryContainer = OnBrandAmberContainer,
    secondary = PromoTeal,
    onSecondary = Color.White,
    secondaryContainer = PromoTealContainer,
    onSecondaryContainer = OnPromoTealContainer,
    background = WarmCream,
    onBackground = WarmNearBlack,
    surface = WarmSurface,
    onSurface = WarmNearBlack,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = WarmMuted,
    outline = WarmBorder,
    outlineVariant = WarmBorder,
)

private val KioskShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

@Composable
fun OrderKioskTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        shapes = KioskShapes,
        content = content
    )
}
