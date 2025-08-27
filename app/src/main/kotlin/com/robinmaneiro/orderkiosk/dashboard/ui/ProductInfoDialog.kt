package com.robinmaneiro.orderkiosk.dashboard.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.robinmaneiro.orderkiosk.dashboard.model.MenuProductExpanded
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.ui.theme.SandyBrown40
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProductOverlay(
    product: MenuProductExpanded,
    onDismiss: () -> Unit,
    onBuy: (MenuProductExpanded) -> Unit = {}
) {
    BackHandler(enabled = true) { onDismiss() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.25f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(48.dp)
                .fillMaxSize()
                .clickable(enabled = false) { },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(text = product.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(6.dp))

                    val formattedPrice = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(product.price)
                    Text(text = formattedPrice, style = MaterialTheme.typography.titleSmall)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }


                Spacer(modifier = Modifier.height(500.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text(
                            "Close",
                            color = SandyBrown40
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        colors = buttonColors(
                            containerColor = Aquamarine40,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(5.dp),
                        onClick = { onBuy(product) }
                    ) {
                        Text("Buy")
                    }
                }
            }
        }
    }
}