package uk.co.softlantic.orderkiosk

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import uk.co.softlantic.orderkiosk.networking.ConnectivityObserver
import uk.co.softlantic.orderkiosk.ui.theme.OrderKioskTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            val viewModel = koinViewModel<BaseViewModel>()
            val status by viewModel.connectivityStatus.collectAsStateWithLifecycle()
            OrderKioskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        navHostController = navHostController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                if (status == ConnectivityObserver.Status.Lost) {
                    AlertDialog( // TODO: Style dialog and secure option - e.g. Code or NFC tag?.
                        onDismissRequest = { /* Prevent dismiss if critical */ },
                        title = { Text("No Connection") },
                        text = { Text("Please advise a member of the staff.") },
                        confirmButton = {
                            Button(onClick = {
                                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                            }) {
                                Text("Open Network Settings")
                            }
                        }
                    )
                }
            }
        }
    }
}
