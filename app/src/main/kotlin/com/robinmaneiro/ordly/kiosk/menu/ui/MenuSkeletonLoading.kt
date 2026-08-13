package com.robinmaneiro.ordly.kiosk.menu.ui

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.robinmaneiro.ordly.kiosk.ui.shimmerEffect

private const val SKELETON_CATEGORY_COUNT = 5
private const val SKELETON_PRODUCT_COLUMNS = 4
private const val SKELETON_PRODUCT_ROWS = 2
private const val SKELETON_TITLE_WIDTH_FRACTION = 0.8f
private const val SKELETON_DESCRIPTION_WIDTH_FRACTION = 0.6f

@Composable
fun MenuSkeletonLoading(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        SkeletonCategoryColumn(
            modifier = Modifier.width(240.dp)
        )

        Spacer(Modifier.width(16.dp))

        SkeletonProductGrid(
            modifier = Modifier.width(900.dp)
        )
    }
}

@Composable
private fun SkeletonCategoryColumn(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Promo card placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect()
        )

        Spacer(Modifier.height(10.dp))

        repeat(SKELETON_CATEGORY_COUNT) {
            Box(
                modifier = Modifier
                    .size(300.dp, 80.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect()
            )
        }
    }
}

@Composable
private fun SkeletonProductGrid(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(SKELETON_PRODUCT_ROWS) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(SKELETON_PRODUCT_COLUMNS) {
                    SkeletonProductCard(
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun SkeletonProductCard(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Image placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(MaterialTheme.shapes.large)
                .shimmerEffect()
        )

        Spacer(Modifier.height(8.dp))

        // Title placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth(SKELETON_TITLE_WIDTH_FRACTION)
                .height(16.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .shimmerEffect()
        )

        Spacer(Modifier.height(6.dp))

        // Description placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth(SKELETON_DESCRIPTION_WIDTH_FRACTION)
                .height(12.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .shimmerEffect()
        )

        Spacer(Modifier.height(6.dp))

        // Price placeholder
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(16.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .shimmerEffect()
        )
    }
}
