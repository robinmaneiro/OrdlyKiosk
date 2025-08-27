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
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.ui.theme.SandyBrown40

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
    data object Yellow : PrimaryBtnStyle(Aquamarine40, Color.White)
    data object Red : PrimaryBtnStyle(SandyBrown40, Color.White)
}