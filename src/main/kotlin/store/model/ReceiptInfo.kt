package store.model

import kotlin.math.min

data class ReceiptInfo(
    private var _items: List<PurchaseInfo>,
    private var _totalAmount: Int = ZER0,
    val bonusItems: List<PurchaseInfo> = listOf(),
    private var _promotionDiscount: Int = ZER0,
    val membership: Membership,
    val storeProducts: Map<String, Product>,
    private var _totalQuantity: Int = ZER0,
) {
    init {
        calculateTotalAmount()
        calculatePromotionDiscount()
        calculateMembershipDiscount()
        calculateTotalQuantity()
    }

    val totalQuantity: Int
        get() = _totalQuantity

    var membershipDiscount = ZER0

    val finalAmount: Int
        get() = _totalAmount - _promotionDiscount - membershipDiscount

    val promotionDiscount: Int
        get() = _promotionDiscount

    val totalAmount: Int
        get() = _totalAmount

    val items: List<PurchaseInfo>
        get() = _items

    fun getItemPrice(name: String): Int {
        val product = storeProducts[name]
        val price = product?.promotionProduct?.price ?: product?.nonPromotionProduct?.price
        return price!!
    }

    private fun calculateTotalAmount() {
        var calculatedTotalAmount = ZER0
        _items.forEach { item ->
            val itemPrice = getItemPrice(item.name)
            calculatedTotalAmount += itemPrice * item.quantity
        }
        _totalAmount = calculatedTotalAmount
    }

    private fun calculatePromotionDiscount() {
        var calculatedPromotionDiscount = ZER0
        bonusItems.forEach { item ->
            val price = storeProducts[item.name]?.promotionProduct?.price
            calculatedPromotionDiscount += price!! * item.quantity
        }
        _promotionDiscount = calculatedPromotionDiscount
    }

    private fun calculateMembershipDiscount() {
        if (!membership.isMember) return
        val totalPromotionAmount = calculateTotalPromotionAmount()
        val discount = ((_totalAmount - totalPromotionAmount) * DISCOUNT_RATE).toInt()
        membershipDiscount = min(discount, LIMITED_PRICE)
    }

    private fun calculateTotalPromotionAmount(): Int {
        var totalPromotionAmount = ZER0
        bonusItems.forEach { item ->
            val promotionQuantity = calculatePromotionQuantity(item)
            val promotionAmount = promotionQuantity * storeProducts[item.name]?.promotionProduct!!.price
            totalPromotionAmount += promotionAmount
        }
        return totalPromotionAmount
    }

    private fun calculatePromotionQuantity(item: PurchaseInfo): Int {
        val freeQuantity = storeProducts[item.name]?.promotionProduct?.promotionType?.freeQuantity ?: ZER0
        val buyQuantity = storeProducts[item.name]?.promotionProduct?.promotionType?.buyQuantity ?: ZER0

        if (freeQuantity <= ZER0) return ZER0
        return (item.quantity / freeQuantity) * (buyQuantity + freeQuantity)
    }

    private fun calculateTotalQuantity() {
        var calculatedTotalQuantity = ZER0
        _items.forEach { item ->
            calculatedTotalQuantity += item.quantity
        }
        _totalQuantity = calculatedTotalQuantity
    }

    companion object {
        private const val ZER0 = 0
        private const val LIMITED_PRICE = 8000
        private const val DISCOUNT_RATE = 0.3
    }
}

