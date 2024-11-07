package store.controller.adapter

import store.model.Product
import java.util.*

class ProductAdapter {
    fun adaptProducts(products: List<Product>): List<String> {
        return products.map { product ->
            val price = product.promotionProduct?.price ?: product.nonPromotionProduct?.price
            val displayPrice = String.format(Locale.KOREA, PRICE_FORMAT_PATTERN, price) + CURRENCY_SYMBOL
            val displayQuantity = getDisplayQuantity(product)
            val displayPromotionType: String = getPromotionLabel(product)
            String.format(PRODUCT_DISPLAY_FORMAT, product.name, displayPrice, displayQuantity, displayPromotionType)
        }
    }

    private fun getDisplayQuantity(product: Product): String {
        val quantity: Int = (product.promotionProduct?.quantity ?: product.nonPromotionProduct?.quantity)!!
        return when {
            quantity > ZERO -> String.format(QUANTITY_UNIT, quantity)
            else -> OUT_OF_STOCK_MESSAGE
        }
    }

    private fun getPromotionLabel(product: Product): String {
        return when {
            (product.promotionProduct != null) -> product.promotionProduct.promotionType.name
            else -> NO_PROMOTION_LABEL
        }
    }

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