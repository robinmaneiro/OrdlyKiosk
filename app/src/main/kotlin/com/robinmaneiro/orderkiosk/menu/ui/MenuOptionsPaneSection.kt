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
import com.robinmaneiro.orderkiosk.MainUiEvent
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.menu.model.DiningOption
import com.robinmaneiro.orderkiosk.ui.CustomDialog
import com.robinmaneiro.orderkiosk.ui.SlideFromSide
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.ui.theme.Iceberg

@Composable
fun MenuOptionsPane(
    mainUiEvent: (MainUiEvent) -> Unit,
    diningOption: DiningOption,
    visible: Boolean,
    toggleDiningOption: () -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldShowDiningOptionDialog by remember { mutableStateOf(false) }

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
                .border(1.dp, Color.DarkGray)
                .background(Color.White)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // TODO: Re-think these options depending if user is able to log in.
//                val optionPaneList = if (uiState.isLoggedIn) {
//                    listOf(
//                        Triple("Account Detais", { navigateToDestination.invoke(Screens.MyAccountScreen.route) }, R.drawable.icn_rounded_user), // TODO: Change for different destination
//                        Triple("Order History", { navigateToDestination.invoke(Screens.OrderHistoryScreen.route) }, R.drawable.icn_burger),
//                        Triple("Coupons", { navigateToDestination.invoke(Screens.CouponsScreen.route) }, R.drawable.icn_ticket),
//                    )
//                } else {
//                    listOf(
//                        Triple("Sign In", { navigateToDestination.invoke(Screens.LoginScreen.route) }, R.drawable.icn_rounded_user),
//                        Triple("Order History", { navigateToDestination.invoke(Screens.OrderHistoryScreen.route) }, R.drawable.icn_burger),
//                        Triple("Coupons", { navigateToDestination.invoke(Screens.CouponsScreen.route) }, R.drawable.icn_ticket),
//                    )
//                }

                val optionPaneList = listOf(
                    Triple("Order History", { mainUiEvent.invoke(MainUiEvent.NavigateToDestination(Screens.OrderHistoryScreen.route)) }, R.drawable.icn_burger),
                    Triple("Coupons", { mainUiEvent.invoke(MainUiEvent.NavigateToDestination(Screens.CouponsScreen.route)) }, R.drawable.icn_ticket)
                )


                optionPaneList.forEach {
                    OptionsPaneItem(it.first, it.second, it.third)
                }
            }

            OptionsPaneItem(
                "Start again",
                {
                    // Start Again
                },
                null
            )

            OptionsPaneItem(
                diningOption.uiText,
                {
                    shouldShowDiningOptionDialog = true
                },
                null
            )
        }
    }
    if (shouldShowDiningOptionDialog) {
        val primaryButtonAction = {
            toggleDiningOption.invoke()
            shouldShowDiningOptionDialog = false
        }
        val secondaryButtonAction = {
            shouldShowDiningOptionDialog = false
        }
        CustomDialog(
            title = "Warning",
            body = "Are you sure you want to change the dining option?",
            primaryButtonLabelToAct = "Change" to primaryButtonAction,
            secondaryButtonLabelToAct = "Cancel" to secondaryButtonAction
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
