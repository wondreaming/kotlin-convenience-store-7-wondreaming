package store.model

import java.time.LocalDate

class PromotionType(
    val name: String,
    val buyQuantity: Int,
    val freeQuantity: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
) {
}