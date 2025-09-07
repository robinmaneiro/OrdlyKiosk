package com.robinmaneiro.orderkiosk.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.menu.model.MenuProductExpanded
import com.robinmaneiro.orderkiosk.menu.ui.MenuCategorySection
import com.robinmaneiro.orderkiosk.menu.ui.MenuItemsSection
import com.robinmaneiro.orderkiosk.menu.ui.ProductOverlay
import com.robinmaneiro.orderkiosk.menu.ui.TotalPriceSection
import com.robinmaneiro.orderkiosk.ui.KiLoadingSpinner
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.ui.theme.Iceberg
import com.robinmaneiro.orderkiosk.util.PixelTabletPreview
import com.robinmaneiro.orderkiosk.util.RotatingArrow
import com.robinmaneiro.orderkiosk.util.SlideFromBottom
import com.robinmaneiro.orderkiosk.util.SlideFromSide
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private const val SCROLL_PIXELS_NUMBER = 300F

@Composable
fun DashboardScreen(
    serviceType: String?,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel = koinViewModel<MenuViewModel>()
    val uiState: MenuViewModel.UiState by viewModel.uiState.collectAsStateWithLifecycle()
    var shownProduct by remember { mutableStateOf<MenuProductExpanded?>(null) }

    if (uiState.isLoading) {
        KiLoadingSpinner()
    }

    LaunchedEffect(viewModel) {
        viewModel.actions.collect { action ->
            when (action) {
                is MenuViewModel.Actions.OpenProductInfo -> shownProduct = action.product
                // handle other actions if needed
            }
        }
    }

    DashboardScreenContent(
        navController = navController,
        viewModel = viewModel, // TODO: Follow pattern to encapsulate functions in the view model
        uiState = uiState,
        serviceType = serviceType,
        onBagClick = { navController.navigate(Screens.BagScreen.route) },
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
    navController: NavController,
    viewModel: MenuViewModel,
    uiState: MenuViewModel.UiState,
    serviceType: String?,
    onBagClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        val lazyGridState = rememberLazyGridState()
        val scope = rememberCoroutineScope()

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Iceberg.copy(alpha = 0.2f))
                .padding(all = 20.dp)
        ) {

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
                menuProducts = uiState.menuProducts,
                onProductClicked = { viewModel.onProductClicked(it) },
                lazyGridState = lazyGridState,
                modifier = Modifier.width(900.dp)
            )
        }

        //region Animated content
        SlideFromBottom(visible = uiState.bagProducts.isNotEmpty()) {
            Box(
                Modifier
                    .fillMaxWidth()
            ) {
                TotalPriceSection(
                    uiState.bagProducts,
                    modifier = Modifier
                        .align(Alignment.Center),
                    onClick = onBagClick
                )
            }
        }

        var show by remember { mutableStateOf(false) }
        RotatingArrow(
            Modifier
                .padding(
                    end = 20.dp
                )
                .size(60.dp)
                .align(Alignment.TopEnd)
        ) {
            show = !show
        }

        NavigationArrows(
            visible = !show,
            onUpArrowClicked = {
                scope.launch {
                    lazyGridState.animateScrollBy(-SCROLL_PIXELS_NUMBER) // Notice the minus symbol.
                }
            },
            onDownArrowClicked = {
                scope.launch {
                    lazyGridState.animateScrollBy(SCROLL_PIXELS_NUMBER)
                }
            }
        )

        MenuOptionsPane(
            navController,
            show
        )
        //endregion
    }
}

@Composable
fun NavigationArrows(
    visible: Boolean,
    onUpArrowClicked: () -> Unit,
    onDownArrowClicked: () -> Unit
) {
    SlideFromSide(
        visible = visible,
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            Modifier.padding(end = 20.dp)
        ) {
            RoundedSquareNavigateArrow(
                imageVector = Icons.Outlined.KeyboardArrowUp,
                onClick = onUpArrowClicked
            )

            Spacer(
                Modifier.height(50.dp)
            )

            RoundedSquareNavigateArrow(
                imageVector = Icons.Outlined.KeyboardArrowDown,
                onClick = onDownArrowClicked
            )
        }
    }
}

@Composable
fun MenuOptionsPane(
    navController: NavController,
    visible: Boolean
) {
    SlideFromSide(
        visible = visible,
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .width(100.dp)
                .padding(
                    top = 100.dp,
                    bottom = 40.dp
                )
                .border(1.dp, Color.DarkGray)
                .background(Color.White)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // TODO: Show different content for the menu depending if the user is a GUEST or LOGGED-IN user
                listOf(
                    "Sign In" to { navController.navigate(Screens.AccountScreen.route) },
                    "Order History" to { navController.navigate(Screens.OrderHistoryScreen.route) },
                    "Coupons" to { navController.navigate(Screens.CouponsScreen.route) },
                ).forEach {
                    OptionsPaneItem(it.first, it.second)
                }
            }

            OptionsPaneItem(
                "Sit in", {}
            )
        }
    }
}

@Composable
fun OptionsPaneItem(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .size(width = 80.dp, height = 100.dp)
            .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp))
            .background(Iceberg.copy(alpha = 0.2f))
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RoundedSquareNavigateArrow(
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = Modifier
            .size(60.dp)
            .border(2.dp, Aquamarine40, RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .padding(5.dp)
    )
}


@PixelTabletPreview
@Composable
fun DashboardScreenPreview() {
    DashboardScreenContent(
        navController = rememberNavController(),
        viewModel = koinViewModel<MenuViewModel>(),
        uiState = MenuViewModel.UiState(),
        onBagClick = {},
        serviceType = "Take Away"
    )
}

@Preview
@Composable
fun OptionsPaneItemPreview() {
    OptionsPaneItem("Test", {})
}
