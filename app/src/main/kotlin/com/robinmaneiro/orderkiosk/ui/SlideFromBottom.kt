package com.robinmaneiro.orderkiosk.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun SlideFromBottom(
    visible: Boolean,
    modifier: Modifier = Modifier,
    animationDuration: Int = 700,
    bottomPadding: Dp = 30.dp,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val screenHeightPx = with(density) { LocalConfiguration.current.screenHeightDp.dp.toPx() }
    val bottomPaddingPx = with(density) { bottomPadding.toPx() }

    val insetBottomDp = with(density) { windowInsets.getBottom(density).toDp() }
    val bottomInsetPx = with(density) { insetBottomDp.toPx() }

    var contentHeightPx by remember { mutableFloatStateOf(0f) }
    val offsetY = remember { Animatable(screenHeightPx) }

    LaunchedEffect(visible, contentHeightPx) {
        if (contentHeightPx <= 0f) return@LaunchedEffect
        val targetY = screenHeightPx - contentHeightPx - bottomInsetPx - bottomPaddingPx
        if (visible) {
            // Ensure starting off-screen (in case of recomposition)
            offsetY.snapTo(screenHeightPx)
            offsetY.animateTo(targetY, animationSpec = tween(durationMillis = animationDuration))
        } else {
            offsetY.animateTo(screenHeightPx, animationSpec = tween(durationMillis = animationDuration))
        }
    }

    Box(
        modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .onGloballyPositioned { coords ->
                    contentHeightPx = coords.size.height.toFloat()
                }
                .offset {
                    IntOffset(x = 0, y = offsetY.value.roundToInt())
                }
                .wrapContentHeight()
        ) {
            content()
        }
    }
}
