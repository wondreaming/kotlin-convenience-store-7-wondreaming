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
    // 오늘이 프로모션 날짜에 포함이 되는 지 확이하는 로직
    fun isPromotionActive(): Boolean {
        val today = DateTimes.now()
        val isPromotionActiveToday = today.isAfter(startDate) && today.isBefore(endDate)
        return isPromotionActiveToday
    }

    fun calculateBonusQuantity(purchasedQuantity: Int): Int = (purchasedQuantity / buyQuantity) * freeQuantity
}