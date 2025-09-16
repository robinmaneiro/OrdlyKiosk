package com.robinmaneiro.orderkiosk.bag.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.bag.model.BagItem
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.util.PixelTabletPreview

@Composable
fun BagItemRow(
    bagItem: BagItem,
    onPlusClick: (String) -> Unit,
    onMinusClick: (String) -> Unit
) {
    Card(
        Modifier.height(100.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.75f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row {

                Column(
                    Modifier
                        .fillMaxHeight()
                        .width(50.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icn_filled_circle_plus),
                        contentDescription = "Delete",
                        tint = Aquamarine40,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onPlusClick.invoke("")
                            }
                    )

                    Text(
                        text = bagItem.quantity.toString()
                    )

                    Icon(
                        painter = painterResource(
                            if (bagItem.quantity == 1) R.drawable.icn_bin else R.drawable.icn_filled_circle_minus
                        ),
                        contentDescription = "Delete",
                        tint = Aquamarine40,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onMinusClick.invoke("")
                            }
                    )
                }
                AsyncImage(
                    modifier = Modifier.size(100.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.item_test_big_mac)
                        .build(),
                    contentDescription = null
                )

                Spacer(Modifier.width(16.dp))

                Column {
                    Text(
                        text = bagItem.title,
                        fontWeight = FontWeight.Bold
                    )
                    Text(bagItem.description)
                }
            }
        }
    }
}

@PixelTabletPreview
@Composable
fun BagItemPreview() {
    BagItemRow(
        BagItem(
            itemId = "",
            productId = "",
            quantity = 1,
            title = "Big Mac",
            description = "It's just bloody delicious",
            price = 9.95
        ),
        onPlusClick = {},
        onMinusClick = {}
    )
}