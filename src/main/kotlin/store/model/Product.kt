package store.model

class Product(
    val name: String,
    val promotionProduct: PromotionProduct?,
    val nonPromotionProduct: NonPromotionProduct?,
) {
    fun checkStock(requestedQuantity: Int): Boolean {
        val availableQuantity = (promotionProduct?.quantity ?: 0) + (nonPromotionProduct?.quantity ?: 0)
        return availableQuantity >= requestedQuantity
    }

    companion object {
        fun isProductAvailable(name: String, products: Map<String, Product>): Boolean {
            return products.containsKey(name)
        }
    }
}