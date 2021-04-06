package process

import model.CustomerSummary
import model.Tap
import model.Trip

interface ProcessTapInterface {
    fun separateTapsByCustomer(tapList: List<Tap>): Map<Long, List<Tap>>
    fun computeCustomerSummary(customerId: Long, tapList: List<Tap>): CustomerSummary
    fun computeTrips(tapList: List<Tap>): List<Trip>
    fun computeTrip(firstTap: Tap, secondTap: Tap): Trip
}