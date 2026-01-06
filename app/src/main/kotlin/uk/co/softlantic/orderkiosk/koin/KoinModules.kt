package uk.co.softlantic.orderkiosk.koin

import uk.co.softlantic.orderkiosk.BaseViewModel
import uk.co.softlantic.orderkiosk.account.accountdetails.AccountDetailsUseCase
import uk.co.softlantic.orderkiosk.account.login.LoginViewModel
import uk.co.softlantic.orderkiosk.account.personaldetails.MyAccountViewModel
import uk.co.softlantic.orderkiosk.account.personaldetails.usecase.UpdateDateOfBirthUseCase
import uk.co.softlantic.orderkiosk.account.personaldetails.usecase.UpdateEmailAddressUseCase
import uk.co.softlantic.orderkiosk.account.personaldetails.usecase.UpdatePhoneNumberUseCase
import uk.co.softlantic.orderkiosk.account.registration.RegistrationViewModel
import uk.co.softlantic.orderkiosk.account.registration.usecase.RegisterAccountUseCase
import uk.co.softlantic.orderkiosk.account.repository.AccountRepository
import uk.co.softlantic.orderkiosk.account.repository.AccountRepositoryImpl
import uk.co.softlantic.orderkiosk.auth.guestsession.usecase.CreateGuestSessionUseCase
import uk.co.softlantic.orderkiosk.auth.guestsession.usecase.GuestSessionDetailsUseCase
import uk.co.softlantic.orderkiosk.auth.guestsession.usecase.RefreshGuestSessionUseCase
import uk.co.softlantic.orderkiosk.auth.repository.AuthRepository
import uk.co.softlantic.orderkiosk.auth.repository.AuthRepositoryImpl
import uk.co.softlantic.orderkiosk.auth.usecase.LoginUserUseCase
import uk.co.softlantic.orderkiosk.auth.usecase.RefreshTokenUseCase
import uk.co.softlantic.orderkiosk.bag.BagViewModel
import uk.co.softlantic.orderkiosk.bag.repository.BagRepository
import uk.co.softlantic.orderkiosk.bag.repository.BagRepositoryImpl
import uk.co.softlantic.orderkiosk.bag.usecase.AddToBagUseCase
import uk.co.softlantic.orderkiosk.bag.usecase.BagSelectorUseCase
import uk.co.softlantic.orderkiosk.bag.usecase.GetBagUseCase
import uk.co.softlantic.orderkiosk.bag.usecase.MergeBagsUseCase
import uk.co.softlantic.orderkiosk.bag.usecase.RemoveAllBagItemsUseCase
import uk.co.softlantic.orderkiosk.bag.usecase.RemoveFromBagUseCase
import uk.co.softlantic.orderkiosk.bag.usecase.UpdateBagItemUseCase
import uk.co.softlantic.orderkiosk.checkout.CheckoutViewModel
import uk.co.softlantic.orderkiosk.coupons.CouponsViewModel
import uk.co.softlantic.orderkiosk.datastore.DataStoreRepository
import uk.co.softlantic.orderkiosk.datastore.DataStoreRepositoryImpl
import uk.co.softlantic.orderkiosk.menu.MenuViewModel
import uk.co.softlantic.orderkiosk.menu.repository.MenuRepository
import uk.co.softlantic.orderkiosk.menu.repository.MenuRepositoryImpl
import uk.co.softlantic.orderkiosk.menu.usecase.GetMenuCategoriesUseCase
import uk.co.softlantic.orderkiosk.menu.usecase.GetProductExtendedInfoUseCase
import uk.co.softlantic.orderkiosk.menu.usecase.GetProductsByCategoryUseCase
import uk.co.softlantic.orderkiosk.networking.ConnectivityObserver
import uk.co.softlantic.orderkiosk.offers.OffersViewModel
import uk.co.softlantic.orderkiosk.orderhistory.OrderHistoryViewModel
import uk.co.softlantic.orderkiosk.ordersummary.OrderSummaryViewModel
import uk.co.softlantic.orderkiosk.usecase.StartAgainUseCase
import uk.co.softlantic.orderkiosk.welcome.WelcomeViewModel
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
    single{ ConnectivityObserver(androidContext()) }
    single<AuthRepository> { AuthRepositoryImpl() }
    single<AccountRepository> { AccountRepositoryImpl() }
    single<DataStoreRepository> { DataStoreRepositoryImpl(get()) }
    single<MenuRepository> { MenuRepositoryImpl() }
    single<BagRepository> { BagRepositoryImpl() }
}
