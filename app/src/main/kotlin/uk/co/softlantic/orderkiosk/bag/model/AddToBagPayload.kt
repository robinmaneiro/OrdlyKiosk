package uk.co.softlantic.orderkiosk.bag.model

data class AddToBagPayload(
    val productId: String,
    val quantity: Int
)
