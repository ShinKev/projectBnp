fun main(args: Array<String>) {
    when {
        args.size < 2 -> println("We need two parameters! The input file path and the output file path!")
        args.size > 2 -> println("We need only two parameters! The input file path and the output file path!")
        else -> {
            println("Input file path is: " + args[0])
            println("Output file path is: " + args[1])
            println("Computing is ongoing... Please wait...")
        }
    }
}