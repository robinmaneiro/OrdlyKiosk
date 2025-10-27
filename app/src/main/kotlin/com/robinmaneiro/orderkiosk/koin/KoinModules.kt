package com.robinmaneiro.orderkiosk.koin

import com.robinmaneiro.orderkiosk.account.guestsession.usecase.CreateGuestSessionUseCase
import com.robinmaneiro.orderkiosk.account.guestsession.usecase.GuestSessionDetailsUseCase
import com.robinmaneiro.orderkiosk.account.guestsession.usecase.RefreshGuestSessionUseCase
import com.robinmaneiro.orderkiosk.account.login.LoginViewModel
import com.robinmaneiro.orderkiosk.account.login.usecase.AccountDetailsUseCase
import com.robinmaneiro.orderkiosk.account.login.usecase.LoginUserUseCase
import com.robinmaneiro.orderkiosk.account.login.usecase.RefreshTokenUseCase
import com.robinmaneiro.orderkiosk.account.registration.RegistrationViewModel
import com.robinmaneiro.orderkiosk.account.registration.usecase.RegisterAccountUseCase
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.account.repository.AccountRepositoryImpl
import com.robinmaneiro.orderkiosk.bag.BagViewModel
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository
import com.robinmaneiro.orderkiosk.bag.repository.BagRepositoryImpl
import com.robinmaneiro.orderkiosk.bag.usecase.AddToBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.BagSelectorUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveAllBagItemsUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveFromBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.UpdateBagItemUseCase
import com.robinmaneiro.orderkiosk.coupons.CouponsViewModel
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepositoryImpl
import com.robinmaneiro.orderkiosk.menu.MenuViewModel
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepository
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepositoryImpl
import com.robinmaneiro.orderkiosk.menu.usecase.GetMenuCategoriesUseCase
import com.robinmaneiro.orderkiosk.menu.usecase.GetProductExtendedInfoUseCase
import com.robinmaneiro.orderkiosk.menu.usecase.GetProductsByCategoryUseCase
import com.robinmaneiro.orderkiosk.orderhistory.OrderHistoryViewModel
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
    factoryOf(::RegisterAccountUseCase)
    factoryOf(::LoginUserUseCase)
    factoryOf(::RefreshTokenUseCase)
    factoryOf(::AccountDetailsUseCase)

    factoryOf(::CreateGuestSessionUseCase)
    factoryOf(::RefreshGuestSessionUseCase)
    factoryOf(::GuestSessionDetailsUseCase)

    factoryOf(::GetMenuCategoriesUseCase)
    factoryOf(::GetProductsByCategoryUseCase)
    factoryOf(::GetProductExtendedInfoUseCase)

    factoryOf(::BagSelectorUseCase)
    factoryOf(::GetBagUseCase)
    factoryOf(::AddToBagUseCase)
    factoryOf(::UpdateBagItemUseCase)
    factoryOf(::RemoveFromBagUseCase)
    factoryOf(::RemoveAllBagItemsUseCase)
}

val repositoryModules = module {
    single<AccountRepository> { AccountRepositoryImpl() }
    single<DataStoreRepository> { DataStoreRepositoryImpl(get()) }
    single<MenuRepository> { MenuRepositoryImpl() }
    single<BagRepository> { BagRepositoryImpl() }
}
