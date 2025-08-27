package com.robinmaneiro.orderkiosk.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.robinmaneiro.orderkiosk.ui.buttons.PrimaryBtnStyle
import com.robinmaneiro.orderkiosk.ui.buttons.PrimaryButton

@Composable
fun BottomSection(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(150.dp)
            .fillMaxSize()
            .shadow(
                elevation = 8.dp,
                clip = true,
                ambientColor = Color.Black.copy(alpha = 0.2f),
                spotColor = Color.Black.copy(alpha = 0.2f)
            )
    ) {
        PrimaryButton(
            "Cancel Order",
            primaryButtonStyle = PrimaryBtnStyle.Red,
            onClickListener = {
                // Cancel order here
            }
        )

        // TODO: Container for orderables here
        Spacer(
            modifier = Modifier
                .padding(310.dp)
        )

        PrimaryButton(
            buttonText = "Complete order",
            primaryButtonStyle = PrimaryBtnStyle.Yellow,
            onClickListener = {
                // Complete order logic
            }
        )
    }
}