package store.model

import camp.nextstep.edu.missionutils.DateTimes
import java.time.LocalDateTime

class PromotionType(
    val name: String,
    val buyQuantity: Int,
    val freeQuantity: Int,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
) {
    fun isPromotionActive(): Boolean {
        val today = DateTimes.now()
        val isPromotionActiveToday = today.isAfter(startDate) && today.isBefore(endDate)
        return isPromotionActiveToday
    }
}