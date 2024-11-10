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

    fun isPromotionActive(): Boolean {
        val today = DateTimes.now()
        val isPromotionActiveToday = today.isAfter(_promotionType.startDate) && today.isBefore(promotionType.endDate)
        return isPromotionActiveToday
    }

    fun missingPromotionQuantity(buyQuantity: Int): Int {
        val promotionQuantity = _promotionType.buyQuantity + _promotionType.freeQuantity
        val missingQuantity = buyQuantity % promotionQuantity
        return (promotionQuantity - missingQuantity).takeIf { missingQuantity == _promotionType.buyQuantity } ?: 0
    }

    fun isPromotionStockSufficient(requiredQuantity: Int): Boolean = _quantity >= requiredQuantity

    fun calculateEligiblePromotionQuantity(): Int {
        val promotionQuantity = _promotionType.buyQuantity + _promotionType.freeQuantity
        return _quantity / promotionQuantity * promotionQuantity
    }

    fun calculateBonusQuantity(purchasedQuantity: Int): Int {
        return if (purchasedQuantity <= _quantity) {
            (purchasedQuantity / (_promotionType.buyQuantity + _promotionType.freeQuantity)) * _promotionType.freeQuantity
        } else {
            (_quantity / (_promotionType.buyQuantity + _promotionType.freeQuantity)) * _promotionType.freeQuantity
        }
    }

}