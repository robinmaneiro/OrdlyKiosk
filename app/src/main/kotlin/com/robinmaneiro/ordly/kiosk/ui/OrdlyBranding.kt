package com.robinmaneiro.ordly.kiosk.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.robinmaneiro.ordly.kiosk.R

private const val LOGO_CORNER_RATIO = 4

@Composable
fun OrdlyBranding(
    logoSize: Dp,
    appNameColor: Color,
    brandNameColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.icn_ordly_logo),
            contentDescription = stringResource(R.string.cd_app_logo),
            modifier = Modifier
                .size(logoSize)
                .clip(RoundedCornerShape(logoSize / LOGO_CORNER_RATIO))
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleMedium,
                color = appNameColor
            )
            Text(
                text = stringResource(R.string.brand_name),
                style = MaterialTheme.typography.labelSmall,
                color = brandNameColor
            )
        }
    }
}
