package com.robinmaneiro.orderkiosk.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
            .background(Color.LightGray)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxSize()
    ) {
        PrimaryButton(
            "Cancel Order",
            primaryButtonStyle = PrimaryBtnStyle.Red,
            onClickListener = {
                // Cancel order here
            }
        )

        // List of orderables here
        Spacer(
            modifier = Modifier
                .background(Color.Green)
                .padding(310.dp)
        )

        PrimaryButton(
            buttonText = "Complete order",
            primaryButtonStyle = PrimaryBtnStyle.Yellow,
            onClickListener = {

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomSectionPreview() {
    BottomSection()
}