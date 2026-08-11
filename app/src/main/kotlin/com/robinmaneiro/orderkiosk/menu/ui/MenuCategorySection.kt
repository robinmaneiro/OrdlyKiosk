package com.robinmaneiro.orderkiosk.menu.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.robinmaneiro.orderkiosk.menu.model.MenuCategory
import com.robinmaneiro.orderkiosk.util.extensions.fadingEdge
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MenuCategorySection(
    menuCategories: ImmutableList<MenuCategory>,
    onCategoryClick: (categoryId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(top = 16.dp)
    ) {
        PromoCategoryCard({})

        Spacer(Modifier.height(10.dp))

        LazyColumn(
            modifier = modifier
                .fadingEdge(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 16.dp
            ),
        ) {
            items(menuCategories) {
                MenuCard(
                    category = it,
                    onCategoryClick = onCategoryClick
                )
            }
        }
    }
}

@Composable
fun MenuCard(
    category: MenuCategory,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .size(300.dp, 80.dp)
            .clickable { onCategoryClick.invoke(category.id) },
        border = BorderStroke(
            if (category.isDefault) 2.dp else 1.dp,
            if (category.isDefault) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
        ) {
            AsyncImage(
                model = category.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                fallback = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
            )

            Text(
                text = category.categoryName,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MenuCardPreview() {
    MenuCard(MenuCategory("123", "Burgers", false), {})
}
