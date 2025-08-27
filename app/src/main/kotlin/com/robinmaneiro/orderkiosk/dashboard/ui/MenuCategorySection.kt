package com.robinmaneiro.orderkiosk.dashboard.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.robinmaneiro.orderkiosk.dashboard.model.MenuCategory
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.ui.theme.Iceberg

@Composable
fun MenuCategorySection(
    menuCategories: List<MenuCategory>,
    onCategoryClicked: (categoryId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(start = 20.dp, top = 10.dp, bottom = 10.dp),
    ) {
        items(menuCategories) {
            MenuCard(
                category = it,
                onCategoryClicked = onCategoryClicked
            )
        }
    }
}

@Composable
fun MenuCard(
    category: MenuCategory,
    onCategoryClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .size(300.dp, 60.dp)
            .clickable { onCategoryClicked.invoke(category.id) },
        border = BorderStroke(if (category.isSelected) 2.dp else 1.dp,
            if (category.isSelected) Aquamarine40 else Color.DarkGray),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.big_mac)
                    .build(),
                contentDescription = null
            )

            Text(
                text = category.categoryName,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MenuCardPreview() {
    MenuCard(MenuCategory("123", "Burgers"), {})
}