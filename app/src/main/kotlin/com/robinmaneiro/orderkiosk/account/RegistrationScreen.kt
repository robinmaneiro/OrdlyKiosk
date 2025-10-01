package com.robinmaneiro.orderkiosk.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar
import com.robinmaneiro.orderkiosk.util.PixelTabletPreview
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<RegistrationViewModel>()

    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(title = "Register New Account", onBack = {
                navController.navigateUp()
            })
        }
    ) {
        RegistrationScreenContent(it)
    }
}

@Composable
fun RegistrationScreenContent(paddingValues: PaddingValues) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        var title by remember { mutableStateOf("") }
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var emailAddress by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        TextField(
            value = title,
            onValueChange = { title = it }
        )

        Spacer(
            Modifier.height(20.dp)
        )

        TextField(
            value = firstName,
            onValueChange = { firstName = it }
        )

        Spacer(
            Modifier.height(20.dp)
        )

        TextField(
            value = lastName,
            onValueChange = { lastName = it }
        )

        Spacer(
            Modifier.height(20.dp)
        )

        TextField(
            value = emailAddress,
            onValueChange = { emailAddress = it }
        )

        Spacer(
            Modifier.height(20.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it }
        )

        Spacer(
            Modifier.height(20.dp)
        )

        Button({}) {
            Text("Register")
        }
    }
}

@PixelTabletPreview
@Composable
fun RegistrationScreenContentPreview() {
    RegistrationScreenContent(PaddingValues(20.dp))
}