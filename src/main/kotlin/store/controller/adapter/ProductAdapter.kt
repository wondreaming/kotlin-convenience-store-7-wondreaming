package store.controller.adapter

import store.model.NonPromotionProduct
import store.model.Product
import store.model.PromotionProduct
import store.model.PromotionType
import java.util.*

class ProductAdapter {
    fun adaptProducts(products: Map<String, Product>): List<String> {
        return products.flatMap { product ->
            listOfNotNull(
                product.value.promotionProduct?.let { getDisplayPromotionProduct(product.key, it) },
                product.value.nonPromotionProduct?.let { getDisplayNonPromotionProduct(product.key, it) }
            )
        }
    }

    private fun getDisplayPromotionProduct(name: String, promotionProduct: PromotionProduct): String {
        val price = promotionProduct.price
        val displayPrice = getDisplayPrice(price)
        val displayQuantity = getDisplayQuantity(promotionProduct.quantity)
        val displayPromotionType = getPromotionLabel(promotionProduct.promotionType)
        return String.format(PRODUCT_DISPLAY_FORMAT, name, displayPrice, displayQuantity, displayPromotionType)
    }

    private fun getDisplayNonPromotionProduct(name: String, nonPromotionProduct: NonPromotionProduct): String {
        val price = nonPromotionProduct.price
        val displayPrice = getDisplayPrice(price)
        val displayQuantity = getDisplayQuantity(nonPromotionProduct.quantity)
        return String.format(PRODUCT_DISPLAY_FORMAT, name, displayPrice, displayQuantity, NO_PROMOTION_LABEL)
    }

    private fun getDisplayPrice(price: Int): String {
        return String.format(Locale.KOREA, PRICE_FORMAT_PATTERN, price) + CURRENCY_SYMBOL
    }

    private fun getDisplayQuantity(quantity: Int): String {
        return when {
            quantity > ZERO -> String.format(QUANTITY_UNIT, quantity)
            else -> OUT_OF_STOCK_MESSAGE
        }
    }

    private fun getPromotionLabel(promotionType: PromotionType): String = promotionType.name

    companion object {
        private const val PRICE_FORMAT_PATTERN = "%,d"
        private const val CURRENCY_SYMBOL = "원"
        private const val OUT_OF_STOCK_MESSAGE = "재고 없음"
        private const val QUANTITY_UNIT = "%d개"
        private const val NO_PROMOTION_LABEL = ""
        private const val PRODUCT_DISPLAY_FORMAT = "- %s %s %s %s"
        private const val ZERO = 0
    }
}