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

    companion object {
        fun isProductAvailable(name: String, products: Map<String, Product>): Boolean {
            return products.containsKey(name)
        }
    }
}