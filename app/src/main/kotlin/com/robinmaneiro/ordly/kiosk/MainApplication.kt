package com.robinmaneiro.ordly.kiosk

import android.app.Application
import com.robinmaneiro.ordly.kiosk.auth.guestsession.usecase.CreateGuestSessionUseCase
import com.robinmaneiro.ordly.kiosk.datastore.DataStoreRepository
import com.robinmaneiro.ordly.kiosk.datastore.EncryptionUtil
import com.robinmaneiro.ordly.kiosk.koin.repositoryModules
import com.robinmaneiro.ordly.kiosk.koin.useCaseModules
import com.robinmaneiro.ordly.kiosk.koin.viewModelModules
import com.robinmaneiro.ordly.kiosk.util.SessionManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import timber.log.Timber

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
        val createGuestSessionUseCase: CreateGuestSessionUseCase = GlobalContext.get().get()
        SessionManager.initialize(dataStore, createGuestSessionUseCase)
    }
}
