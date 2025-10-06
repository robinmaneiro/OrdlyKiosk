package com.robinmaneiro.orderkiosk.bag.model

data class AddToBagPayload(
    val productId: String,
    val quantity: Int
)
