import process.implementation.ProcessInput

fun main(args: Array<String>) {
    when {
        args.size < 2 -> println("We need two parameters! The input file path and the output file path!")
        args.size > 2 -> println("We need only two parameters! The input file path and the output file path!")
        else -> {
            val inputFilePath = args[0]
            val outputFilePath = args[1]

            println("Input file path is: $inputFilePath")
            println("Output file path is: $outputFilePath")
            println("Computing is ongoing... Please wait...")

            val processInput = ProcessInput()
            processInput.computeOutputFile(inputFilePath, outputFilePath)

            println("Output file computed! You can now check your output file.")
        }
    }
}