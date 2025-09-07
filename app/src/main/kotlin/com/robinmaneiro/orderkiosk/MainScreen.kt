package com.robinmaneiro.orderkiosk

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.robinmaneiro.orderkiosk.account.AccountScreen
import com.robinmaneiro.orderkiosk.bag.BagScreen
import com.robinmaneiro.orderkiosk.coupons.CouponsScreen
import com.robinmaneiro.orderkiosk.menu.MenuScreen
import com.robinmaneiro.orderkiosk.orderhistory.OrderHistoryScreen
import com.robinmaneiro.orderkiosk.welcome.WelcomeScreen

@Composable
fun MainScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navHostController, startDestination = Screens.WelcomeScreen.route, modifier = modifier) {
        composable(Screens.WelcomeScreen.route) { WelcomeScreen(modifier = Modifier, navHostController = navHostController) }
        composable(
            route = Screens.MenuScreen().route,
            arguments = listOf(
                navArgument(Screens.MenuScreen.DINING_OPTION_SUB) { type = NavType.StringType }
            )
        ) {
            val serviceType = navHostController.currentBackStackEntry?.arguments?.getString(Screens.MenuScreen.DINING_OPTION_SUB)

            MenuScreen(
                modifier = Modifier,
                serviceType = serviceType,
                navController = navHostController
            )
        }
        composable(route = Screens.BagScreen.route) { BagScreen(navController = navHostController) }
        composable(route = Screens.AccountScreen.route) { AccountScreen(navController = navHostController) }
        composable(route = Screens.CouponsScreen.route) { CouponsScreen(navController = navHostController) }
        composable(route = Screens.OrderHistoryScreen.route) { OrderHistoryScreen(navController = navHostController) }

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
