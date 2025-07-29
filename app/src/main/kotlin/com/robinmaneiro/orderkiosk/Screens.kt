package com.robinmaneiro.orderkiosk

sealed class Screens(val route: String) {
    data object WelcomeScreen: Screens(route = "WelcomeScreen")
    data object DashboardScreen : Screens(route = "DashboardScreen")
}