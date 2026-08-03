package com.robinmaneiro.orderkiosk.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.util.extensions.noRippleClickable

@Composable
fun BagTotalCostSection(
    formattedTotalCost: String,
    itemCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .widthIn(min = 180.dp)
            .shadow(
                elevation = 16.dp,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            .noRippleClickable(onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 26.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.bag_chip_total) + " " + formattedTotalCost,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.width(30.dp))
            Box(Modifier.size(32.dp)) {
                Icon(
                    painter = painterResource(R.drawable.icn_meal_bag),
                    contentDescription = stringResource(R.string.cd_bag_icon),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = itemCount.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .size(18.dp)
                        .offset(x = 6.dp, y = (-6).dp)
                        .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimary, shape = CircleShape)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}

@Preview
@Composable
private fun BottomSectionPreview() {
    BagTotalCostSection("£42.95", 6, {})
}
