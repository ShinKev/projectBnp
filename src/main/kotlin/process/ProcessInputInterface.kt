package process

import model.CustomerSummary
import model.Tap

interface ProcessInputInterface {
    fun computeOutputFile(inputPath: String, outputPath: String)
    fun extractInputData(inputPath: String): List<Tap>
    fun computeOutputData(tapList: List<Tap>): List<CustomerSummary>
}