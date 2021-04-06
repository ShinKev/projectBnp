package model

data class CustomerSummary(
    val customerId: Long,
    var totalCostInCents: Int = 0,
    val trips: List<OutputTrip>
) {
    fun computeTotalCostInCents() {
        totalCostInCents = trips.sumOf { it.costInCents }
    }
}
