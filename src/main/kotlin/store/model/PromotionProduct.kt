package store.model

import camp.nextstep.edu.missionutils.DateTimes

data class PromotionProduct(
    override val price: Int,
    private var _quantity: Int,
    private var _promotionType: PromotionType
) : ProductType {
    override val quantity: Int
        get() = _quantity
    val promotionType: PromotionType
        get() = _promotionType

    // 제품이 프로모션 날짜에 속하는 지 아닌지
    fun isPromotionActive(): Boolean {
        val today = DateTimes.now()
        val isPromotionActiveToday = today.isAfter(_promotionType.startDate) && today.isBefore(promotionType.endDate)
        return isPromotionActiveToday
    }

}