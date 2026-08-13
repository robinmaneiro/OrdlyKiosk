package com.robinmaneiro.ordly.kiosk.bag.model

data class UpdateBagItemPayload(
    val bagItemId: String,
    val quantity: Int
)
