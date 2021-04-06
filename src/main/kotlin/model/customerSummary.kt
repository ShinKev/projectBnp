package model

data class customerSummary(
    val customerId: Long,
    var totalCostInCents: Int = 0,
    val trips: List<OutputTrip>
) {
    fun computeTotalCostInCents() {
        totalCostInCents = trips.sumOf { it.costInCents }
    }
}
