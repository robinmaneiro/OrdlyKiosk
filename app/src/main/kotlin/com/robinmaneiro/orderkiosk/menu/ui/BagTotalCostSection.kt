package com.robinmaneiro.orderkiosk.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.ui.theme.SandyBrown40
import com.robinmaneiro.orderkiosk.util.noRippleClickable

@Composable
fun BagTotalCostSection(
    formattedTotalCost: String,
    itemCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .widthIn(min = 200.dp)
            .shadow(
                elevation = 5.dp,
                ambientColor = Aquamarine40,
                spotColor = Aquamarine40,
                shape = RoundedCornerShape(percent = 50)
            )
            .background(White, shape = RoundedCornerShape(percent = 50))
            .border(2.dp, Aquamarine40, shape = RoundedCornerShape(percent = 50))
            .noRippleClickable(onClick)
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = formattedTotalCost,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 40.sp)
            )
            Box(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icn_meal_bag),
                    contentDescription = "Vector icon",
                    tint = Aquamarine40,
                    modifier = Modifier.size(60.dp)
                )
                Text(
                    itemCount.toString(),
                    color = White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(22.dp)
                        .background(color = SandyBrown40, shape = CircleShape)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}

//@Preview( TODO: Restore preview
//    showBackground = true
//)
//@Composable
//fun BottomSectionPreview() {
//    BagTotalCostSection(listOf(), {})
//}
