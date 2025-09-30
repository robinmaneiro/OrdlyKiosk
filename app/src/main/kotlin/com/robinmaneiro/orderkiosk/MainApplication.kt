package com.robinmaneiro.orderkiosk

import android.app.Application
import com.robinmaneiro.orderkiosk.koin.repositoryModules
import com.robinmaneiro.orderkiosk.koin.tinkModule
import com.robinmaneiro.orderkiosk.koin.useCaseModules
import com.robinmaneiro.orderkiosk.koin.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(
                tinkModule,
                viewModelModules,
                repositoryModules,
                useCaseModules
            ))
        }
    }
}
