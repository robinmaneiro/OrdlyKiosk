package uk.co.softlantic.orderkiosk

import android.app.Application
import uk.co.softlantic.orderkiosk.auth.guestsession.usecase.CreateGuestSessionUseCase
import uk.co.softlantic.orderkiosk.auth.guestsession.usecase.RefreshGuestSessionUseCase
import uk.co.softlantic.orderkiosk.auth.usecase.RefreshTokenUseCase
import uk.co.softlantic.orderkiosk.datastore.DataStoreRepository
import uk.co.softlantic.orderkiosk.datastore.EncryptionUtil
import uk.co.softlantic.orderkiosk.koin.repositoryModules
import uk.co.softlantic.orderkiosk.koin.useCaseModules
import uk.co.softlantic.orderkiosk.koin.viewModelModules
import uk.co.softlantic.orderkiosk.networking.NetworkManager
import uk.co.softlantic.orderkiosk.util.SessionManager
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
