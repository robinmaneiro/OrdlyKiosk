package com.robinmaneiro.orderkiosk.dashboard

import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.robinmaneiro.orderkiosk.welcome.HandleAction
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    navHostController: NavHostController,
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

    Column(
        modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.weight(0.8F)
        ) {
            MenuCategorySection(menuCategories = uiState.menuCategories, onCategoryClicked = { viewModel.updateItemsOnCategorySelected(it) })
            MenuItemsSection(menuProducts = uiState.menuProducts, onProductClicked = { viewModel.onProductClicked(it) })
        }
        val bagItems = listOf<String>() // TODO: Specify when
        if (bagItems.isNotEmpty()) {
            BottomSection()
        }
    }
    shownProduct?.let {
        ProductOverlay(
            product = it,
            onDismiss = { shownProduct = null }
        )
    }
}
