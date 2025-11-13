package com.robinmaneiro.orderkiosk.menu.ui

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.menu.MenuViewModel
import com.robinmaneiro.orderkiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.ui.theme.SandyBrown40

@Composable
fun ProductOverlay(
    product: MenuItemExpanded,
    onDismiss: () -> Unit,
    onUiEvent: (MenuViewModel.UiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(enabled = true) { onDismiss() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.40f)) // TODO: Move to an independent color?
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(horizontal = 148.dp, vertical = 48.dp)
                .clickable(enabled = false) { },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column {
                    AsyncImage(
                        modifier = Modifier.size(400.dp, 400.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.item_test_big_mac)
                            .build(),
                        contentDescription = null
                    )

                    Text(text = product.title, style = MaterialTheme.typography.titleLarge)

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = product.formattedPrice, style = MaterialTheme.typography.titleMedium)
                }

                SectionButtons(
                    onDismiss = onDismiss,
                    onAddToBasket = { onUiEvent.invoke(MenuViewModel.UiEvent.AddToBasket(product.productId)) }
                )
            }
        }
    }
}

@Composable
private fun SectionButtons(
    onDismiss: () -> Unit,
    onAddToBasket: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(
            onClick = onDismiss
        ) {
            Text(
                text = "Close",
                color = SandyBrown40,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            modifier = Modifier.size(150.dp, 50.dp),
            colors = buttonColors(
                containerColor = Aquamarine40,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(5.dp),
            onClick = { onAddToBasket.invoke() }
        ) {
            Text(
                text = "Add to basket",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
