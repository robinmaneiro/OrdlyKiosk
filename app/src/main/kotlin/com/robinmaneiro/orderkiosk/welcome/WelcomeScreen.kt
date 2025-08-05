package com.robinmaneiro.orderkiosk.welcome

import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.util.showToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<WelcomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(WelcomeViewModel.UiState())
    val action by viewModel.actions.collectAsStateWithLifecycle(null)
    action?.let {
        HandleAction(it, context)
    }

    Column(
        modifier = modifier.padding(all = 16.dp)
            .fillMaxSize()
            .clickable {
                navHostController.navigate(Screens.DashboardScreen.route)
            }
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to McDonald's"
        )

        Text(
            text = "Tap anywhere to start the order"
        )
    }
}

@Composable
fun HandleAction(action: WelcomeViewModel.Actions, context: Context) {
    when (action) {
        WelcomeViewModel.Actions.ShowErrorDialog -> {
            val activity = LocalActivity.current
            var showErrorDialog by remember { mutableStateOf(true) }
//            if (showErrorDialog) {
//                ErrorDialog {
//                    showErrorDialog = false
//                    activity?.finishAffinity() // Close the app, since this is the only screen visible to the user
//                }
//            }
        }

        is WelcomeViewModel.Actions.ShowToastMessage -> context.showToast(action.message)
    }
}
