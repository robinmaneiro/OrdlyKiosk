package uk.co.softlantic.orderkiosk.menu.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import uk.co.softlantic.orderkiosk.R
import uk.co.softlantic.orderkiosk.menu.model.MenuCategory
import uk.co.softlantic.orderkiosk.ui.theme.Aquamarine40

@Composable
fun PromoCategoryCard(
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .size(240.dp, 80.dp)
//            .clickable { onCategoryClick.invoke(category.id) }
        ,
        border = BorderStroke(
            1.dp,
            Aquamarine40
        ),
        colors = CardDefaults.cardColors().copy(
            containerColor = Aquamarine40
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        ) {
            Text(
                text = "Promotions",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PromoCategoryCardPreview() {
    PromoCategoryCard(
        {}
    )
}