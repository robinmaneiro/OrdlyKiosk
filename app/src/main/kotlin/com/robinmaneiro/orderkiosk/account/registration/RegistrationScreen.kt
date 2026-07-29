package com.robinmaneiro.orderkiosk.account.registration

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
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import com.robinmaneiro.orderkiosk.NavigationEvent
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.account.registration.model.RegisterPayload
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar

@Composable
fun RegistrationScreen(
    mainUiEvent: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<RegistrationViewModel>()

    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(title = stringResource(R.string.screen_title_register), onBack = {
                mainUiEvent.invoke(NavigationEvent.NavigateUp)
            })
        }
    ) {
        RegistrationScreenContent(
            paddingValues = it,
            onRegisterClick = viewModel::registerAccount
        )
    }
}

@Composable
fun RegistrationScreenContent(
    paddingValues: PaddingValues,
    onRegisterClick: (RegisterPayload) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
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

        Button({
            onRegisterClick.invoke(
                RegisterPayload(
                    title = title,
                    firstName = firstName,
                    lastName = lastName,
                    email = emailAddress,
                    password = password
                )
            )
        }) {
            Text(stringResource(R.string.btn_register))
        }
    }
}

@PreviewPixelTablet
@Composable
private fun RegistrationScreenContentPreview() {
    RegistrationScreenContent(paddingValues = PaddingValues(20.dp), onRegisterClick = {})
}
