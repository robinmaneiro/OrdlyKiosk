package com.robinmaneiro.orderkiosk.bag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import com.robinmaneiro.orderkiosk.NavigationEvent
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.bag.model.BagItem
import com.robinmaneiro.orderkiosk.bag.ui.BagItemRow
import com.robinmaneiro.orderkiosk.ui.CustomDialog
import com.robinmaneiro.orderkiosk.ui.KiLoadingSpinner
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.ui.theme.SandyBrown40
import com.robinmaneiro.orderkiosk.util.extensions.fadingEdge

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
            SimpleTopBar(title = stringResource(R.string.screen_title_bag), onBack = {
                navigationEvent.invoke(NavigationEvent.NavigateUp)
            })
        }
    ) {
        Column(
            Modifier.padding(it)
        ) {
            ItemsSection(
                bagItems = uiState.bagItems,
                onHandleUiEvent = viewModel::onHandleEvent,
                onRemoveTapped = { itemToRemove = it }
            )

            TopShadow()

            BottomSection(
                modifier = Modifier.weight(.18F),
                showClearBagDialog = { showClearBagDialog = true },
                navigationEvent = navigationEvent,
                itemCount = uiState.itemCount,
                formattedTotalCost = uiState.formattedTotalCost
            )
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
            title = stringResource(R.string.dialog_remove_item_title),
            body = stringResource(R.string.dialog_remove_item_body),
            primaryButtonLabelToAct = stringResource(R.string.btn_remove_item) to primaryButtonAction,
            secondaryButtonLabelToAct = stringResource(R.string.btn_cancel) to secondaryButtonAction,
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
            title = stringResource(R.string.dialog_remove_item_title),
            body = stringResource(R.string.dialog_clear_bag_body),
            primaryButtonLabelToAct = stringResource(R.string.btn_clear_basket) to primaryButtonAction,
            secondaryButtonLabelToAct = stringResource(R.string.btn_cancel) to secondaryButtonAction,
        )
    }

    if (uiState.isLoading) {
        KiLoadingSpinner()
    }
}

@Composable
fun ColumnScope.ItemsSection(
    modifier: Modifier = Modifier,
    bagItems: ImmutableList<BagItem>,
    onHandleUiEvent: (BagViewModel.UiEvent) -> Unit,
    onRemoveTapped: (BagItem) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fadingEdge()
            .fillMaxWidth()
            .weight(weight = .82F),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        itemsIndexed(bagItems) { index, bagItemData ->
            BagItemRow(
                bagItem = bagItemData,
                onPlusClick = {
                    onHandleUiEvent.invoke(BagViewModel.UiEvent.IncreaseQuantity(bagItemData))
                },
                onMinusClick = {
                    if (bagItemData.quantity == 1) {
                        onRemoveTapped.invoke(bagItemData)
                    } else {
                        onHandleUiEvent.invoke(BagViewModel.UiEvent.DecreaseQuantity(bagItemData))
                    }
                }
            )
            if (index < bagItems.lastIndex) {
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
}

@Composable
fun TopShadow(alpha: Float = 0.1f, height: Dp = 8.dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = alpha),
                    )
                )
            )
    )
}

@Composable
fun BottomSection(
    modifier: Modifier = Modifier,
    showClearBagDialog: (Boolean) -> Unit,
    navigationEvent: (NavigationEvent) -> Unit,
    itemCount: Int,
    formattedTotalCost: String
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(end = 20.dp, start = 20.dp, top = 10.dp)
    ) {
        Text(
            text = stringResource(R.string.bag_item_count, itemCount),
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
                text = stringResource(R.string.bag_total)
            )

            Text(
                text = formattedTotalCost
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.size(150.dp, 50.dp),
                colors = buttonColors(
                    containerColor = Aquamarine40,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(5.dp),
                onClick = { navigationEvent.invoke(NavigationEvent.NavigateToDestination(Screens.CheckoutScreen.route)) }
            ) {
                Text(
                    text = stringResource(R.string.btn_order_now),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            TextButton(
                onClick = {
                    showClearBagDialog.invoke(true)
                }
            ) {
                Text(
                    text = stringResource(R.string.btn_cancel_order),
                    color = SandyBrown40,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@PreviewPixelTablet
@Composable
fun BottomSectionPreview() {
    BottomSection(
        modifier = Modifier.height(100.dp),
        showClearBagDialog = {},
        navigationEvent = {},
        itemCount = 12,
        formattedTotalCost = "£45.95",
    )
}
