package process

import model.Station
import model.Zone
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TripCostCalculatorTest {

    private lateinit var tripCostCalculator: TripCostCalculatorInterface

    @BeforeTest
    fun setup() {
        // Here we will create the tripCostCalculator instance
    }

    @Test
    fun `lowest cost zones for station A to station B are zone 1 to zone 1`() {
        // given
        val firstStation = Station.A
        val secondStation = Station.B

        // when
        val zones = tripCostCalculator.findLowestCostZones(firstStation, secondStation)

        // then
        assertEquals(zones, Pair(Zone.ONE, Zone.ONE))
    }

    @Test
    fun `lowest cost zones for station C to station D are zone 2 to zone 2`() {
        // given
        val firstStation = Station.C
        val secondStation = Station.D

        // when
        val zones = tripCostCalculator.findLowestCostZones(firstStation, secondStation)

        // then
        assertEquals(zones, Pair(Zone.TWO, Zone.TWO))
    }

    @Test
    fun `lowest cost zones for station C to station (E or F) are zone 3 to zone 3`() {
        // given
        val firstStation = Station.C
        val secondStation = Station.E
        val thirdStation = Station.F

        // when
        val zones = tripCostCalculator.findLowestCostZones(firstStation, secondStation)
        val zones2 = tripCostCalculator.findLowestCostZones(firstStation, thirdStation)

        // then
        assertEquals(zones, Pair(Zone.THREE, Zone.THREE))
        assertEquals(zones2, Pair(Zone.THREE, Zone.THREE))
    }

    @Test
    fun `lowest cost zones for station (F or G) to station I are zone 4 to zone 4`() {
        // given
        val firstStation = Station.F
        val secondStation = Station.G
        val thirdStation = Station.I

        // when
        val zones = tripCostCalculator.findLowestCostZones(firstStation, thirdStation)
        val zones2 = tripCostCalculator.findLowestCostZones(secondStation, thirdStation)

        // then
        assertEquals(zones, Pair(Zone.FOUR, Zone.FOUR))
        assertEquals(zones2, Pair(Zone.FOUR, Zone.FOUR))
    }

    @Test
    fun `lowest cost zones for station A to station (C or D) are zone 1 to zone 2`() {
        // given
        val firstStation = Station.A
        val secondStation = Station.C
        val thirdStation = Station.D

        // when
        val zones = tripCostCalculator.findLowestCostZones(firstStation, secondStation)
        val zones2 = tripCostCalculator.findLowestCostZones(firstStation, thirdStation)

        // then
        assertEquals(zones, Pair(Zone.ONE, Zone.TWO))
        assertEquals(zones2, Pair(Zone.ONE, Zone.TWO))
    }

    @Test
    fun `lowest cost zones for station B to station F are zone 1 to zone 3`() {
        // given
        val firstStation = Station.B
        val secondStation = Station.F

        // when
        val zones = tripCostCalculator.findLowestCostZones(firstStation, secondStation)

        // then
        assertEquals(zones, Pair(Zone.ONE, Zone.THREE))
    }

    @Test
    fun `lowest cost zones for station A to station G are zone 1 to zone 4`() {
        // given
        val firstStation = Station.A
        val secondStation = Station.G

        // when
        val zones = tripCostCalculator.findLowestCostZones(firstStation, secondStation)

        // then
        assertEquals(zones, Pair(Zone.ONE, Zone.FOUR))
    }

    @Test
    fun `lowest cost zones for station D to station F are zone 2 to zone 3`() {
        // given
        val firstStation = Station.D
        val secondStation = Station.F

        // when
        val zones = tripCostCalculator.findLowestCostZones(firstStation, secondStation)

        // then
        assertEquals(zones, Pair(Zone.TWO, Zone.THREE))
    }

    @Test
    fun `lowest cost zones for station D to station G are zone 2 to zone 4`() {
        // given
        val firstStation = Station.D
        val secondStation = Station.G

        // when
        val zones = tripCostCalculator.findLowestCostZones(firstStation, secondStation)

        // then
        assertEquals(zones, Pair(Zone.TWO, Zone.FOUR))
    }

    @Test
    fun `lowest cost zones for station E to station I are zone 3 to zone 4`() {
        // given
        val firstStation = Station.E
        val secondStation = Station.I

        // when
        val zones = tripCostCalculator.findLowestCostZones(firstStation, secondStation)

        // then
        assertEquals(zones, Pair(Zone.THREE, Zone.FOUR))
    }

    @Test
    fun `calculate the right cost for a trip within zone 1 and 2`() {
        // when
        val cost = tripCostCalculator.calculateTripCost(Zone.ONE, Zone.ONE)
        val cost2 = tripCostCalculator.calculateTripCost(Zone.ONE, Zone.TWO)
        val cost3 = tripCostCalculator.calculateTripCost(Zone.TWO, Zone.TWO)

        // then
        assertEquals(cost, 240)
        assertEquals(cost2, 240)
        assertEquals(cost3, 240)
    }

    @Test
    fun `calculate the right cost for a trip within zone 3 and 4`() {
        // when
        val cost = tripCostCalculator.calculateTripCost(Zone.THREE, Zone.THREE)
        val cost2 = tripCostCalculator.calculateTripCost(Zone.THREE, Zone.FOUR)
        val cost3 = tripCostCalculator.calculateTripCost(Zone.FOUR, Zone.FOUR)

        // then
        assertEquals(cost, 200)
        assertEquals(cost2, 200)
        assertEquals(cost3, 200)
    }

    @Test
    fun `calculate the right cost for a trip between zone 3 and zone (1 or 2)`() {
        // when
        val cost = tripCostCalculator.calculateTripCost(Zone.THREE, Zone.ONE)
        val cost2 = tripCostCalculator.calculateTripCost(Zone.THREE, Zone.TWO)

        // then
        assertEquals(cost, 280)
        assertEquals(cost2, 280)
    }

    @Test
    fun `calculate the right cost for a trip between zone 4 and zone (1 or 2)`() {
        // when
        val cost = tripCostCalculator.calculateTripCost(Zone.FOUR, Zone.ONE)
        val cost2 = tripCostCalculator.calculateTripCost(Zone.FOUR, Zone.TWO)

        // then
        assertEquals(cost, 300)
        assertEquals(cost2, 300)
    }
}