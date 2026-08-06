package com.robinmaneiro.orderkiosk.menu.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.robinmaneiro.orderkiosk.NavigationEvent
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.menu.MenuViewModel
import com.robinmaneiro.orderkiosk.menu.model.DiningOption
import com.robinmaneiro.orderkiosk.ui.CustomDialog
import com.robinmaneiro.orderkiosk.ui.SlideFromSide

@Composable
fun MenuOptionsPane(
    mainUiEvent: (NavigationEvent) -> Unit,
    diningOption: DiningOption,
    visible: Boolean,
    onUiEvent: (MenuViewModel.UiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldShowDiningOptionDialog by remember { mutableStateOf(false) }
    var shouldShowStartAgainDialog by remember { mutableStateOf(false) }

    SlideFromSide(
        visible = visible,
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            modifier
                .fillMaxHeight()
                .width(100.dp)
                .padding(
                    top = 100.dp,
                    bottom = 40.dp
                )
                .border(1.dp, MaterialTheme.colorScheme.outline)
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val optionPaneList = listOf(
                    Triple(stringResource(R.string.screen_title_order_history), { mainUiEvent.invoke(NavigationEvent.NavigateToDestination(Screens.OrderHistoryScreen.route)) }, R.drawable.icn_burger),
                    Triple(stringResource(R.string.screen_title_coupons), { mainUiEvent.invoke(NavigationEvent.NavigateToDestination(Screens.CouponsScreen.route)) }, R.drawable.icn_ticket)
                )

                optionPaneList.forEach {
                    OptionsPaneItem(it.first, it.second, it.third)
                }
            }

            OptionsPaneItem(
                stringResource(R.string.menu_option_start_again),
                {
                    shouldShowStartAgainDialog = true
                },
                R.drawable.icn_restart
            )

            OptionsPaneItem(
                diningOption.uiText,
                {
                    shouldShowDiningOptionDialog = true
                },
                R.drawable.icn_dining
            )
        }
    }
    if (shouldShowDiningOptionDialog) {
        val primaryButtonAction = {
            onUiEvent.invoke(MenuViewModel.UiEvent.ToggleDiningOption)
            shouldShowDiningOptionDialog = false
        }
        val secondaryButtonAction = {
            shouldShowDiningOptionDialog = false
        }
        CustomDialog(
            title = stringResource(R.string.dialog_warning_title),
            body = stringResource(R.string.dialog_change_dining_body),
            primaryButtonLabelToAct = stringResource(R.string.btn_change) to primaryButtonAction,
            secondaryButtonLabelToAct = stringResource(R.string.btn_cancel) to secondaryButtonAction
        )
    }

    if (shouldShowStartAgainDialog) {
        val primaryButtonAction = {
            onUiEvent.invoke(MenuViewModel.UiEvent.StartAgain)
            shouldShowStartAgainDialog = false
        }

        val secondaryButtonAction = {
            shouldShowStartAgainDialog = false
        }

        CustomDialog(
            title = stringResource(R.string.dialog_warning_title),
            body = stringResource(R.string.dialog_start_again_body),
            primaryButtonLabelToAct = stringResource(R.string.menu_option_start_again) to primaryButtonAction,
            secondaryButtonLabelToAct = stringResource(R.string.btn_cancel) to secondaryButtonAction
        )
    }
}

@Composable
fun OptionsPaneItem(
    label: String,
    onClick: () -> Unit,
    @DrawableRes icnRes: Int?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .size(width = 80.dp, height = 110.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icnRes?.let {
            Icon(
                painter = painterResource(icnRes),
                contentDescription = stringResource(R.string.cd_bag_icon),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(50.dp)
            )
        }

        Text(
            text = label,
            textAlign = TextAlign.Center
        )
    }
}
