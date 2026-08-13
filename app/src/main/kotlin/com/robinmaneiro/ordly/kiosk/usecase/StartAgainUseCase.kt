package com.robinmaneiro.ordly.kiosk.usecase

import com.robinmaneiro.ordly.kiosk.auth.guestsession.usecase.CreateGuestSessionUseCase
import com.robinmaneiro.ordly.kiosk.bag.usecase.RemoveAllBagItemsUseCase
import com.robinmaneiro.ordly.kiosk.datastore.DataStoreRepository

class StartAgainUseCase(
    private val dataStoreRepository: DataStoreRepository,
    private val clearAllBagItemsUseCase: RemoveAllBagItemsUseCase,
    private val createGuestSessionUseCase: CreateGuestSessionUseCase
) {
    suspend operator fun invoke() {
        clearAllBagItemsUseCase.invoke(dataStoreRepository.getGuestBagId())
        dataStoreRepository.clearDataStore()
        createGuestSessionUseCase.invoke()
    }
}
