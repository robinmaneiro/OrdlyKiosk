package com.robinmaneiro.orderkiosk.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.robinmaneiro.orderkiosk.dashboard.model.MenuProductExpanded
import com.robinmaneiro.orderkiosk.dashboard.ui.BottomSection
import com.robinmaneiro.orderkiosk.dashboard.ui.MenuCategorySection
import com.robinmaneiro.orderkiosk.dashboard.ui.MenuItemsSection
import com.robinmaneiro.orderkiosk.dashboard.ui.ProductOverlay
import com.robinmaneiro.orderkiosk.util.RotatingArrow
import com.robinmaneiro.orderkiosk.ui.KiLoadingSpinner
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
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
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Iceberg.copy(alpha = 0.2f))
                .padding(all = 20.dp)
        ) {
            val totalWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp }
            val menuCategoriesWidth: Dp = 300.dp
            var rightPaneMenuWidth by remember { mutableStateOf(20.dp) } // TODO: Probably better to move this to the state
            val menuItemsWidth: Dp = totalWidth - menuCategoriesWidth - rightPaneMenuWidth

            MenuCategorySection(
                modifier = Modifier
                    .width(240.dp),
                menuCategories = uiState.menuCategories,
                onCategoryClicked = { viewModel.updateItemsOnCategorySelected(it) }
            )

            Spacer(
                Modifier.width(16.dp)
            )

            MenuItemsSection(
                modifier = Modifier
                    .width(900.dp),
                menuProducts = uiState.menuProducts,
                onProductClicked = { viewModel.onProductClicked(it) }
            )
        }
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

        var show by remember { mutableStateOf(false) }
        RotatingArrow(
            Modifier.align(Alignment.TopEnd)
        ) {
            show = !show
        }

        SlideFromSide(
            visible = !show
        ) {
            Column(
                Modifier.padding(end = 20.dp)
            ) {
                RoundedSquareNavigateArrow(
                    imageVector = Icons.Outlined.KeyboardArrowUp
                )

                Spacer(
                    Modifier.height(50.dp)
                )

                RoundedSquareNavigateArrow(
                    imageVector = Icons.Outlined.KeyboardArrowDown
                )
            }
        }

        SlideFromSide(
            visible = show,
            horizontalPadding = 16.dp
        ) {
            Column(
                Modifier
                    .padding()
            ) {
                repeat(5) {
                    PaneX()
                    Spacer(Modifier.height(5.dp))
                }
            }
        }
    }
}

@Composable
fun PaneX() {
    Box(
        Modifier
            .size(
                height = 100.dp,
                width = 80.dp
            )
            .border(2.dp, Color.Blue)
    )
}

@Composable
fun RoundedSquareNavigateArrow(
    imageVector: ImageVector
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = Modifier
            .size(60.dp)
            .border(2.dp, Aquamarine40, RoundedCornerShape(3.dp))
            .padding(5.dp)
    )
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

@Preview
@Composable
fun PaneXPreview() {
    PaneX()
}
