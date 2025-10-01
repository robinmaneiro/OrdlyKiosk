package com.robinmaneiro.orderkiosk.koin

import com.robinmaneiro.orderkiosk.account.LoginViewModel
import com.robinmaneiro.orderkiosk.account.RegistrationViewModel
import com.robinmaneiro.orderkiosk.bag.BagViewModel
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository
import com.robinmaneiro.orderkiosk.bag.repository.BagRepositoryImpl
import com.robinmaneiro.orderkiosk.bag.usecase.AddToBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveAllBagItemsUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveFromBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.UpdateBagItemUseCase
import com.robinmaneiro.orderkiosk.coupons.CouponsViewModel
import com.robinmaneiro.orderkiosk.menu.MenuViewModel
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepository
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepositoryImpl
import com.robinmaneiro.orderkiosk.menu.usecase.GetProductExtendedInfoUseCase
import com.robinmaneiro.orderkiosk.menu.usecase.GetMenuCategoriesUseCase
import com.robinmaneiro.orderkiosk.menu.usecase.GetProductsByCategoryUseCase
import com.robinmaneiro.orderkiosk.orderhistory.OrderHistoryViewModel
import com.robinmaneiro.orderkiosk.util.DataStoreRepository
import com.robinmaneiro.orderkiosk.util.DataStoreRepositoryImpl
import com.robinmaneiro.orderkiosk.util.EncryptionUtil.initialize
import com.robinmaneiro.orderkiosk.welcome.WelcomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModules = module {
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::MenuViewModel)
    viewModelOf(::BagViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::CouponsViewModel)
    viewModelOf(::OrderHistoryViewModel)
}

val useCaseModules = module {
    factoryOf(::GetMenuCategoriesUseCase)
    factoryOf(::GetProductsByCategoryUseCase)
    factoryOf(::GetProductExtendedInfoUseCase)

    factoryOf(::GetBagUseCase)
    factoryOf(::AddToBagUseCase)
    factoryOf(::UpdateBagItemUseCase)
    factoryOf(::RemoveFromBagUseCase)
    factoryOf(::RemoveAllBagItemsUseCase)
}

val repositoryModules = module {
    single<DataStoreRepository> { DataStoreRepositoryImpl(get()) }
    single<MenuRepository> { MenuRepositoryImpl() }
    single<BagRepository> { BagRepositoryImpl() }
}

val tinkModule = module {
    single {
        initialize(get())
    }
}
