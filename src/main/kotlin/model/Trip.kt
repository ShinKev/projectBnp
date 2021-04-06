package model

data class Trip(
    val stationStart: Station,
    val stationEnd: Station,
    val startedJourneyAt: Long,
    val costInCents: Int,
    val zoneFrom: Zone,
    val zoneTo: Zone
) {
    fun toOutputTrip() =
        OutputTrip(stationStart, stationEnd, startedJourneyAt, costInCents, zoneFrom.getZoneInt(), zoneTo.getZoneInt())
}

data class OutputTrip(
    val stationStart: Station,
    val stationEnd: Station,
    val startedJourneyAt: Long,
    val costInCents: Int,
    val zoneFrom: Int,
    val zoneTo: Int
)


