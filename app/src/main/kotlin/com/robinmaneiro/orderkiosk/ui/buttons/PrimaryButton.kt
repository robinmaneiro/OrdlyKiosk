package com.robinmaneiro.orderkiosk.ui.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryButton(
    buttonText: String,
    primaryButtonStyle: PrimaryBtnStyle,
    onClickListener: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier
            .size(300.dp, 80.dp),
        onClick = onClickListener,
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryButtonStyle.containerColor,
            contentColor = primaryButtonStyle.backgroundColor
        )
    ) {
        Text(
            text = buttonText,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

sealed class PrimaryBtnStyle(val containerColor: Color, val backgroundColor: Color) {
    data object Yellow : PrimaryBtnStyle(Color.Yellow, Color.Black)
    data object Red : PrimaryBtnStyle(Color.Red, Color.Black)
}