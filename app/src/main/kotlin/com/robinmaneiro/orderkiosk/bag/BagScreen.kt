package com.robinmaneiro.orderkiosk.bag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.robinmaneiro.orderkiosk.bag.ui.BagItemRow
import com.robinmaneiro.orderkiosk.ui.KiLoadingSpinner
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar
import com.robinmaneiro.orderkiosk.util.fadingEdge
import org.koin.androidx.compose.koinViewModel

@Composable
fun BagScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<BagViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SimpleTopBar(title = "Bag", onBack = {
                navController.navigateUp()
            })
        }
    ) {
        Column(
            Modifier.padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fadingEdge()
                    .fillMaxWidth()
                    .weight(.85F),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                itemsIndexed(uiState.bagItems) { index, bagItemData ->
                    BagItemRow(
                        bagItem = bagItemData,
                        onPlusClick = {
                            viewModel.increaseQuantity(bagItemData)
                        },
                        onMinusClick = {
                            viewModel.decreaseQuantity(bagItemData)
                        }
                    )
                    if (index < uiState.bagItems.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            thickness = 1.dp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Box(
                Modifier
                    .weight(.15f)
                    .fillMaxWidth()
                    .background(Color.Red)
            )
        }
    }

    if (uiState.isLoading) {
        KiLoadingSpinner()
    }
}
