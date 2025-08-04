package com.robinmaneiro.orderkiosk.koin

import com.robinmaneiro.orderkiosk.dashboard.DashboardViewModel
import com.robinmaneiro.orderkiosk.welcome.WelcomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val viewModelModules = module {
    factoryOf(::WelcomeViewModel)
    factoryOf(::DashboardViewModel)
}
