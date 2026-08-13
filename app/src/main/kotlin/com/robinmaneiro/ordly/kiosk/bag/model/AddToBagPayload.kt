package com.robinmaneiro.ordly.kiosk.bag.model

data class AddToBagPayload(
    val productId: String,
    val quantity: Int
)
