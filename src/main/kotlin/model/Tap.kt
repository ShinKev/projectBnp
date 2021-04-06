package model

data class Tap(
    val unixTimestamp: Long,
    val customerId: Long,
    val station: Station
)

