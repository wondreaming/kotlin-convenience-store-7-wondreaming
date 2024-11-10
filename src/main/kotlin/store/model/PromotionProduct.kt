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
        val missingQuantity = buyQuantity % promotionQuantity
        return (promotionQuantity - missingQuantity).takeIf { missingQuantity == _promotionType.buyQuantity } ?: 0
    }

    // 프로모션 제품이 요청한 수량보다 충분한 지 검사
    fun isPromotionStockSufficient(requiredQuantity: Int): Boolean = _quantity > requiredQuantity

    // 프로모션이 가능한 제품 갯수 계산
    fun calculateEligiblePromotionQuantity(): Int {
        val promotionQuantity = _promotionType.buyQuantity + _promotionType.freeQuantity
        return _quantity / promotionQuantity * promotionQuantity
    }

    fun calculateBonusQuantity(purchasedQuantity: Int): Int {
        return if (purchasedQuantity <= _quantity) {
            (purchasedQuantity / (_promotionType.buyQuantity+_promotionType.freeQuantity)) * _promotionType.freeQuantity
        } else {
            (_quantity / (_promotionType.buyQuantity+_promotionType.freeQuantity)) * _promotionType.freeQuantity
        }
    }

}