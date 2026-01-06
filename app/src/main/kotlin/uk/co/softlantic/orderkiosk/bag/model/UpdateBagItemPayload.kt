package uk.co.softlantic.orderkiosk.bag.model

data class UpdateBagItemPayload(
    val bagItemId: String,
    val quantity: Int
)
