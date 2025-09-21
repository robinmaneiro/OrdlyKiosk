package com.robinmaneiro.orderkiosk.orderhistory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar

@Composable
fun OrderHistoryScreen(
    navController: NavController,
    modifier: Modifier= Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(title = "Order History", onBack = {
                navController.navigateUp()
            })
        }
    ) {
        Box(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "This is ORDER HISTORY SCREEN!!!!"
            )
        }
    }
}