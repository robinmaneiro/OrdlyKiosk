package com.robinmaneiro.ordly.kiosk.menu.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.robinmaneiro.ordly.kiosk.menu.model.MenuProduct
import com.robinmaneiro.ordly.kiosk.util.extensions.fadingEdge
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MenuItemsSection(
    menuProducts: ImmutableList<MenuProduct>,
    lazyGridState: LazyGridState,
    onProductClick: (productId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier
            .fadingEdge(),
        columns = GridCells.Adaptive(200.dp),
        state = lazyGridState,
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 100.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(menuProducts) {
            ProductCard(
                productTitle = it.title,
                productPrice = it.formattedPrice,
                productId = it.productId,
                productDescription = it.description,
                imageUrl = it.imageUrl,
                onProductClick = onProductClick
            )
        }
    }
}

@Composable
private fun ProductCard(
    productTitle: String,
    productPrice: String,
    productId: String,
    productDescription: String,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    promotionMessage: String? = null
) {
    Card(
        modifier = modifier
            .height(300.dp)
            .clickable {
                onProductClick.invoke(productId)
            },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.large
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            fallback = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
        )

        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = productTitle,
                style = MaterialTheme.typography.titleSmall,
                minLines = 2,
                maxLines = 2
            )

            Text(
                text = productDescription,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                minLines = 2,
                maxLines = 2
            )

            Text(
                text = productPrice,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    ProductCard(
        promotionMessage = "Back again",
        productTitle = "McChicken Classic",
        productDescription = "It's so delicious",
        productId = "12",
        productPrice = "£5.49",
        onProductClick = {}
    )
}
