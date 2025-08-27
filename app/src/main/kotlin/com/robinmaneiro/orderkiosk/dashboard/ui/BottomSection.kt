package com.robinmaneiro.orderkiosk.dashboard.ui

import  androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.robinmaneiro.orderkiosk.dashboard.model.MenuProductExpanded
import com.robinmaneiro.orderkiosk.ui.buttons.PrimaryBtnStyle
import com.robinmaneiro.orderkiosk.ui.buttons.PrimaryButton

@Composable
fun BottomSection(
    bagProducts: List<MenuProductExpanded>,
    onSecondaryButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        Modifier
            .shadow(
            elevation = 5.dp,
            clip = false,
            ambientColor = Color.Black.copy(alpha = 0.2f),
            spotColor = Color.Black.copy(alpha = 0.2f)
        ).padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .height(150.dp)
        ) {
            PrimaryButton(
                "Cancel Order",
                primaryButtonStyle = PrimaryBtnStyle.Red,
                onClickListener = onSecondaryButtonClicked
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                items(bagProducts) {
                    Text(it.description)
                }
            }

            PrimaryButton(
                buttonText = "Complete order",
                primaryButtonStyle = PrimaryBtnStyle.Yellow,
                onClickListener = {
                    // Complete order logic
                }
            )
        }
    }
}