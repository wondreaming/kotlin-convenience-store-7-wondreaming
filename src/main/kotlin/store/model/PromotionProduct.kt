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

    // 고객이 프로모션 제품 수량보다 적게 가져옴-> 수량 전달
    fun missingPromotionQuantity(buyQuantity: Int): Int {
        val promotionQuantity = _promotionType.buyQuantity + _promotionType.freeQuantity
        val missingQuality = buyQuantity % promotionQuantity
        return if (missingQuality == 0) 0 else promotionQuantity - missingQuality
    }

    // 프로모션 제품이 요청한 수량보다 충분한 지 검사
    fun isPromotionStockSufficient(requiredQuantity: Int): Boolean = _quantity >= requiredQuantity
}