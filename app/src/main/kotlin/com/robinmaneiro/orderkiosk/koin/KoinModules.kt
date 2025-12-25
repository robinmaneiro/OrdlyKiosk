package com.robinmaneiro.orderkiosk.koin

import com.robinmaneiro.orderkiosk.account.accountdetails.AccountDetailsUseCase
import com.robinmaneiro.orderkiosk.account.login.LoginViewModel
import com.robinmaneiro.orderkiosk.account.personaldetails.MyAccountViewModel
import com.robinmaneiro.orderkiosk.account.personaldetails.usecase.UpdateDateOfBirthUseCase
import com.robinmaneiro.orderkiosk.account.personaldetails.usecase.UpdateEmailAddressUseCase
import com.robinmaneiro.orderkiosk.account.personaldetails.usecase.UpdatePhoneNumberUseCase
import com.robinmaneiro.orderkiosk.account.registration.RegistrationViewModel
import com.robinmaneiro.orderkiosk.account.registration.usecase.RegisterAccountUseCase
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.account.repository.AccountRepositoryImpl
import com.robinmaneiro.orderkiosk.auth.guestsession.usecase.CreateGuestSessionUseCase
import com.robinmaneiro.orderkiosk.auth.guestsession.usecase.GuestSessionDetailsUseCase
import com.robinmaneiro.orderkiosk.auth.guestsession.usecase.RefreshGuestSessionUseCase
import com.robinmaneiro.orderkiosk.auth.repository.AuthRepository
import com.robinmaneiro.orderkiosk.auth.repository.AuthRepositoryImpl
import com.robinmaneiro.orderkiosk.auth.usecase.LoginUserUseCase
import com.robinmaneiro.orderkiosk.auth.usecase.RefreshTokenUseCase
import com.robinmaneiro.orderkiosk.bag.BagViewModel
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository
import com.robinmaneiro.orderkiosk.bag.repository.BagRepositoryImpl
import com.robinmaneiro.orderkiosk.bag.usecase.AddToBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.BagSelectorUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.MergeBagsUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveAllBagItemsUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveFromBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.UpdateBagItemUseCase
import com.robinmaneiro.orderkiosk.checkout.CheckoutViewModel
import com.robinmaneiro.orderkiosk.coupons.CouponsViewModel
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepositoryImpl
import com.robinmaneiro.orderkiosk.menu.MenuViewModel
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepository
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepositoryImpl
import com.robinmaneiro.orderkiosk.menu.usecase.GetMenuCategoriesUseCase
import com.robinmaneiro.orderkiosk.menu.usecase.GetProductExtendedInfoUseCase
import com.robinmaneiro.orderkiosk.menu.usecase.GetProductsByCategoryUseCase
import com.robinmaneiro.orderkiosk.offers.OffersViewModel
import com.robinmaneiro.orderkiosk.orderhistory.OrderHistoryViewModel
import com.robinmaneiro.orderkiosk.ordersummary.OrderSummaryViewModel
import com.robinmaneiro.orderkiosk.usecase.StartAgainUseCase
import com.robinmaneiro.orderkiosk.welcome.WelcomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModules = module {
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
    single<AuthRepository> { AuthRepositoryImpl() }
    single<AccountRepository> { AccountRepositoryImpl() }
    single<DataStoreRepository> { DataStoreRepositoryImpl(get()) }
    single<MenuRepository> { MenuRepositoryImpl() }
    single<BagRepository> { BagRepositoryImpl() }
}
