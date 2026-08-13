package com.robinmaneiro.ordly.kiosk.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robinmaneiro.ordly.kiosk.R
import com.robinmaneiro.ordly.kiosk.util.extensions.noRippleClickable

@Composable
fun RotatingArrow(
    modifier: Modifier = Modifier,
    durationMillis: Int = 300,
    toggledRotation: Float = 180f,
    initialRotated: Boolean = false,
    onToggle: (() -> Unit)? = null
) {
    var rotated by remember { mutableStateOf(initialRotated) }
    val target = if (rotated) toggledRotation else 0f
    val rotation by animateFloatAsState(
        targetValue = target,
        animationSpec = tween(durationMillis)
    )

    val expandedDesc = stringResource(R.string.cd_expanded)
    val collapsedDesc = stringResource(R.string.cd_collapsed)
    val contentDesc = if (rotated) expandedDesc else collapsedDesc

    Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, // TODO: Replace icon with excessive padding
        contentDescription = contentDesc,
        modifier = modifier
            .size(60.dp)
            .rotate(rotation)
            .noRippleClickable {
                rotated = !rotated
                onToggle?.invoke()
            }
            .semantics { contentDescription = contentDesc }
    )
}

@Preview
@Composable
private fun PreviewRotatingArrow() {
    RotatingArrow {}
}
