package process

import model.Station
import model.Zone

interface TripCostCalculatorInterface {
    fun calculateTripCost(firstZone: Zone, secondZone: Zone): Int
    fun findLowestCostZones(firstStation: Station, secondStation: Station): Pair<Zone, Zone>
}