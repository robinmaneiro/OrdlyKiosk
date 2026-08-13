package com.robinmaneiro.ordly.kiosk.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val SegmentColors = listOf(
    Color(0xFFE53935), // Red
    Color(0xFFFDD835), // Yellow
    Color(0xFF43A047), // Green
    Color(0xFF1E88E5), // Blue
    Color(0xFFE91E63), // Pink
    Color(0xFFFF6F00), // Amber
    Color(0xFF6A1B9A), // Purple
    Color(0xFF00838F), // Teal
)

@Composable
fun RouletteWheel(
    rotation: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    wheelSize: Dp = 200.dp
) {
    val segmentCount = SegmentColors.size
    val sweepAngle = 360f / segmentCount

    Box(
        modifier = modifier
            .size(wheelSize)
            .graphicsLayer { rotationZ = rotation }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            // Draw segments
            SegmentColors.forEachIndexed { index, color ->
                drawArc(
                    color = color,
                    startAngle = index * sweepAngle - 90f,
                    sweepAngle = sweepAngle,
                    useCenter = true
                )
            }

            // White divider lines from center to edge
            repeat(segmentCount) { index ->
                val angleRad = (index * sweepAngle - 90.0) * PI / 180.0
                drawLine(
                    color = Color.White,
                    start = center,
                    end = Offset(
                        center.x + radius * cos(angleRad).toFloat(),
                        center.y + radius * sin(angleRad).toFloat()
                    ),
                    strokeWidth = 4f
                )
            }

            // Outer border ring
            drawCircle(
                color = Color.White,
                radius = radius,
                style = Stroke(width = 6f)
            )

            // Center knob
            drawCircle(
                color = Color.White,
                radius = radius * 0.08f,
                center = center
            )
        }
    }
}
