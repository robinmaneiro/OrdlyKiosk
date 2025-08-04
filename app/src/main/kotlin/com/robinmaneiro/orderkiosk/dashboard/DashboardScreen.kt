package com.robinmaneiro.orderkiosk.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.welcome.WelcomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<DashboardViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(WelcomeViewModel.UiState())

    Column {
        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .background(Color.Red)
        ) {
            Text(text = "Price: £9.95")
        }

        Row {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(start = 20.dp, top = 10.dp, bottom = 10.dp),
            ) {
                items(count = 30) {
                    MenuCard()
                }
            }
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Adaptive(180.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(count = 30) {
                    ProductCard(
                        productTitle = "McChicken Classic",
                        productPrice = "£5.49"
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .height(100.dp)
                .background(Color.Red)
        ) {
            Text(text = "Price: £9.95")
        }
    }
}

@Composable
fun MenuCard() {
    Card(
        modifier = Modifier
            .size(300.dp, 60.dp),
        border = BorderStroke(1.dp, Color.DarkGray),
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
                text = "Burgers",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ProductCard(
    productTitle: String,
    productPrice: String,
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
                .fillMaxSize()
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
                fontWeight = FontWeight.Bold
            )

            Text(
                text = productPrice
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    ProductCard(
        promotionMessage = "Back again",
        productTitle = "McChicken Classic",
        productPrice = "£5.49"
    )
}