package com.robinmaneiro.orderkiosk.bag.model

class BagItemResponse: ArrayList<BagItem>()

data class BagItem(
    val itemId: String,
    val productId: String,
    val quantity: Int,
    val title: String,
    val description: String,
    val price: Double
)