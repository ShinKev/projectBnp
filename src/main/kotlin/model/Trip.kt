package model

data class Trip(
    val stationStart: Station,
    val stationEnd: Station,
    val startedJourneyAt: Long,
    var costInCents: Int = 0,
    var zoneFrom: Zone = Zone.ONE,
    var zoneTo: Zone = Zone.ONE
) {
    fun toOutputTrip(): OutputTrip {
        return OutputTrip(
            stationStart,
            stationEnd,
            startedJourneyAt,
            costInCents,
            zoneFrom.getZoneInt(),
            zoneTo.getZoneInt()
        )
    }
}

data class OutputTrip(
    val stationStart: Station,
    val stationEnd: Station,
    val startedJourneyAt: Long,
    val costInCents: Int,
    val zoneFrom: Int,
    val zoneTo: Int
)


