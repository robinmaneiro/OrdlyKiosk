package com.robinmaneiro.orderkiosk.account

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.account.model.LoginPayload
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<LoginViewModel>()

    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(title = "Account", onBack = {
                navController.navigateUp()
            })
        }
    ) {
        LoginScreenContent(
            it,
            loginUser = { email, pass -> viewModel.loginUser(LoginPayload(email, pass)) },
            goToRegistration = { navController.navigate(Screens.RegistrationScreen.route) }
        )
    }
}

@Composable
fun LoginScreenContent(
    paddingValues: PaddingValues,
    loginUser: (String, String) -> Unit,
    goToRegistration: () -> Unit,
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
                .background(Color.White.copy(0.8F)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var userName by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                Text(
                    text = "LOGIN"
                )

                Spacer(
                    Modifier.height(20.dp)
                )

                TextField(
                    value = userName,
                    onValueChange = { userName = it }
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

                Button(
                    {
                        loginUser.invoke(userName, password)
                    }
                ) {
                    Text("Login")
                }

                Spacer(
                    Modifier.height(40.dp)
                )

                TextButton(
                    {
                        goToRegistration.invoke()
                    }
                ) {
                    Text("Register")
                }
            }
        }
    }
}

@PreviewPixelTablet
@Composable
private fun AccountScreenContentPreview() {
    LoginScreenContent(PaddingValues(20.dp), loginUser = { user, name -> }, {})
}
