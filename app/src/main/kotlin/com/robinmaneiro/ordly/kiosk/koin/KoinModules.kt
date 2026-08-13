package com.robinmaneiro.ordly.kiosk.koin

import com.robinmaneiro.ordly.kiosk.BaseViewModel
import com.robinmaneiro.ordly.kiosk.account.accountdetails.AccountDetailsUseCase
import com.robinmaneiro.ordly.kiosk.account.login.LoginViewModel
import com.robinmaneiro.ordly.kiosk.account.personaldetails.MyAccountViewModel
import com.robinmaneiro.ordly.kiosk.account.personaldetails.usecase.UpdateDateOfBirthUseCase
import com.robinmaneiro.ordly.kiosk.account.personaldetails.usecase.UpdateEmailAddressUseCase
import com.robinmaneiro.ordly.kiosk.account.personaldetails.usecase.UpdatePhoneNumberUseCase
import com.robinmaneiro.ordly.kiosk.account.registration.RegistrationViewModel
import com.robinmaneiro.ordly.kiosk.account.registration.usecase.RegisterAccountUseCase
import com.robinmaneiro.ordly.kiosk.account.repository.AccountRepository
import com.robinmaneiro.ordly.kiosk.account.repository.AccountRepositoryImpl
import com.robinmaneiro.ordly.kiosk.auth.guestsession.usecase.CreateGuestSessionUseCase
import com.robinmaneiro.ordly.kiosk.auth.guestsession.usecase.GuestSessionDetailsUseCase
import com.robinmaneiro.ordly.kiosk.auth.guestsession.usecase.RefreshGuestSessionUseCase
import com.robinmaneiro.ordly.kiosk.auth.repository.AuthRepository
import com.robinmaneiro.ordly.kiosk.auth.repository.AuthRepositoryImpl
import com.robinmaneiro.ordly.kiosk.auth.usecase.LoginUserUseCase
import com.robinmaneiro.ordly.kiosk.auth.usecase.RefreshTokenUseCase
import com.robinmaneiro.ordly.kiosk.bag.BagViewModel
import com.robinmaneiro.ordly.kiosk.bag.repository.BagRepository
import com.robinmaneiro.ordly.kiosk.bag.repository.BagRepositoryImpl
import com.robinmaneiro.ordly.kiosk.bag.usecase.AddToBagUseCase
import com.robinmaneiro.ordly.kiosk.bag.usecase.BagSelectorUseCase
import com.robinmaneiro.ordly.kiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.ordly.kiosk.bag.usecase.MergeBagsUseCase
import com.robinmaneiro.ordly.kiosk.bag.usecase.RemoveAllBagItemsUseCase
import com.robinmaneiro.ordly.kiosk.bag.usecase.RemoveFromBagUseCase
import com.robinmaneiro.ordly.kiosk.bag.usecase.UpdateBagItemUseCase
import com.robinmaneiro.ordly.kiosk.checkout.CheckoutViewModel
import com.robinmaneiro.ordly.kiosk.coupons.CouponsViewModel
import com.robinmaneiro.ordly.kiosk.datastore.DataStoreRepository
import com.robinmaneiro.ordly.kiosk.datastore.DataStoreRepositoryImpl
import com.robinmaneiro.ordly.kiosk.menu.MenuViewModel
import com.robinmaneiro.ordly.kiosk.menu.repository.MenuRepository
import com.robinmaneiro.ordly.kiosk.menu.repository.MenuRepositoryImpl
import com.robinmaneiro.ordly.kiosk.menu.usecase.GetMenuCategoriesUseCase
import com.robinmaneiro.ordly.kiosk.menu.usecase.GetProductExtendedInfoUseCase
import com.robinmaneiro.ordly.kiosk.menu.usecase.GetProductsByCategoryUseCase
import com.robinmaneiro.ordly.kiosk.networking.AppTokenRefresher
import com.robinmaneiro.ordly.kiosk.networking.ConnectivityObserver
import com.robinmaneiro.ordly.kiosk.networking.DataStoreTokenProvider
import com.robinmaneiro.ordly.kiosk.networking.NetworkManager
import com.robinmaneiro.ordly.kiosk.networking.TokenProvider
import com.robinmaneiro.ordly.kiosk.networking.TokenRefresher
import com.robinmaneiro.ordly.kiosk.offers.OffersViewModel
import com.robinmaneiro.ordly.kiosk.orderhistory.OrderHistoryViewModel
import com.robinmaneiro.ordly.kiosk.ordersummary.OrderSummaryViewModel
import com.robinmaneiro.ordly.kiosk.usecase.StartAgainUseCase
import com.robinmaneiro.ordly.kiosk.welcome.WelcomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModules = module {
    viewModelOf(::BaseViewModel)
    viewModelOf(::OffersViewModel)
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::MenuViewModel)
    viewModelOf(::BagViewModel)
    viewModelOf(::CheckoutViewModel)
    viewModelOf(::OrderSummaryViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::MyAccountViewModel)
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

    factoryOf(::UpdateDateOfBirthUseCase)
    factoryOf(::UpdateEmailAddressUseCase)
    factoryOf(::UpdatePhoneNumberUseCase)

    factoryOf(::BagSelectorUseCase)
    factoryOf(::GetBagUseCase)
    factoryOf(::AddToBagUseCase)
    factoryOf(::UpdateBagItemUseCase)
    factoryOf(::MergeBagsUseCase)
    factoryOf(::RemoveFromBagUseCase)
    factoryOf(::RemoveAllBagItemsUseCase)
    factoryOf(::StartAgainUseCase)
}

val repositoryModules = module {
    single { ConnectivityObserver(androidContext()) }
    single<TokenProvider> { DataStoreTokenProvider(get()) }
    single<TokenRefresher> { AppTokenRefresher(lazy { get() }, lazy { get() }) }
    single { NetworkManager(androidContext(), get(), get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<AccountRepository> { AccountRepositoryImpl(get()) }
    single<DataStoreRepository> { DataStoreRepositoryImpl(get()) }
    single<MenuRepository> { MenuRepositoryImpl(get()) }
    single<BagRepository> { BagRepositoryImpl(get()) }
}
