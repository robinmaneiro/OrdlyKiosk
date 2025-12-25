package com.robinmaneiro.orderkiosk.usecase

import com.robinmaneiro.orderkiosk.auth.guestsession.usecase.CreateGuestSessionUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveAllBagItemsUseCase
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository

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
