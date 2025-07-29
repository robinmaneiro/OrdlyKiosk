package com.robinmaneiro.orderkiosk

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.robinmaneiro.orderkiosk.dashboard.DashboardScreen
import com.robinmaneiro.orderkiosk.welcome.WelcomeScreen

@Composable
fun MainScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navHostController, startDestination = Screens.WelcomeScreen.route, modifier = modifier) {
        composable(Screens.WelcomeScreen.route) { WelcomeScreen(modifier = Modifier, navHostController = navHostController) }
        composable(Screens.DashboardScreen.route) { DashboardScreen(modifier = Modifier, navHostController = navHostController) }
//        navAnimatedComposable(Screens.TestingScreen.route) { TestingScreen() }
//        navAnimatedComposable(Screens.LoginScreen.route) { LoginScreen(navController = navHostController) }
//        navAnimatedComposable(Screens.RegistrationScreen.route) { RegistrationScreen() }
//        navAnimatedComposable(Screens.PasswordRecoveryScreen.route, listOf(
//            navArgument("email_address") {
//                type = NavType.StringType
//            }
//        )) {
//            val emailAddress = it.arguments?.getString("email_address").orEmpty()
//            PasswordRecoveryScreen(emailAddress = emailAddress)
//        }
    }
}
