package store.controller.adapter

import store.model.NonPromotionalProduct
import store.model.Product
import store.model.PromotionType
import store.model.PromotionalProduct
import store.util.DateUtils.stringToLocalDate
import java.io.File
import java.util.*

class ProductController {
    private val productsFilePath = "src/main/resources/products.md"
    private val promotionFilePath = "src/main/resources/promotions.md"

    fun loadPromotionsType(): List<PromotionType> {
        File(promotionFilePath).forEachLine { line: String ->
            if (!line.startsWith("name")) {
                val (name, buyQuantity, freeQuantity, _startDate, _endDate) = line.split(",")
                val startDate = stringToLocalDate(_startDate)
                val endDate = stringToLocalDate(_endDate)
                PromotionType(
                    name = name,
                    buyQuantity = buyQuantity.toInt(),
                    freeQuantity = freeQuantity.toInt(),
                    startDate = startDate,
                    endDate = endDate,
                )
            }
        }
    }

    fun loadProducts(): List<Product> {
        File(productsFilePath).forEachLine { line: String ->
            if (!line.startsWith("name")) {
                val (name, price, quality, promtionType) = line.split(",")
            }
        }
    }

    fun adaptProducts(products: List<Product>): List<String> {
        return products.map { product ->
            val displayPrice = String.format(Locale.KOREA, PRICE_FORMAT_PATTERN, product.price) + CURRENCY_SYMBOL
            val displayQuantity = when {
                product.quality > 0 -> String.format(QUANTITY_UNIT, product.quality)
                else -> OUT_OF_STOCK_MESSAGE
            }
            val displayPromotionType: String = getPromotionLabel(product)
            String.format(PRODUCT_DISPLAY_FORMAT, product.name, displayPrice, displayQuantity, displayPromotionType)
        }
    }

    private fun getPromotionLabel(product: Product): String {
        return when (product) {
            is PromotionalProduct -> product.promotionType.name
            is NonPromotionalProduct -> NO_PROMOTION_LABEL
        }
    }

    companion object {
        private const val PRICE_FORMAT_PATTERN = "%,d"
        private const val CURRENCY_SYMBOL = "원"
        private const val OUT_OF_STOCK_MESSAGE = "재고 없음"
        private const val QUANTITY_UNIT = "%d개"
        private const val NO_PROMOTION_LABEL = ""
        private const val PRODUCT_DISPLAY_FORMAT = "- %s %s %s %s\n"
    }
}