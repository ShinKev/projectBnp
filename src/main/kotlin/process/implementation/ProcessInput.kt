package process.implementation

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import model.CustomerSummary
import model.Tap
import process.ProcessInputInterface
import java.io.File

class ProcessInput : ProcessInputInterface {
    private val processTap = ProcessTap()
    private val mapper = jacksonObjectMapper()

    override fun computeOutputFile(inputPath: String, outputPath: String) {
        val tapList = extractInputData(inputPath)
        val customerSummaryList = computeOutputData(tapList)
        val outputData = mapOf(Pair("customerSummaries", customerSummaryList))
        val jsonOutputData = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(outputData)
        File(outputPath).writeText(jsonOutputData)
    }

    override fun extractInputData(inputPath: String): List<Tap> {
        val json = File(inputPath).readText(Charsets.UTF_8)
        val inputData: Map<String, List<Tap>> = mapper.readValue(json)
        return inputData.getOrDefault("taps", listOf())
    }

    override fun computeOutputData(tapList: List<Tap>): List<CustomerSummary> {
        val mapTapsByCustomer = processTap.separateTapsByCustomer(tapList)
        return mapTapsByCustomer.map { processTap.computeCustomerSummary(it.key, it.value) }
    }
}