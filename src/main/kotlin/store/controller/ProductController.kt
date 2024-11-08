package store.controller

import store.controller.adapter.ProductAdapter
import store.model.NonPromotionProduct
import store.model.Product
import store.model.PromotionProduct
import store.model.PromotionType
import java.io.File

class ProductController(
    private val promotionTypeController: PromotionTypeController,
    private val productAdapter: ProductAdapter,
) {
    private val productsFilePath = PRODUCTS_FILE_PATH

    fun loadProducts(): Map<String, Product> =
        File(productsFilePath).readLines()
            .filterNot { it.startsWith(HEADER_NAME) }
            .map { parseProduct(it) }
            .associateBy { it.name }

    private fun parseProduct(line: String): Product {
        val (name, price, quantity, promotionTypeName) = line.split(DELIMITER)
        return when {
            promotionTypeName != NO_PROMOTION_LABEL ->
                parsePromotionalProduct(name, price, quantity, promotionTypeName)

            else -> parseNonPromotionalProduct(name, price, quantity)
        }
    }

    private fun parsePromotionalProduct(
        name: String,
        price: String,
        quantity: String,
        promotionTypeName: String,
    ): Product {
        val promotionType = findPromotionType(promotionTypeName)
        return createProductWithPromotion(name, price.toInt(), quantity.toInt(), promotionType)
    }

    private fun createProductWithPromotion(
        name: String,
        price: Int,
        quantity: Int,
        promotionType: PromotionType
    ) = Product(
        name = name,
        promotionProduct = PromotionProduct(price, quantity, promotionType),
        nonPromotionProduct = null
    )

    private fun findPromotionType(promotionTypeName: String): PromotionType {
        val promotionTypes = promotionTypeController.loadPromotionType()
        return promotionTypes.find { it.name == promotionTypeName }
            ?: throw IllegalArgumentException(String.format(PROMOTION_TYPE_NOT_FOUND_ERROR, promotionTypeName))
    }

    private fun parseNonPromotionalProduct(name: String, price: String, quantity: String): Product {
        return Product(
            name = name,
            promotionProduct = null,
            nonPromotionProduct = NonPromotionProduct(
                price = price.toInt(),
                _quantity = quantity.toInt()
            )
        )
    }

    fun adaptProducts(products: Map<String, Product>): List<String> {
        return productAdapter.adaptProducts(products)
    }

    companion object {
        private const val PRODUCTS_FILE_PATH = "src/main/resources/products.md"
        private const val HEADER_NAME = "name"
        private const val DELIMITER = ","
        private const val PROMOTION_TYPE_NOT_FOUND_ERROR = "PromotionType '%s' not found"
        private const val NO_PROMOTION_LABEL = "null"

    }
}