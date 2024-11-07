package store.controller

import store.model.NonPromotionalProduct
import store.model.Product
import store.model.PromotionType
import store.model.PromotionalProduct
import store.util.DateUtils.stringToLocalDate
import java.io.File
import java.util.*

class ProductController {
    private val productsFilePath = PRODUCTS_FILE_PATH

    fun loadProducts(promotionTypes: List<PromotionType>): List<Product> =
        File(productsFilePath).readLines()
            .filterNot { it.startsWith(HEADER_NAME) }
            .map { parseProduct(it, promotionTypes) }

    private fun parseProduct(line: String, promotionTypes: List<PromotionType>): Product {
        val (name, price, quantity, promotionTypeName) = line.split(DELIMITER)
        return when {
            promotionTypeName != NO_PROMOTION_LABEL -> {
                parsePromotionalProduct(name, price, quantity, promotionTypeName, promotionTypes)
            }

            else -> parseNonPromotionalProduct(name, price, quantity)
        }
    }

    private fun parsePromotionalProduct(
        name: String,
        price: String,
        quantity: String,
        promotionTypeName: String,
        promotionTypes: List<PromotionType>
    ): Product {
        val promotionType = promotionTypes.find { it.name == promotionTypeName }
            ?: throw IllegalArgumentException(String.format(PROMOTION_TYPE_NOT_FOUND_ERROR, promotionTypeName))
        return Product(
            name = name,
            promotionProduct = PromotionalProduct(
                price = price.toInt(),
                _quantity = quantity.toInt(),
                _promotionType = promotionType
            ),
            nonPromotionProduct = null
        )
    }

    private fun parseNonPromotionalProduct(name: String, price: String, quantity: String): Product {
        return Product(
            name = name,
            promotionProduct = null,
            nonPromotionProduct = NonPromotionalProduct(
                price = price.toInt(),
                _quantity = quantity.toInt()
            )
        )
    }

    companion object {
        private const val PRODUCTS_FILE_PATH = "src/main/resources/products.md"
        private const val HEADER_NAME = "name"
        private const val DELIMITER = ","
        private const val PROMOTION_TYPE_NOT_FOUND_ERROR = "PromotionType '%s' not found"
        private const val NO_PROMOTION_LABEL = "null"

    }
}