package process

import model.OutputTrip
import model.Station
import model.Tap
import process.implementation.ProcessInput
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ProcessInputTest {

    private lateinit var processInput: ProcessInputInterface
    private val inputPath = "src/test/resources/testFile.txt"

    @BeforeTest
    fun setup() {
        processInput = ProcessInput()
    }

    @Test
    fun `the input test file is in the project test directory`() {
        val file = File(inputPath)
        assert(file.exists())
    }

    @Test
    fun `function extractInputData extracts the expected taps`() {
        // when
        val tapList = processInput.extractInputData(inputPath)

        // then
        assertEquals(tapList[0], Tap(1572242400, 1, Station.A))
        assertEquals(tapList[1], Tap(1572244200, 1, Station.D))
        assertEquals(tapList[2], Tap(1572282000, 1, Station.D))
        assertEquals(tapList[3], Tap(1572283800, 1, Station.A))
    }

    @Test
    fun `function computeOutputData computes the expected customerSummary`() {
        // given
        val tap1 = Tap(1572242400, 1, Station.A)
        val tap2 = Tap(1572244200, 1, Station.D)
        val tap3 = Tap(1572282000, 1, Station.D)
        val tap4 = Tap(1572283800, 1, Station.A)
        val tapList = listOf(tap1, tap2, tap3, tap4)

        // when
        val customerSummaryList = processInput.computeOutputData(tapList)
        val customerSummary = customerSummaryList[0]

        // then
        assertEquals(customerSummary.customerId, 1)
        assertEquals(customerSummary.totalCostInCents, 480)
        assertEquals(customerSummary.trips[0], OutputTrip(Station.A, Station.D, 1572242400, 240, 1, 2))
        assertEquals(customerSummary.trips[1], OutputTrip(Station.D, Station.A, 1572282000, 240, 2, 1))
    }

    @Test
    fun `function computeOutputFile creates an output file with the expected content`() {
        // given
        val outputPath = "src/test/resources/resultTestFile.txt"
        val expectedJson = """
            {
              "customerSummaries" : [ {
                "customerId" : 1,
                "totalCostInCents" : 480,
                "trips" : [ {
                  "stationStart" : "A",
                  "stationEnd" : "D",
                  "startedJourneyAt" : 1572242400,
                  "costInCents" : 240,
                  "zoneFrom" : 1,
                  "zoneTo" : 2
                }, {
                  "stationStart" : "D",
                  "stationEnd" : "A",
                  "startedJourneyAt" : 1572282000,
                  "costInCents" : 240,
                  "zoneFrom" : 2,
                  "zoneTo" : 1
                } ]
              } ]
            }
        """.trimIndent()

        // when
        processInput.computeOutputFile(inputPath, outputPath)

        // then
        val file = File(outputPath)
        assert(file.exists())

        val outputJson = file.readText(Charsets.UTF_8)
        assertEquals(outputJson, expectedJson)
    }
}