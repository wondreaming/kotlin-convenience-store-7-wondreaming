package store.model

import java.time.LocalDate
import java.time.LocalDateTime

class PromotionType(
    val name: String,
    val buyQuantity: Int,
    val freeQuantity: Int,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
) {
}