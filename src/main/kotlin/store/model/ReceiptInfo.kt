package store.model

data class ReceiptInfo(
    private var _items: List<PurchaseInfo>,
    private var _totalAmount: Int = 0,
    val bonusItems: List<PurchaseInfo> = listOf(),
    private var _promotionDiscount: Int = 0,
    val membership: Membership,
    val storeProducts: Map<String, Product>,
    private var _totalQuantity: Int = 0,
) {
    init {
        calculateTotalAmount()
        calculatePromotionDiscount()
        calculateMmembershipDiscount()
        caclulateTotalQuantity()
    }

    val totalQuantity: Int
        get() = _totalQuantity
    var membershipDiscount = 0

    val finalAmount: Int
        get() = _totalAmount + _promotionDiscount + membershipDiscount

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
        var calculatedTotalAmount = 0
        _items.forEach { item ->
            val itemPrice = getItemPrice(item.name)
            calculatedTotalAmount += itemPrice * item.quantity
        }
        _totalAmount = calculatedTotalAmount
    }

    private fun calculatePromotionDiscount() {
        var calculatedPromotionDiscount = 0
        bonusItems.forEach { item ->
            val price = storeProducts[item.name]?.promotionProduct?.price
            calculatedPromotionDiscount += price!! * item.quantity
        }
        _promotionDiscount = -calculatedPromotionDiscount
    }

    private fun calculateMmembershipDiscount() {
        if (membership.isMember && membership.dailyLimitUsed < 8000) {
            println(_totalAmount)
            println(_promotionDiscount)
            membershipDiscount = -(Math.floor(((_totalAmount + _promotionDiscount) * 0.3) / 1000.0) * 1000).toInt()
        }
    }

    private fun caclulateTotalQuantity() {
        var calculatedTotalQuantity = 0
        _items.forEach { item ->
            calculatedTotalQuantity += item.quantity
        }
        _totalQuantity = calculatedTotalQuantity
    }

}

