package uk.co.softlantic.orderkiosk.util.extensions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import uk.co.softlantic.orderkiosk.ui.theme.Iceberg

@Suppress("ModifierComposed") // TODO: Refactor to Modifier.Node when time permits.
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) {
        onClick()
    }
}

fun Modifier.boxShadow() = shadow(
    elevation = 20.dp,
    ambientColor = Color(0x80000000),
    spotColor = Color(0x80000000)
).background(Color.White, shape = RoundedCornerShape(6.dp))

fun Modifier.fadingEdge(
    brush: Brush = Brush.verticalGradient(
        0f to Color.Transparent,
        0.05f to Iceberg,
        0.95f to Iceberg,
        1.0f to Color.Transparent,
    )
) = graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }
