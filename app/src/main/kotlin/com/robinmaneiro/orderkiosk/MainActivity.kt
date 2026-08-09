package com.robinmaneiro.orderkiosk

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.robinmaneiro.orderkiosk.networking.ConnectivityObserver
import com.robinmaneiro.orderkiosk.ui.theme.OrderKioskTheme
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
                        title = { Text(stringResource(R.string.no_connection_title)) },
                        text = { Text(stringResource(R.string.no_connection_body)) },
                        confirmButton = {
                            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                Button(onClick = {
                                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                                }) {
                                    Text(stringResource(R.string.btn_open_network_settings))
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
