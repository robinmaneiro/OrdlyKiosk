package com.robinmaneiro.orderkiosk.dashboard.ui

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.dashboard.model.MenuProductExpanded
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.util.boxShadow
import com.robinmaneiro.orderkiosk.util.noRippleClickable

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BottomSection(
    bagProducts: List<MenuProductExpanded>,
    onSecondaryButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .boxShadow()
    ) {
        Row(
            modifier = modifier
                .background(White)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = bagProducts.sumOf { it.price }.toString(),
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 48.sp),
                modifier = Modifier.noRippleClickable(onSecondaryButtonClicked)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.icn_meal_bag),
                    contentDescription = "Vector icon",
                    tint = Aquamarine40,
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}

@Preview(
    device = PIXEL_TABLET,
    showBackground = true
)
@Composable
fun BottomSectionPreview() {
    BottomSection(listOf(), {})
}