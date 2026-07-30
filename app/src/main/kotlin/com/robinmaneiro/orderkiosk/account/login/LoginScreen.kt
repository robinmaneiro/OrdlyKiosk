package com.robinmaneiro.orderkiosk.account.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import com.robinmaneiro.orderkiosk.NavigationEvent
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.account.login.model.LoginPayload
import com.robinmaneiro.orderkiosk.ui.ErrorDialog
import com.robinmaneiro.orderkiosk.ui.KiLoadingSpinner
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar

@Composable
fun AccountScreen(
    mainUiEvent: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<LoginViewModel>()
    val uiState: LoginViewModel.UiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.actions.collect { action ->
            when (action) {
                is LoginViewModel.Actions.NavigateBack -> mainUiEvent.invoke(NavigationEvent.NavigateUp)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(title = stringResource(R.string.screen_title_account), onBack = {
                mainUiEvent.invoke(NavigationEvent.NavigateUp)
            })
        }
    ) {
        if (uiState.isLoading) {
            KiLoadingSpinner(Modifier.padding(it))
            return@Scaffold
        }

        LoginScreenContent(
            paddingValues = it,
            emailError = uiState.emailError,
            passwordError = uiState.passwordError,
            loginUser = { email, pass -> viewModel.loginUser(LoginPayload(email, pass)) },
            onEmailChanged = { viewModel.clearEmailError() },
            onPasswordChanged = { viewModel.clearPasswordError() },
            goToRegistration = { mainUiEvent.invoke(NavigationEvent.NavigateToDestination(Screens.RegistrationScreen.route)) },
            goToResetPassword = { mainUiEvent.invoke(NavigationEvent.NavigateToDestination(Screens.ResetPasswordScreen.route)) }
        )

        if (uiState.hasError) {
            ErrorDialog { mainUiEvent.invoke(NavigationEvent.NavigateUp) }
            return@Scaffold
        }
    }
}

@Composable
fun LoginScreenContent(
    paddingValues: PaddingValues,
    loginUser: (String, String) -> Unit,
    goToRegistration: () -> Unit,
    goToResetPassword: () -> Unit,
    emailError: Int? = null,
    passwordError: Int? = null,
    onEmailChanged: () -> Unit = {},
    onPasswordChanged: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(R.drawable.background_test),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(10.dp),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .size(600.dp, 500.dp)
                .background(Color.White.copy(alpha = 0.8F)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var userName by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                Text(
                    text = stringResource(R.string.login_heading)
                )

                Spacer(
                    Modifier.height(20.dp)
                )

                TextField(
                    value = userName,
                    onValueChange = {
                        userName = it
                        onEmailChanged()
                    },
                    isError = emailError != null,
                    supportingText = emailError?.let { { Text(stringResource(it)) } }
                )

                Spacer(
                    Modifier.height(20.dp)
                )

                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        onPasswordChanged()
                    },
                    isError = passwordError != null,
                    supportingText = passwordError?.let { { Text(stringResource(it)) } }
                )

                Spacer(
                    Modifier.height(20.dp)
                )

                Button(
                    {
                        loginUser.invoke(userName, password)
                    }
                ) {
                    Text(stringResource(R.string.btn_login))
                }

                Spacer(
                    Modifier.height(40.dp)
                )

                TextButton(
                    onClick = goToResetPassword
                ) {
                    Text(stringResource(R.string.btn_reset_password))
                }

                TextButton(
                    onClick = goToRegistration
                ) {
                    Text(stringResource(R.string.btn_register))
                }
            }
        }
    }
}

@PreviewPixelTablet
@Composable
private fun AccountScreenContentPreview() {
    LoginScreenContent(PaddingValues(20.dp), loginUser = { _, _ -> }, {}, {})
}
