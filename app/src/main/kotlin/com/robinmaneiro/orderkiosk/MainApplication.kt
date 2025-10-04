package com.robinmaneiro.orderkiosk

import android.app.Application
import com.robinmaneiro.orderkiosk.koin.repositoryModules
import com.robinmaneiro.orderkiosk.koin.useCaseModules
import com.robinmaneiro.orderkiosk.koin.viewModelModules
import com.robinmaneiro.orderkiosk.networking.NetworkManager
import com.robinmaneiro.orderkiosk.util.EncryptionUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(
                viewModelModules,
                repositoryModules,
                useCaseModules
            ))
        }
        EncryptionUtil.initialize(this)
        NetworkManager.initializeChucker(this)
    }
}
