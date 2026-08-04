package com.robinmaneiro.orderkiosk.bag.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.bag.model.BagItem
import com.robinmaneiro.orderkiosk.bag.model.ItemPrice
import com.robinmaneiro.orderkiosk.bag.model.PriceData
import com.robinmaneiro.orderkiosk.bag.model.Tax
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import com.robinmaneiro.orderkiosk.util.extensions.noRippleClickable

@Composable
fun BagItemRow(
    bagItem: BagItem,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 0.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .size(600.dp, height = 100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = bagItem.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                fallback = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
            )

            Spacer(Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.height(80.dp)
            ) {
                Text(
                    text = bagItem.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = bagItem.description,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Text(
            bagItem.formattedUnitPrice,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.width(150.dp)
        )

        Row(
            Modifier
                .width(150.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = painterResource(
                    if (bagItem.quantity == 1) R.drawable.icn_bin else R.drawable.icn_filled_circle_minus
                ),
                contentDescription = stringResource(R.string.cd_delete),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(48.dp)
                    .noRippleClickable {
                        onMinusClick.invoke()
                    }
            )

            Text(
                text = bagItem.quantity.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.width(50.dp)
            )

            Icon(
                painter = painterResource(R.drawable.icn_filled_circle_plus),
                contentDescription = stringResource(R.string.cd_delete),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(48.dp)
                    .noRippleClickable {
                        onPlusClick.invoke()
                    }
            )
        }

        Text(
            text = bagItem.formattedPrice,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.width(150.dp),
            textAlign = TextAlign.Center
        )
    }
}

@PreviewPixelTablet
@Composable
private fun BagItemPreview() {
    val priceData = PriceData(
        currencyCode = "GBP",
        _withTax = 99500,
        _withoutTax = 99500,
        tax = Tax()
    )
    BagItemRow(
        BagItem(
            itemId = "",
            productId = "",
            quantity = 1,
            title = "Big Mac",
            description = "It's just bloody delicious, you won't believe it when you try it. This is something else to occupy the second line and forcing the text to",
            _itemPrice = ItemPrice(priceData, priceData)
        ),
        onPlusClick = {},
        onMinusClick = {}
    )
}
