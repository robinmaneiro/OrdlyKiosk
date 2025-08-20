package com.robinmaneiro.orderkiosk.dashboard.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.menu.model.MenuItem

@Composable
fun ProductsSection(
    modifier: Modifier = Modifier,
    menuItems: List<MenuItem>
) {
    LazyVerticalGrid(
        modifier = Modifier,
        columns = GridCells.Adaptive(180.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(menuItems) {
            ProductCard(
                productTitle = it.title,
                productPrice = it.price.toString(),
                productDescription = it.description
            )
        }
    }
}

@Composable
private fun ProductCard(
    productTitle: String,
    productPrice: String,
    productDescription: String,
    modifier: Modifier = Modifier,
    promotionMessage: String? = null
) {
    Card(
        modifier = modifier,
        border = BorderStroke(1.dp, Color.DarkGray),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    // Here to open the product view
                }
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.big_mac)
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
        productPrice = "£5.49"
    )
}
