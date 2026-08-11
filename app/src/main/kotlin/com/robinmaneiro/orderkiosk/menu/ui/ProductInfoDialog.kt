package com.robinmaneiro.orderkiosk.menu.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.menu.MenuViewModel
import com.robinmaneiro.orderkiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.orderkiosk.util.extensions.getFormattedPrice
import com.robinmaneiro.orderkiosk.util.extensions.noRippleClickable

@Composable
fun ProductOverlay(
    product: MenuItemExpanded,
    onDismiss: () -> Unit,
    onUiEvent: (MenuViewModel.UiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var quantity by remember { mutableIntStateOf(1) }

    BackHandler(enabled = true) { onDismiss() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.40f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(horizontal = 80.dp, vertical = 32.dp)
                .clickable(enabled = false) { },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                ProductImagePane(
                    imageUrl = product.imageUrl,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )

                ProductDetailsPane(
                    product = product,
                    quantity = quantity,
                    onQuantityChange = { quantity = it },
                    onAddToBasket = {
                        onUiEvent(MenuViewModel.UiEvent.AddToBasket(product.productId, quantity))
                    },
                    onDismiss = onDismiss,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
        }
    }
}

@Composable
private fun ProductImagePane(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
        error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
        fallback = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier.clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
    )
}

@Composable
private fun ProductDetailsPane(
    product: MenuItemExpanded,
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    onAddToBasket: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.btn_close),
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = product.formattedPrice,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(20.dp))

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            Spacer(Modifier.height(16.dp))

            InfoSection(
                title = stringResource(R.string.product_nutritional_info),
                body = stringResource(R.string.product_nutritional_placeholder)
            )

            Spacer(Modifier.height(16.dp))

            InfoSection(
                title = stringResource(R.string.product_allergens),
                body = stringResource(R.string.product_allergens_placeholder)
            )
        }

        Spacer(Modifier.height(16.dp))

        QuantitySelector(
            quantity = quantity,
            onQuantityChange = onQuantityChange,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(12.dp))

        AddToBasketButton(
            formattedTotal = calculateFormattedTotal(product, quantity),
            onClick = onAddToBasket,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun InfoSection(
    title: String,
    body: String,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.icn_filled_circle_minus),
            contentDescription = stringResource(R.string.cd_decrease_quantity),
            tint = if (quantity > 1) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.outlineVariant,
            modifier = Modifier
                .size(48.dp)
                .noRippleClickable {
                    if (quantity > 1) onQuantityChange(quantity - 1)
                }
        )

        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(48.dp)
        )

        Icon(
            painter = painterResource(R.drawable.icn_filled_circle_plus),
            contentDescription = stringResource(R.string.cd_increase_quantity),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(48.dp)
                .noRippleClickable {
                    onQuantityChange(quantity + 1)
                }
        )
    }
}

@Composable
private fun AddToBasketButton(
    formattedTotal: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = stringResource(R.string.product_add_to_basket_with_price, formattedTotal),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

private fun calculateFormattedTotal(product: MenuItemExpanded, quantity: Int): String {
    val total = product.price * quantity
    return total.getFormattedPrice(product.currencyCode)
}
