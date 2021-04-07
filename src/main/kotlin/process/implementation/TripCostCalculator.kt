package process.implementation

import model.Station
import model.Zone
import process.TripCostCalculatorInterface

class TripCostCalculator : TripCostCalculatorInterface {
    override fun calculateTripCost(firstZone: Zone, secondZone: Zone): Int {
        val zoneOneAndTwoList = listOf(Zone.ONE, Zone.TWO)
        val zoneThreeAndFourList = listOf(Zone.THREE, Zone.FOUR)

        if (zoneThreeAndFourList.containsAll(listOf(firstZone, secondZone))) return 200
        if (zoneOneAndTwoList.containsAll(listOf(firstZone, secondZone))) return 240
        if (firstZone in zoneOneAndTwoList && secondZone == Zone.THREE) return 280
        if (secondZone in zoneOneAndTwoList && firstZone == Zone.THREE) return 280
        return 300
    }

    override fun findLowestCostZones(firstStation: Station, secondStation: Station): Pair<Zone, Zone> {
        var zones = withinZoneThreeAndFour(firstStation, secondStation)
        if (zones == null) zones = withinZoneOneAndTwo(firstStation, secondStation)
        if (zones == null) zones = inZoneThreeToZoneOneTwoOrReverse(firstStation, secondStation)
        if (zones == null) zones = inZoneFourToZoneOneTwoOrReverse(firstStation, secondStation)
        return zones
    }

    private fun withinZoneThreeAndFour(firstStation: Station, secondStation: Station): Pair<Zone, Zone>? {
        if (Zone.FOUR.stationList.containsAll(listOf(firstStation, secondStation)))
            return Pair(Zone.FOUR, Zone.FOUR)
        if (Zone.THREE.stationList.containsAll(listOf(firstStation, secondStation)))
            return Pair(Zone.THREE, Zone.THREE)
        if (firstStation in Zone.FOUR.stationList && secondStation in Zone.THREE.stationList)
            return Pair(Zone.FOUR, Zone.THREE)
        if (firstStation in Zone.THREE.stationList && secondStation in Zone.FOUR.stationList)
            return Pair(Zone.THREE, Zone.FOUR)
        return null
    }

    private fun withinZoneOneAndTwo(firstStation: Station, secondStation: Station): Pair<Zone, Zone>? {
        if (Zone.TWO.stationList.containsAll(listOf(firstStation, secondStation)))
            return Pair(Zone.TWO, Zone.TWO)
        if (Zone.ONE.stationList.containsAll(listOf(firstStation, secondStation)))
            return Pair(Zone.ONE, Zone.ONE)
        if (firstStation in Zone.TWO.stationList && secondStation in Zone.ONE.stationList)
            return Pair(Zone.TWO, Zone.ONE)
        if (firstStation in Zone.ONE.stationList && secondStation in Zone.TWO.stationList)
            return Pair(Zone.ONE, Zone.TWO)
        return null
    }

    private fun inZoneThreeToZoneOneTwoOrReverse(firstStation: Station, secondStation: Station): Pair<Zone, Zone>? {
        if (firstStation in Zone.THREE.stationList) {
            if (secondStation in Zone.TWO.stationList) return Pair(Zone.THREE, Zone.TWO)
            if (secondStation in Zone.ONE.stationList) return Pair(Zone.THREE, Zone.ONE)
        }
        if (secondStation in Zone.THREE.stationList) {
            if (firstStation in Zone.TWO.stationList) return Pair(Zone.TWO, Zone.THREE)
            if (firstStation in Zone.ONE.stationList) return Pair(Zone.ONE, Zone.THREE)
        }
        return null
    }

    private fun inZoneFourToZoneOneTwoOrReverse(firstStation: Station, secondStation: Station): Pair<Zone, Zone> {
        if (firstStation in Zone.FOUR.stationList) {
            if (secondStation in Zone.TWO.stationList) return Pair(Zone.FOUR, Zone.TWO)
            if (secondStation in Zone.ONE.stationList) return Pair(Zone.FOUR, Zone.TWO)
        }

        // If we arrive here, that means secondStation is in Zone.FOUR
        if (firstStation in Zone.TWO.stationList) return Pair(Zone.TWO, Zone.FOUR)

        // If we arrive here, that means firstStation is in Zone.ONE
        return Pair(Zone.ONE, Zone.FOUR)
    }
}