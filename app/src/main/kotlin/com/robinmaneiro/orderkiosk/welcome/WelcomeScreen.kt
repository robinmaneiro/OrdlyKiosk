package com.robinmaneiro.orderkiosk.welcome

import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.ui.theme.DarkGrey
import com.robinmaneiro.orderkiosk.ui.theme.Iceberg
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

    WelcomeScreenContent(
        onEatInClicked = { navHostController.navigate(Screens.DashboardScreen.route) },
        onTakeAwayClicked = { navHostController.navigate(Screens.DashboardScreen.route) }
    )
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

@Composable
fun WelcomeScreenContent(
    onEatInClicked: () -> Unit = {},
    onTakeAwayClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Iceberg.copy(alpha = 0.5F))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "How would you like to enjoy your meal?",
            fontSize = 72.sp,
            color = DarkGrey,
            textAlign = TextAlign.Center,
            lineHeight = 90.sp
        )

        Spacer(Modifier.height(40.dp))

        Row {
            DeliveryTypeCard(text = "Eat In", onClick = onEatInClicked)
            Spacer(Modifier.width(40.dp))
            DeliveryTypeCard(text = "Take Away", onClick = onTakeAwayClicked)
        }
    }

}

@Composable
fun DeliveryTypeCard(
    text: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(width = 450.dp, height = 400.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Aquamarine40,
            contentColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 48.sp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = PIXEL_TABLET
)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreenContent()
}