package com.robinmaneiro.orderkiosk.util

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.BottomAppBarDefaults.windowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun SlideFromSide(
    visible: Boolean,
    animationDuration: Int = 700,
    horizontalPadding: Dp = 0.dp,          // padding from the screen edge (start or end)
    slideFromEnd: Boolean = true,           // true = slide from end (right in LTR), false = slide from start (left in LTR)
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val screenWidthPx = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val horizontalPaddingPx = with(density) { horizontalPadding.toPx() }

    // Resolve whether the sliding edge is the absolute right or left based on layout direction
    val slidingFromRight = remember(layoutDirection, slideFromEnd) {
        if (layoutDirection == LayoutDirection.Ltr) slideFromEnd else !slideFromEnd
    }

    // Get appropriate inset for the absolute edge
    val edgeInsetPx = with(density) {
        if (slidingFromRight) windowInsets.getRight(density, layoutDirection) else  windowInsets.getLeft(density, layoutDirection)
    }

    var contentWidthPx by remember { mutableFloatStateOf(0f) }
    val offsetX = remember { Animatable(if (slidingFromRight) screenWidthPx else -screenWidthPx) }

    LaunchedEffect(visible, contentWidthPx, slidingFromRight) {
        if (contentWidthPx <= 0f) return@LaunchedEffect
        val targetX = if (slidingFromRight) {
            screenWidthPx - contentWidthPx - edgeInsetPx - horizontalPaddingPx
        } else {
            // negative x so content's start aligns with padding/inset from left
            0f + edgeInsetPx + horizontalPaddingPx
        }

        if (visible) {
            offsetX.snapTo(if (slidingFromRight) screenWidthPx else -screenWidthPx)
            offsetX.animateTo(targetX, animationSpec = tween(durationMillis = animationDuration))
        } else {
            offsetX.animateTo(if (slidingFromRight) screenWidthPx else -screenWidthPx, animationSpec = tween(durationMillis = animationDuration))
        }
    }

    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .onGloballyPositioned { coords ->
                    contentWidthPx = coords.size.width.toFloat()
                }
                .offset { IntOffset(x = offsetX.value.roundToInt(), y = 0) }
                .wrapContentWidth()
        ) {
            content()
        }
    }
}
