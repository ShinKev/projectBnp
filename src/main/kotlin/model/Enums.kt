package model

enum class Station {
    A, B, C, D, E, F, G, H, I
}

enum class Zone(val stationList: List<Station>) {
    ONE(listOf(Station.A, Station.B)),
    TWO(listOf(Station.C, Station.D, Station.E)),
    THREE(listOf(Station.C, Station.E, Station.F)),
    FOUR(listOf(Station.F, Station.G, Station.H, Station.I));

    fun getZoneInt(): Int {
        return when (this) {
            ONE -> 1
            TWO -> 2
            THREE -> 3
            FOUR -> 4
        }
    }
}