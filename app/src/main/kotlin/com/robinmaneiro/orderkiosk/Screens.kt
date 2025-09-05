package com.robinmaneiro.orderkiosk

import com.robinmaneiro.orderkiosk.Screens.WelcomeScreen.asPlaceholder

sealed interface Screens {
    val route: String

    fun String.getParametrizedRoute(vararg parameters: String): String {
        val routeParametersList = parameters.toMutableList().apply { add(0, this@getParametrizedRoute) }
        return routeParametersList.joinToString(separator = "/")
    }

    fun String.asPlaceholder() = "{$this}"

    data object WelcomeScreen : Screens {
        override val route: String = "WelcomeScreen"
    }

    data class DashboardScreen(
        val serviceType: String = SERVICE_TYPE_SUB.asPlaceholder()
    ) : Screens {
        override val route: String = "DashboardScreen".getParametrizedRoute(serviceType)

        companion object Companion {
            const val SERVICE_TYPE_SUB = "serviceType"
        }
    }

    data object BagScreen : Screens {
        override val route: String = "BagScreen"
    }

    data object AccountScreen : Screens {
        override val route: String = "AccountScreen"
    }

    data object OrderHistoryScreen: Screens {
        override val route: String = "OrderHistoryScreen"
    }

    data object CouponsScreen: Screens {
        override val route: String = "CouponsScreen"
    }
}
