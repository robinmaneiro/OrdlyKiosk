package com.robinmaneiro.orderkiosk.bag.repository

interface BagRepository {
    fun fetchBagItems()

    fun addToBag(productId: String)

    fun removeFromBag(bagItemId: String)

    fun updateBagItem(bagItemId: String)
}
