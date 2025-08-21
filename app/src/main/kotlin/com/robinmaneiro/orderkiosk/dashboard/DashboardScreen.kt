package com.robinmaneiro.orderkiosk.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.robinmaneiro.orderkiosk.dashboard.ui.BottomSection
import com.robinmaneiro.orderkiosk.dashboard.ui.MenuSection
import com.robinmaneiro.orderkiosk.dashboard.ui.ProductsSection
import com.robinmaneiro.orderkiosk.ui.KiLoadingSpinner
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<DashboardViewModel>()
    val uiState: DashboardViewModel.UiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        KiLoadingSpinner()
    }

    Column(
        modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.weight(0.8F)
        ) {
            MenuSection()
            ProductsSection(menuProducts = uiState.menuProducts)
        }
//        Spacer(Modifier.padding(5.dp))
        BottomSection()
    }
}
