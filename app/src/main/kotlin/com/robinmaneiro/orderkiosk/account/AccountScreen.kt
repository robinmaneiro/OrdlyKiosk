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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar
import com.robinmaneiro.orderkiosk.util.PixelTabletPreview
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreen(
    navController: NavController,
    modifier: Modifier = Modifier
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
        AccountScreenContent(it)
    }
}

@Composable
fun AccountScreenContent(paddingValues: PaddingValues) {
    Box(
        Modifier
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
                    .fillMaxSize()
                    .padding(100.dp)
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
                        {}
                    ) {
                        Text("Login")
                    }
                }
            }
    }
}

@PixelTabletPreview
@Composable
fun AccountScreenContentPreview() {
    AccountScreenContent(PaddingValues(20.dp))
}
