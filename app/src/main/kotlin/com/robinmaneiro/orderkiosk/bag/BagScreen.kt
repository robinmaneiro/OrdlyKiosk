package com.robinmaneiro.orderkiosk.bag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.robinmaneiro.orderkiosk.bag.model.BagItem
import com.robinmaneiro.orderkiosk.bag.ui.BagItemRow
import com.robinmaneiro.orderkiosk.ui.CustomDialog
import com.robinmaneiro.orderkiosk.ui.KiLoadingSpinner
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar
import com.robinmaneiro.orderkiosk.ui.theme.Iceberg
import com.robinmaneiro.orderkiosk.util.fadingEdge
import org.koin.androidx.compose.koinViewModel

@Composable
fun BagScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<BagViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var itemToRemove by remember { mutableStateOf<BagItem?>(null) }
    var showClearBagDialog by remember { mutableStateOf(false)}

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
                            if (bagItemData.quantity == 1) {
                                itemToRemove = bagItemData
                            } else {
                                viewModel.decreaseQuantity(bagItemData)
                            }
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
                    .background(Iceberg)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    "${uiState.itemCount} Items",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                Column(
                    Modifier
                        .fillMaxHeight()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Total"
                    )

                    Text(
                        text = uiState.formattedTotalCost
                    )
                }

                Column(
                    Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd)
                ) {
                    Button(
                        onClick = {}
                    ) {
                        Text(
                            "Order Now"
                        )
                    }

                    TextButton(
                        onClick = {
                            showClearBagDialog = true
                        }
                    ) {
                        Text(
                            "Cancel Order"
                        )
                    }
                }
            }
        }
    }

    itemToRemove?.let { bagItem ->
        CustomDialog(
            title = "Are you sure? ",
            body = "Do you really want to remove this item from the bag?",
            primaryButtonLabel = "Remove item",
            secondaryButtonLabel = "Cancel",
            onPrimaryButtonClicked = {
                viewModel.removeItem(bagItem)
                itemToRemove = null
            },
            onSecondaryButtonClicked = {
                itemToRemove = null
            }
        )
    }

    if (showClearBagDialog) {
        CustomDialog(
            title = "Are you sure?",
            body = "This action will remove ALL items from the bag",
            primaryButtonLabel = "Clear Basket",
            secondaryButtonLabel = "Cancel",
            onPrimaryButtonClicked = {
                viewModel.removeAllItems()
                showClearBagDialog = false
            },
            onSecondaryButtonClicked = {
                showClearBagDialog = false
            }
        )
    }

    if (uiState.isLoading) {
        KiLoadingSpinner()
    }
}
