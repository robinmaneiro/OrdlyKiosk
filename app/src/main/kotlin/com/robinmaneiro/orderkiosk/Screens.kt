package com.robinmaneiro.orderkiosk

import com.robinmaneiro.orderkiosk.Screens.WelcomeScreen.asPlaceholder

sealed interface Screens {
    val route: String

    fun String.getParametrizedRoute(vararg parameters: String): String {
        val routeParametersList = parameters.toMutableList().apply { add(0, this@getParametrizedRoute) }
        return routeParametersList.joinToString(separator = "/")
    }

    fun String.asPlaceholder() = "{$this}"

    data object OffersScreen : Screens {
        override val route: String = "OffersScreen"
    }

    data object WelcomeScreen : Screens {
        override val route: String = "WelcomeScreen"
    }

    data class MenuScreen(
        val diningOption: String = DINING_OPTION_SUB.asPlaceholder()
    ) : Screens {
        override val route: String = "MenuScreen".getParametrizedRoute(diningOption)

        companion object Companion {
            const val DINING_OPTION_SUB = "diningOption"
        }
    }

    data object BagScreen : Screens {
        override val route: String = "BagScreen"
    }

    data object CheckoutScreen : Screens {
        override val route: String = "CheckoutScreen"
    }

    data object OrderSummaryScreen : Screens {
        override val route: String = "OrderSummary"
    }

    data object RegistrationScreen : Screens {
        override val route: String = "RegistrationScreen"
    }

    data object ResetPasswordScreen : Screens {
        override val route: String = "ResetPasswordScreen"
    }

    data object LoginScreen : Screens {
        override val route: String = "LoginScreen"
    }

    data object OrderHistoryScreen : Screens {
        override val route: String = "OrderHistoryScreen"
    }

    data object CouponsScreen : Screens {
        override val route: String = "CouponsScreen"
    }

    data object MyAccountScreen : Screens {
        override val route: String = "MyAccountScreen"
    }
}
