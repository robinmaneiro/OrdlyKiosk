package uk.co.softlantic.orderkiosk.usecase

import uk.co.softlantic.orderkiosk.auth.guestsession.usecase.CreateGuestSessionUseCase
import uk.co.softlantic.orderkiosk.bag.usecase.RemoveAllBagItemsUseCase
import uk.co.softlantic.orderkiosk.datastore.DataStoreRepository

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
