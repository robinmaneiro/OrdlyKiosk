package com.robinmaneiro.orderkiosk.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.robinmaneiro.orderkiosk.MainUiEvent
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.menu.model.DiningOption
import com.robinmaneiro.orderkiosk.menu.model.MenuCategory
import com.robinmaneiro.orderkiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.orderkiosk.menu.model.MenuProduct
import com.robinmaneiro.orderkiosk.menu.ui.BagTotalCostSection
import com.robinmaneiro.orderkiosk.menu.ui.MenuCategorySection
import com.robinmaneiro.orderkiosk.menu.ui.MenuItemsSection
import com.robinmaneiro.orderkiosk.menu.ui.MenuOptionsPane
import com.robinmaneiro.orderkiosk.menu.ui.OptionsPaneItem
import com.robinmaneiro.orderkiosk.menu.ui.ProductOverlay
import com.robinmaneiro.orderkiosk.ui.ErrorDialog
import com.robinmaneiro.orderkiosk.ui.KiLoadingSpinner
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import com.robinmaneiro.orderkiosk.ui.RotatingArrow
import com.robinmaneiro.orderkiosk.ui.SlideFromBottom
import com.robinmaneiro.orderkiosk.ui.SlideFromSide
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.ui.theme.Iceberg
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private const val SCROLL_PIXELS_NUMBER = 300F

@Composable
fun MenuScreen(
    serviceType: String?,
    mainUiEvent: (MainUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<MenuViewModel> {
        parametersOf(serviceType)
    }
    val uiState: MenuViewModel.UiState by viewModel.uiState.collectAsStateWithLifecycle()
    var shownProduct by remember { mutableStateOf<MenuItemExpanded?>(null) }
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(viewModel) {
        viewModel.actions.collect { action ->
            when (action) {
                is MenuViewModel.Actions.OpenProductInfo -> shownProduct = action.product
                is MenuViewModel.Actions.ResetLazyGridState -> lazyGridState.scrollToItem(0)
            }
        }
    }

    if (uiState.hasError) {
        ErrorDialog { mainUiEvent.invoke(MainUiEvent.NavigateUp) }
        return
    }

    val bagResponse = uiState.bagResponse ?: return

    MenuScreenContent(
        onUiEvent = viewModel::onHandleEvent,
        mainUiEvent = mainUiEvent,
        modifier = modifier,
        lazyGridState = lazyGridState,
        menuCategories = uiState.menuCategories,
        menuProducts = uiState.menuProducts,
        bagResponse = bagResponse,
        diningOption = uiState.diningOption
    )

    shownProduct?.let {
        ProductOverlay(
            product = it,
            onDismiss = { shownProduct = null },
            onUiEvent = { uiEvent ->
                viewModel.onHandleEvent(uiEvent)
                shownProduct = null
            }
        )
    }

    if (uiState.isLoading) {
        KiLoadingSpinner()
    }
}

@Composable
fun MenuScreenContent(
    menuCategories: ImmutableList<MenuCategory>,
    menuProducts: ImmutableList<MenuProduct>,
    diningOption: DiningOption,
    bagResponse: BagResponse?,
    lazyGridState: LazyGridState,
    mainUiEvent: (MainUiEvent) -> Unit,
    onUiEvent: (MenuViewModel.UiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        FixedContent(
            menuCategories = menuCategories,
            menuProducts = menuProducts,
            lazyGridState = lazyGridState,
            onProductClick = { productId -> onUiEvent(MenuViewModel.UiEvent.OnProductClick(productId)) },
            onCategoryClick = { categoryId -> onUiEvent(MenuViewModel.UiEvent.OnCategoryClick(categoryId)) }
        )

        AnimatedContent(
            bagResponse = bagResponse,
            lazyGridState = lazyGridState,
            mainUiEvent = mainUiEvent,
            diningOption = diningOption,
            onToggleDiningOption = { onUiEvent(MenuViewModel.UiEvent.ToggleDiningOption) },
        )
    }
}

@Composable
private fun FixedContent(
    menuCategories: ImmutableList<MenuCategory>,
    menuProducts: ImmutableList<MenuProduct>,
    lazyGridState: LazyGridState,
    onProductClick: (productId: String) -> Unit,
    onCategoryClick: (categoryId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(Iceberg.copy(alpha = 0.2f))
            .padding(horizontal = 20.dp, vertical = 4.dp)
    ) {
        MenuCategorySection(
            modifier = Modifier
                .width(240.dp),
            menuCategories = menuCategories,
            onCategoryClick = onCategoryClick
        )

        Spacer(
            Modifier.width(16.dp)
        )

        MenuItemsSection(
            menuProducts = menuProducts,
            onProductClick = onProductClick,
            lazyGridState = lazyGridState,
            modifier = Modifier.width(900.dp)
        )
    }
}

@Composable
private fun BoxScope.AnimatedContent(
    bagResponse: BagResponse?,
    lazyGridState: LazyGridState,
    diningOption: DiningOption,
    mainUiEvent: (MainUiEvent) -> Unit,
    onToggleDiningOption: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    SlideFromBottom(visible = bagResponse?.itemCount != 0) {
        bagResponse ?: return@SlideFromBottom
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            BagTotalCostSection(
                bagResponse.formattedTotalCost,
                bagResponse.itemCount,
                modifier = Modifier
                    .align(Alignment.Center),
                onClick = { mainUiEvent.invoke(MainUiEvent.NavigateToDestination(Screens.BagScreen.route)) }
            )
        }
    }

    var show by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .align(Alignment.TopEnd)
    ) {
        RotatingArrow {
            show = !show
        }

        Text(
            text = if (show) "Close" else "Open"
        )
    }

    NavigationArrows(
        visible = !show,
        onUpArrowClick = {
            scope.launch {
                lazyGridState.animateScrollBy(-SCROLL_PIXELS_NUMBER) // Notice the minus symbol.
            }
        },
        onDownArrowClick = {
            scope.launch {
                lazyGridState.animateScrollBy(SCROLL_PIXELS_NUMBER)
            }
        }
    )

    MenuOptionsPane(
        visible = show,
        diningOption = diningOption,
        toggleDiningOption = onToggleDiningOption,
        mainUiEvent = mainUiEvent
    )
}

@Composable
private fun NavigationArrows(
    visible: Boolean,
    onUpArrowClick: () -> Unit,
    onDownArrowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SlideFromSide(
        visible = visible,
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
    ) {
        Column(
            Modifier.padding(end = 20.dp)
        ) {
            RoundedSquareNavigateArrow(
                imageVector = Icons.Outlined.KeyboardArrowUp,
                onClick = onUpArrowClick
            )

            Spacer(
                Modifier.height(50.dp)
            )

            RoundedSquareNavigateArrow(
                imageVector = Icons.Outlined.KeyboardArrowDown,
                onClick = onDownArrowClick
            )
        }
    }
}

@Composable
private fun RoundedSquareNavigateArrow(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = modifier
            .size(60.dp)
            .border(2.dp, Aquamarine40, RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .padding(5.dp)
    )
}

@PreviewPixelTablet
@Composable
private fun DashboardScreenPreview() {
    MenuScreenContent(
        menuCategories = persistentListOf(),
        menuProducts = persistentListOf(),
        diningOption = DiningOption.TAKE_AWAY,
        bagResponse = null,
        lazyGridState = rememberLazyGridState(),
        mainUiEvent = {},
        onUiEvent = {},
        modifier = Modifier
    )
}

@Preview
@Composable
private fun OptionsPaneItemPreview() {
    OptionsPaneItem("Test", {}, null)
}
