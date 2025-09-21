package com.robinmaneiro.orderkiosk.account

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
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreen(
    navController: NavController,
    modifier: Modifier= Modifier
) {
    val viewModel = koinViewModel<AccountViewModel>()

    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(title = "Account", onBack = {
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
                text = "This is ACCOUNT SCREEN!!!!"
            )
        }
    }
}
