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
        val availableQuantity = (promotionProduct?.quantity ?: ZER0) + (nonPromotionProduct?.quantity ?: ZER0)
        return availableQuantity >= requestedQuantity
    }

    fun updatePromotionProduct(promotionProduct: PromotionProduct) {
        _promotionProduct = promotionProduct
    }

    fun updateNonPromotionProduct(nonPromotionProduct: NonPromotionProduct) {
        _nonPromotionProduct = nonPromotionProduct
    }


    fun reduceQuantity(amount: Int) {
        val remainingAmount = reducePromotionQuantity(amount)
        if (remainingAmount > ZER0) reduceNonPromotionQuantity(remainingAmount)
    }

    private fun reducePromotionQuantity(amount: Int): Int {
        if (_promotionProduct?.isPromotionActive() == true && _promotionProduct!!.quantity > 0) {
            val reducedAmount = minOf(amount, _promotionProduct!!.quantity)
            _promotionProduct = _promotionProduct!!.copy(_quantity = _promotionProduct!!.quantity - reducedAmount)
            return amount - reducedAmount
        }
        return amount
    }

    private fun reduceNonPromotionQuantity(amount: Int) {
        if (_nonPromotionProduct != null && _nonPromotionProduct!!.quantity >= amount) {
            _nonPromotionProduct = _nonPromotionProduct!!.copy(_quantity = _nonPromotionProduct!!.quantity - amount)
        }
    }


    fun updateNonPromotionQuantity(quantity: Int) {
        _nonPromotionProduct = _nonPromotionProduct!!.copy(_quantity = _nonPromotionProduct!!.quantity + quantity)
    }

    companion object {
        private const val ZER0 = 0
        fun isProductAvailable(name: String, products: Map<String, Product>): Boolean {
            return products.containsKey(name)
        }
    }
}