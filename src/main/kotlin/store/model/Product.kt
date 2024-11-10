package store.model

class Product(
    val name: String,
    private var _promotionProduct: PromotionProduct?,
    private var _nonPromotionProduct: NonPromotionProduct?,
) {
    val promotionProduct: PromotionProduct?
        get() = _promotionProduct

    val nonPromotionProduct: NonPromotionProduct?
        get() = _nonPromotionProduct

    fun checkStock(requestedQuantity: Int): Boolean {
        val availableQuantity = (promotionProduct?.quantity ?: 0) + (nonPromotionProduct?.quantity ?: 0)
        return availableQuantity >= requestedQuantity
    }

    fun updatePromotionProduct(promotionProduct: PromotionProduct) {
        _promotionProduct = promotionProduct
    }

    fun updateNonPromotionProduct(nonPromotionProduct: NonPromotionProduct) {
        _nonPromotionProduct = nonPromotionProduct
    }

    fun reduceQuantity(amount: Int) {
        var remainingAmount = amount
        if (_promotionProduct != null && _promotionProduct!!.isPromotionActive() && _promotionProduct!!.quantity > 0) {
            val reducedAmount = minOf(remainingAmount, _promotionProduct!!.quantity)
            _promotionProduct = _promotionProduct!!.copy(_quantity = promotionProduct!!.quantity - reducedAmount)
            remainingAmount -= reducedAmount
        }
        if (remainingAmount > 0 && _nonPromotionProduct != null && _nonPromotionProduct!!.quantity > 0) {
            _nonPromotionProduct = _nonPromotionProduct!!.copy(_quantity = _nonPromotionProduct!!.quantity - remainingAmount)
        }
    }

    companion object {
        fun isProductAvailable(name: String, products: Map<String, Product>): Boolean {
            return products.containsKey(name)
        }
    }
}