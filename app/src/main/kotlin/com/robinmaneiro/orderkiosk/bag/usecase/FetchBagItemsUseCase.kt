package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class FetchBagItemsUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke() {

    }
}
