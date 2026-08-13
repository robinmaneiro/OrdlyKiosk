package com.robinmaneiro.ordly.kiosk.orderhistory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.robinmaneiro.ordly.kiosk.NavigationEvent
import com.robinmaneiro.ordly.kiosk.R
import com.robinmaneiro.ordly.kiosk.ui.SimpleTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrderHistoryScreen(
    mainUiEvent: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val orderHistoryViewModel = koinViewModel<OrderHistoryViewModel>()

    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(title = stringResource(R.string.screen_title_order_history), onBack = {
                mainUiEvent.invoke(NavigationEvent.NavigateUp)
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
