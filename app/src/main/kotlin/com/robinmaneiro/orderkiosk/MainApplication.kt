package com.robinmaneiro.orderkiosk

import android.app.Application
import com.robinmaneiro.orderkiosk.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import timber.log.Timber
import com.robinmaneiro.orderkiosk.auth.guestsession.usecase.CreateGuestSessionUseCase
import com.robinmaneiro.orderkiosk.auth.guestsession.usecase.RefreshGuestSessionUseCase
import com.robinmaneiro.orderkiosk.auth.usecase.RefreshTokenUseCase
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.datastore.EncryptionUtil
import com.robinmaneiro.orderkiosk.koin.repositoryModules
import com.robinmaneiro.orderkiosk.koin.useCaseModules
import com.robinmaneiro.orderkiosk.koin.viewModelModules
import com.robinmaneiro.orderkiosk.networking.NetworkManager
import com.robinmaneiro.orderkiosk.util.SessionManager

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(
                    viewModelModules,
                    repositoryModules,
                    useCaseModules
                )
            )
        }
        EncryptionUtil.initialize(this)

        val dataStore: DataStoreRepository = GlobalContext.get().get()
        val refreshTokenUseCase: RefreshTokenUseCase = GlobalContext.get().get()
        val refreshGuestSessionUseCase: RefreshGuestSessionUseCase = GlobalContext.get().get()

        NetworkManager.initialize(this, dataStore, refreshTokenUseCase, refreshGuestSessionUseCase)

        val createGuestSessionUseCase: CreateGuestSessionUseCase = GlobalContext.get().get()
        SessionManager.initialize(dataStore, createGuestSessionUseCase)
    }
}
