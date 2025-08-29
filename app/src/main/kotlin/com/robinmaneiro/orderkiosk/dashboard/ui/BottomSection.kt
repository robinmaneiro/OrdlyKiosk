package com.robinmaneiro.orderkiosk.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robinmaneiro.orderkiosk.dashboard.model.MenuProductExpanded
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.ui.theme.SandyBrown40
import com.robinmaneiro.orderkiosk.util.PixelTabletPreview

@Composable
fun BottomSection(
    bagProducts: List<MenuProductExpanded>,
    onSecondaryButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .shadow(
            elevation = 5.dp,
            clip = false,
            ambientColor = Color.Black.copy(alpha = 0.50f),
            spotColor = Color.Black.copy(alpha = 0.50f)
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .sizeIn(minWidth = 750.dp, minHeight = 150.dp)
                .background(Color(0xFFebf8f9))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = modifier
                        .size(width = 200.dp, height = 70.dp),
                    onClick = {
                        // go to the bag
                    },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Aquamarine40,
                        contentColor = White
                    )
                ) {
                    Text(
                        text = "Go To Bag",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                TextButton(
                    onClick = onSecondaryButtonClicked
                ) {
                    Text(
                        text = "Cancel Order",
                        fontSize = 24.sp,
                        color = SandyBrown40
                    )
                }
            }
        }
    }
}

@PixelTabletPreview
@Composable
fun BottomSectionPreview() {
    BottomSection(listOf(), {})
}