package com.robinmaneiro.orderkiosk

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.robinmaneiro.orderkiosk.account.login.AccountScreen
import com.robinmaneiro.orderkiosk.account.personaldetails.MyAccountScreen
import com.robinmaneiro.orderkiosk.account.registration.RegistrationScreen
import com.robinmaneiro.orderkiosk.account.resetpassword.ResetPasswordScreen
import com.robinmaneiro.orderkiosk.bag.BagScreen
import com.robinmaneiro.orderkiosk.checkout.CheckoutScreen
import com.robinmaneiro.orderkiosk.coupons.CouponsScreen
import com.robinmaneiro.orderkiosk.menu.MenuScreen
import com.robinmaneiro.orderkiosk.offers.OffersScreen
import com.robinmaneiro.orderkiosk.orderhistory.OrderHistoryScreen
import com.robinmaneiro.orderkiosk.ordersummary.OrderSummaryScreen
import com.robinmaneiro.orderkiosk.welcome.WelcomeScreen

@Suppress("NonSkippableComposable")
@Composable
fun MainScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    fun onHandleEvent(mainUiEvent: MainUiEvent) {
        when (mainUiEvent) {
            MainUiEvent.NavigateUp -> navHostController.navigateUp()
            is MainUiEvent.NavigateToDestination -> navHostController.navigate(mainUiEvent.destinationId)
        }
    }

    NavHost(navController = navHostController, startDestination = Screens.OffersScreen.route, modifier = modifier) {
        composable(Screens.OffersScreen.route) { OffersScreen(::onHandleEvent) }
        composable(
            Screens.WelcomeScreen.route,
            enterTransition = {
                // Slide in from the right and fade in
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                ) + fadeIn(animationSpec = tween(700))
            },
        ) {
            WelcomeScreen(::onHandleEvent)
        }
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
                mainUiEvent = ::onHandleEvent
            )
        }
        composable(route = Screens.BagScreen.route) { BagScreen(::onHandleEvent) }
        composable(route = Screens.CheckoutScreen.route) { CheckoutScreen(::onHandleEvent) }
        composable(route = Screens.OrderSummaryScreen.route) { OrderSummaryScreen(::onHandleEvent) }
        composable(route = Screens.RegistrationScreen.route) { RegistrationScreen(::onHandleEvent) }
        composable(route = Screens.ResetPasswordScreen.route) { ResetPasswordScreen(::onHandleEvent) }
        composable(route = Screens.LoginScreen.route) { AccountScreen(::onHandleEvent) }
        composable(route = Screens.MyAccountScreen.route) { MyAccountScreen(::onHandleEvent) }
        composable(route = Screens.CouponsScreen.route) { CouponsScreen(::onHandleEvent) }
        composable(route = Screens.OrderHistoryScreen.route) { OrderHistoryScreen(::onHandleEvent) }
    }
}

sealed interface MainUiEvent {
    data object NavigateUp : MainUiEvent
    data class NavigateToDestination(val destinationId: String) : MainUiEvent
}
