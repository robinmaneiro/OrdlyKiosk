package com.robinmaneiro.orderkiosk.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.robinmaneiro.orderkiosk.dashboard.model.MenuProductExpanded
import com.robinmaneiro.orderkiosk.dashboard.ui.BottomSection
import com.robinmaneiro.orderkiosk.dashboard.ui.MenuCategorySection
import com.robinmaneiro.orderkiosk.dashboard.ui.MenuItemsSection
import com.robinmaneiro.orderkiosk.dashboard.ui.ProductOverlay
import com.robinmaneiro.orderkiosk.dashboard.ui.RightOptionsPane
import com.robinmaneiro.orderkiosk.ui.KiLoadingSpinner
import com.robinmaneiro.orderkiosk.ui.theme.Iceberg
import com.robinmaneiro.orderkiosk.util.PixelTabletPreview
import com.robinmaneiro.orderkiosk.util.SlideFromBottom
import com.robinmaneiro.orderkiosk.util.SlideFromSide
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    serviceType: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<DashboardViewModel>()
    val uiState: DashboardViewModel.UiState by viewModel.uiState.collectAsStateWithLifecycle()
    var shownProduct by remember { mutableStateOf<MenuProductExpanded?>(null) }

    if (uiState.isLoading) {
        KiLoadingSpinner()
    }

    LaunchedEffect(viewModel) {
        viewModel.actions.collect { action ->
            when (action) {
                is DashboardViewModel.Actions.OpenProductInfo -> shownProduct = action.product
                // handle other actions if needed
            }
        }
    }

    DashboardScreenContent(
        viewModel = viewModel, // TODO: Follow pattern to encapsulate functions in the view model
        uiState = uiState,
        serviceType = serviceType,
        modifier = modifier
    )

    shownProduct?.let {
        ProductOverlay(
            product = it,
            onDismiss = { shownProduct = null },
            onAddToBasket = { product ->
                viewModel.addToBasket(product) // TODO: Just send up the product id
                shownProduct = null
            }
        )
    }
}

@Composable
fun DashboardScreenContent(
    viewModel: DashboardViewModel,
    uiState: DashboardViewModel.UiState,
    serviceType: String?,
    modifier: Modifier = Modifier
) {
    Box {
        Column(
            modifier
                .fillMaxSize()
                .background(Iceberg.copy(alpha = 0.2f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var showRightPane by remember {
                mutableStateOf(false)
            }

            Spacer(Modifier.height(20.dp))
            Row(
                modifier = Modifier.weight(0.9F)
            ) {
                val totalWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp }
                val menuCategoriesWidth: Dp = 300.dp
                var rightPaneMenuWidth by remember { mutableStateOf(20.dp) } // TODO: Probably better to move this to the state
                val menuItemsWidth: Dp = totalWidth - menuCategoriesWidth - rightPaneMenuWidth

                MenuCategorySection(
                    modifier = Modifier
                        .width(menuCategoriesWidth),
                    menuCategories = uiState.menuCategories,
                    onCategoryClicked = { viewModel.updateItemsOnCategorySelected(it) }
                )

                MenuItemsSection(
                    modifier = Modifier
                        .widthIn(max = menuItemsWidth),
                    menuProducts = uiState.menuProducts, onProductClicked = { viewModel.onProductClicked(it) }
                )

                SlideFromSide(
                    visible = showRightPane
                ) {
                    Box(
                        Modifier
                            .size(
                                width = 60.dp,
                                height = 500.dp
                            )
                            .background(Color.Blue)
                    )
                }

                RightOptionsPane(
                    modifier = Modifier
                        .width(menuItemsWidth)
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .background(Color.Red),
                    onArrowClicked = {
                        rightPaneMenuWidth = if (it) 100.dp else 20.dp
                    }
                )
            }
        }

        Box(Modifier.fillMaxSize()) {
            SlideFromBottom(visible = uiState.bagProducts.isNotEmpty()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                ) {
                    BottomSection(
                        uiState.bagProducts,
                        modifier = Modifier
                            .align(Alignment.Center),
                        onSecondaryButtonClicked = { viewModel.cancelOrder() }
                    )
                }
            }

            SlideFromSide(
                visible = uiState.bagProducts.isNotEmpty()
            ) {
                Box(
                    Modifier
                        .size(
                            width = 60.dp,
                            height = 500.dp
                        )
                        .background(Color.Blue)
                )
            }
        }
    }
}

@PixelTabletPreview
@Composable
fun DashboardScreenPreview() {
    DashboardScreenContent(
        koinViewModel<DashboardViewModel>(),
        DashboardViewModel.UiState(),
        "Take Away"
    )
}
