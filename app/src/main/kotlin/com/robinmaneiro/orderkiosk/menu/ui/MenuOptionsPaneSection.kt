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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.menu.MenuViewModel
import com.robinmaneiro.orderkiosk.ui.CustomDialog
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.ui.theme.Iceberg
import com.robinmaneiro.orderkiosk.util.SlideFromSide

@Composable
fun MenuOptionsPane(
    navController: NavController,
    uiState: MenuViewModel.UiState,
    visible: Boolean,
    toggleDiningOption: () -> Unit
) {
    var shouldShowDiningOptionDialog by remember { mutableStateOf(false) }

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
                    Triple("Sign In", { navController.navigate(Screens.LoginScreen.route) }, R.drawable.icn_rounded_user),
                    Triple("Order History", { navController.navigate(Screens.OrderHistoryScreen.route) }, R.drawable.icn_burger),
                    Triple("Coupons", { navController.navigate(Screens.CouponsScreen.route) }, R.drawable.icn_ticket),
                ).forEach {
                    OptionsPaneItem(it.first, it.second, it.third)
                }
            }

            OptionsPaneItem(
                uiState.diningOption.uiText, {
                    shouldShowDiningOptionDialog = true
                },
                null
            )
        }
    }
    if (shouldShowDiningOptionDialog) {
        CustomDialog(
            title = "Warning",
            body = "Are you sure you want to change the dining option?",
            primaryButtonLabel = "Change",
            secondaryButtonLabel = "Cancel",
            onPrimaryButtonClick = {
                toggleDiningOption.invoke()
                shouldShowDiningOptionDialog = false
            },
            onSecondaryButtonClick = {
                shouldShowDiningOptionDialog = false
            }
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
            .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp))
            .background(Iceberg.copy(alpha = 0.2f))
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icnRes?.let {
            Icon(
                painter = painterResource(icnRes),
                contentDescription = "Vector icon",
                tint = Aquamarine40,
                modifier = Modifier.size(50.dp)
            )
        }

        Text(
            text = label,
            textAlign = TextAlign.Center
        )
    }
}
