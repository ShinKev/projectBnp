package model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Tap(
    val unixTimestamp: Long,
    val customerId: Long,
    val station: Station
)

