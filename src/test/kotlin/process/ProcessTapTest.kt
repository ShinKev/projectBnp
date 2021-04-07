package process

import model.*
import process.implementation.ProcessTap
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ProcessTapTest {

    private lateinit var processTap: ProcessTapInterface

    @BeforeTest
    fun setup() {
        processTap = ProcessTap()
    }

    @Test
    fun `function separateTapsByCustomer separates taps by customer id`() {
        // given
        val tap1 = Tap(10, 1, Station.A)
        val tap2 = Tap(20, 2, Station.C)
        val tap3 = Tap(30, 3, Station.D)
        val tap4 = Tap(40, 1, Station.B)
        val tap5 = Tap(50, 2, Station.F)
        val tap6 = Tap(60, 3, Station.E)
        val tapList = listOf(tap1, tap2, tap3, tap4, tap5, tap6)

        // when
        val mapTapsByCustomer = processTap.separateTapsByCustomer(tapList)

        // then
        assertEquals(mapTapsByCustomer[1], listOf(tap1, tap4))
        assertEquals(mapTapsByCustomer[2], listOf(tap2, tap5))
        assertEquals(mapTapsByCustomer[3], listOf(tap3, tap6))
    }

    @Test
    fun `the function computeCustomerSummary computes a customerSummary with the expected field values`() {
        // given
        val customerId = 1L
        val tap1 = Tap(10, customerId, Station.A)
        val tap2 = Tap(20, customerId, Station.C)
        val tap3 = Tap(30, customerId, Station.C)
        val tap4 = Tap(40, customerId, Station.G)
        val tapList = listOf(tap1, tap2, tap3, tap4)

        // when
        val customerSummary = processTap.computeCustomerSummary(customerId, tapList)

        // then
        assertEquals(customerSummary.customerId, 1)
        assertEquals(customerSummary.totalCostInCents, 440)
        assertEquals(customerSummary.trips[0], OutputTrip(Station.A, Station.C, 10, 240, 1, 2))
        assertEquals(customerSummary.trips[1], OutputTrip(Station.C, Station.G, 30, 200, 3, 4))
    }

    @Test
    fun `the function computeTrips computes a list of trips with the expected values`() {
        // given
        val customerId = 1L
        val tap1 = Tap(5, customerId, Station.A)
        val tap2 = Tap(15, customerId, Station.F)
        val tap3 = Tap(25, customerId, Station.F)
        val tap4 = Tap(35, customerId, Station.A)
        val tapList = listOf(tap1, tap2, tap3, tap4)

        // when
        val tripList = processTap.computeTrips(tapList)

        // then
        assertEquals(tripList[0], Trip(Station.A, Station.F, 5, 280, Zone.ONE, Zone.THREE))
        assertEquals(tripList[1], Trip(Station.F, Station.A, 25, 280, Zone.THREE, Zone.ONE))
    }

    @Test
    fun `the function computeTrip computes the expected trip`() {
        // given
        val customerId = 1L
        val tap1 = Tap(50, customerId, Station.G)
        val tap2 = Tap(100, customerId, Station.E)

        // when
        val trip = processTap.computeTrip(tap1, tap2)

        // then
        assertEquals(trip, Trip(Station.G, Station.E, 50, 200, Zone.FOUR, Zone.THREE))
    }
}