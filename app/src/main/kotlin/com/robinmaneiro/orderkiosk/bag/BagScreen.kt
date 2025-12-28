package com.robinmaneiro.orderkiosk.bag

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.robinmaneiro.orderkiosk.NavigationEvent
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.bag.model.BagItem
import com.robinmaneiro.orderkiosk.bag.ui.BagItemRow
import com.robinmaneiro.orderkiosk.ui.CustomDialog
import com.robinmaneiro.orderkiosk.ui.KiLoadingSpinner
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar
import com.robinmaneiro.orderkiosk.ui.theme.Iceberg
import com.robinmaneiro.orderkiosk.util.extensions.fadingEdge
import org.koin.androidx.compose.koinViewModel

@Composable
fun BagScreen(
    navigationEvent: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<BagViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var itemToRemove by remember { mutableStateOf<BagItem?>(null) }
    var showClearBagDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        viewModel.actions.collect { action ->
            when (action) {
                is BagViewModel.Actions.NavigateBack -> navigationEvent.invoke(NavigationEvent.NavigateUp)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(title = "Bag", onBack = {
                navigationEvent.invoke(NavigationEvent.NavigateUp)
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
                    .weight(weight = .85F),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                itemsIndexed(uiState.bagItems) { index, bagItemData ->
                    BagItemRow(
                        bagItem = bagItemData,
                        onPlusClick = {
                            viewModel.onHandleEvent(BagViewModel.UiEvent.IncreaseQuantity(bagItemData))
                        },
                        onMinusClick = {
                            if (bagItemData.quantity == 1) {
                                itemToRemove = bagItemData
                            } else {
                                viewModel.onHandleEvent(BagViewModel.UiEvent.DecreaseQuantity(bagItemData))
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
                    .weight(weight = .15f)
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
                            modifier = Modifier.clickable {
                                navigationEvent.invoke(NavigationEvent.NavigateToDestination(Screens.CheckoutScreen.route))
                            },
                            text = "Order Now"
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
        val primaryButtonAction = {
            viewModel.onHandleEvent(BagViewModel.UiEvent.RemoveItem(bagItem))
            itemToRemove = null
        }
        val secondaryButtonAction = {
            itemToRemove = null
        }
        CustomDialog(
            title = "Are you sure? ",
            body = "Do you really want to remove this item from the bag?",
            primaryButtonLabelToAct = "Remove item" to primaryButtonAction,
            secondaryButtonLabelToAct = "Cancel" to secondaryButtonAction,
        )
    }

    if (showClearBagDialog) {
        val primaryButtonAction = {
            viewModel.onHandleEvent(BagViewModel.UiEvent.RemoveAllItems)
            showClearBagDialog = false
        }
        val secondaryButtonAction = {
            showClearBagDialog = false
        }
        CustomDialog(
            title = "Are you sure?",
            body = "This action will remove ALL items from the bag",
            primaryButtonLabelToAct = "Clear Basket" to primaryButtonAction,
            secondaryButtonLabelToAct = "Cancel" to secondaryButtonAction,
        )
    }

    if (uiState.isLoading) {
        KiLoadingSpinner()
    }
}
