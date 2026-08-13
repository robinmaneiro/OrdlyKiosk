package com.robinmaneiro.ordly.kiosk.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private const val SHIMMER_DURATION_MS = 1200
private const val SHIMMER_TARGET_VALUE = 1000f
private const val SHIMMER_OFFSET = 500f

@Suppress("MagicNumber")
private val ShimmerColorBase = Color(0xFFEDE9E2)

@Suppress("MagicNumber")
private val ShimmerColorHighlight = Color(0xFFF7F5F0)

@Composable
fun Modifier.shimmerEffect(): Modifier {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = SHIMMER_TARGET_VALUE,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = SHIMMER_DURATION_MS, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )

    val shimmerColors = listOf(ShimmerColorBase, ShimmerColorHighlight, ShimmerColorBase)

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - SHIMMER_OFFSET, translateAnim - SHIMMER_OFFSET),
        end = Offset(translateAnim, translateAnim)
    )

    return this.background(brush)
}
