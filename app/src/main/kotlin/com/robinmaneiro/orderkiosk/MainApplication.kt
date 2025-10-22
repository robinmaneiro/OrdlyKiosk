package com.robinmaneiro.orderkiosk

import android.app.Application
import com.robinmaneiro.orderkiosk.account.guestsession.usecase.CreateGuestSessionUseCase
import com.robinmaneiro.orderkiosk.account.guestsession.usecase.RefreshGuestSessionUseCase
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.datastore.EncryptionUtil
import com.robinmaneiro.orderkiosk.koin.repositoryModules
import com.robinmaneiro.orderkiosk.koin.useCaseModules
import com.robinmaneiro.orderkiosk.koin.viewModelModules
import com.robinmaneiro.orderkiosk.networking.NetworkManager
import com.robinmaneiro.orderkiosk.util.TokenManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG) { TODO: Uncomment when adding build types to gradle
        Timber.plant(Timber.DebugTree())
//        }
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(
                viewModelModules,
                repositoryModules,
                useCaseModules
            ))
        }
        EncryptionUtil.initialize(this)

        val dataStore: DataStoreRepository = GlobalContext.get().get()
        val refreshGuestSessionUseCase: RefreshGuestSessionUseCase = GlobalContext.get().get()

        NetworkManager.initialize(this, dataStore, refreshGuestSessionUseCase)

        val createGuestSessionUseCase: CreateGuestSessionUseCase = GlobalContext.get().get()
        TokenManager.initialize(dataStore, createGuestSessionUseCase)
    }
}
