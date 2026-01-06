package uk.co.softlantic.orderkiosk.menu.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import uk.co.softlantic.orderkiosk.R
import uk.co.softlantic.orderkiosk.menu.model.MenuProduct
import uk.co.softlantic.orderkiosk.util.extensions.fadingEdge
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
    promotionMessage: String? = null
) {
    Card(
        modifier = modifier
            .height(300.dp)
            .clickable {
                onProductClick.invoke(productId)
            },
        border = BorderStroke(1.dp, Color.DarkGray),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.item_test_big_mac)
                    .build(),
                contentDescription = null
            )

            Text(
                text = productTitle,
                fontWeight = FontWeight.Bold,
                minLines = 2,
                maxLines = 2
            )

            Text(
                text = productDescription,
                fontWeight = FontWeight.Thin,
                color = Color.DarkGray,
                minLines = 2,
                maxLines = 2
            )

            Text(
                text = productPrice
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
