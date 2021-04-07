package process.implementation

import model.CustomerSummary
import model.Tap
import model.Trip
import process.ProcessTapInterface

class ProcessTap : ProcessTapInterface {
    private val tripCostCalculator = TripCostCalculator()

    override fun separateTapsByCustomer(tapList: List<Tap>): Map<Long, List<Tap>> {
        return tapList.groupBy { it.customerId }
    }

    override fun computeCustomerSummary(customerId: Long, tapList: List<Tap>): CustomerSummary {
        val tripList = computeTrips(tapList)
        val outputTripList = tripList.map { it.toOutputTrip() }
        val customerSummary = CustomerSummary(customerId = customerId, totalCostInCents = 0, trips = outputTripList)
        customerSummary.computeTotalCostInCents()

        return customerSummary
    }

    override fun computeTrips(tapList: List<Tap>): List<Trip> {
        val tapByPairList = tapList.chunked(2) // This will group the taps 2 by 2
        return tapByPairList.map {
            val firstTap = it[0]
            val secondTap = it[1]
            computeTrip(firstTap, secondTap)
        }
    }

    override fun computeTrip(firstTap: Tap, secondTap: Tap): Trip {
        val zones = tripCostCalculator.findLowestCostZones(
            firstStation = firstTap.station,
            secondStation = secondTap.station
        )

        val tripCost = tripCostCalculator.calculateTripCost(
            firstZone = zones.first,
            secondZone = zones.second
        )

        return Trip(
            stationStart = firstTap.station,
            stationEnd = secondTap.station,
            startedJourneyAt = firstTap.unixTimestamp,
            costInCents = tripCost,
            zoneFrom = zones.first,
            zoneTo = zones.second
        )
    }
}