package com.robinmaneiro.orderkiosk.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.robinmaneiro.orderkiosk.dashboard.model.MenuProductExpanded
import com.robinmaneiro.orderkiosk.dashboard.ui.BottomSection
import com.robinmaneiro.orderkiosk.dashboard.ui.MenuCategorySection
import com.robinmaneiro.orderkiosk.dashboard.ui.MenuItemsSection
import com.robinmaneiro.orderkiosk.dashboard.ui.ProductOverlay
import com.robinmaneiro.orderkiosk.ui.KiLoadingSpinner
import com.robinmaneiro.orderkiosk.ui.theme.Iceberg
import com.robinmaneiro.orderkiosk.util.PixelTabletPreview
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
    modifier: Modifier
) {
    Box {
        Column(
            modifier
                .fillMaxSize()
                .background(Iceberg.copy(alpha = 0.2f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.weight(0.9F)
            ) {
                MenuCategorySection(menuCategories = uiState.menuCategories, onCategoryClicked = { viewModel.updateItemsOnCategorySelected(it) })
                MenuItemsSection(menuProducts = uiState.menuProducts, onProductClicked = { viewModel.onProductClicked(it) })
            }
        }

        if (uiState.bagProducts.isNotEmpty()) {
            BottomSection(
                bagProducts = uiState.bagProducts,
                modifier = Modifier.align(Alignment.BottomCenter),
                onSecondaryButtonClicked = { viewModel.cancelOrder() }
            )
        }
    }
}

@PixelTabletPreview
@Composable
fun DashboardScreenPreview() {
    DashboardScreenContent(
        koinViewModel<DashboardViewModel>(),
        DashboardViewModel.UiState(),
        Modifier
    )
}
