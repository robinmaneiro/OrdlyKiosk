package com.robinmaneiro.orderkiosk.koin

import com.robinmaneiro.orderkiosk.account.AccountViewModel
import com.robinmaneiro.orderkiosk.bag.BagViewModel
import com.robinmaneiro.orderkiosk.coupons.CouponsViewModel
import com.robinmaneiro.orderkiosk.dashboard.DashboardViewModel
import com.robinmaneiro.orderkiosk.dashboard.usecase.GetItemInfoUseCase
import com.robinmaneiro.orderkiosk.dashboard.usecase.GetMenuCategoriesUseCase
import com.robinmaneiro.orderkiosk.dashboard.usecase.GetMenuItemsByCategoryUseCase
import com.robinmaneiro.orderkiosk.orderhistory.OrderHistoryViewModel
import com.robinmaneiro.orderkiosk.welcome.WelcomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val viewModelModules = module {
    factoryOf(::DashboardViewModel)
    factoryOf(::BagViewModel)
    factoryOf(::AccountViewModel)
    factoryOf(::CouponsViewModel)
    factoryOf(::OrderHistoryViewModel)
    factoryOf(::WelcomeViewModel)
}

val useCaseModules = module {
    factoryOf(::GetMenuCategoriesUseCase)
    factoryOf(::GetMenuItemsByCategoryUseCase)
    factoryOf(::GetItemInfoUseCase)
}
