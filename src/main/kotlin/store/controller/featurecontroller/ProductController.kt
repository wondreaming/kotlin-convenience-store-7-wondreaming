package store.controller.featurecontroller

import store.model.NonPromotionProduct
import store.model.Product
import store.model.PromotionProduct
import store.model.PromotionType
import java.io.File

class ProductController(
    private val promotionTypeController: PromotionTypeController,
) {
    fun loadProducts(): Map<String, Product> {
        val products = mutableMapOf<String, Product>()
        File(PRODUCTS_FILE_PATH).readLines()
            .filterNot { it.startsWith(HEADER_NAME) }
            .forEach { line ->
                processLine(line, products)
            }
        return products
    }

    private fun processLine(line: String, products: MutableMap<String, Product>) {
        val (name, price, quantity, promotionTypeName) = line.split(DELIMITER)
        if (!products.containsKey(name)) {
            products[name] = createProduct(name, price, quantity, promotionTypeName)
            return
        }
        updateExistingProduct(products[name]!!, price, quantity, promotionTypeName)
    }

    private fun createProduct(name: String, price: String, quantity: String, promotionTypeName: String): Product {
        if (promotionTypeName != NO_PROMOTION_LABEL) {
            val promotionTypes = findPromotionType(promotionTypeName)
            if (promotionTypes.isPromotionActive()) {
                val promotionProduct = parsePromotionProduct(price, quantity, promotionTypeName)
                val nonPromotionProduct = parseNonPromotionProduct(price, ZERO)
                return Product(name, promotionProduct, nonPromotionProduct)

            }
        }
        val nonPromotionProduct = parseNonPromotionProduct(price, quantity)
        return Product(name, null, nonPromotionProduct)
    }

    private fun updateExistingProduct(
        product: Product,
        price: String,
        quantity: String,
        promotionTypeName: String
    ) {
        if (isPromotionProductUpdateNeeded(product, promotionTypeName)) {
            updatePromotionProduct(product, price, quantity, promotionTypeName)
            return
        }

        if (isNonPromotionProductCreationNeeded(product, promotionTypeName)) {
            updateNonPromotionProduct(product, price, quantity)
            return
        }

        increaseNonPromotionQuantity(product, quantity)
    }

    private fun isPromotionProductUpdateNeeded(product: Product, promotionTypeName: String): Boolean {
        return promotionTypeName != NO_PROMOTION_LABEL &&
                findPromotionType(promotionTypeName).isPromotionActive() &&
                product.promotionProduct == null
    }

    private fun isNonPromotionProductCreationNeeded(product: Product, promotionTypeName: String): Boolean {
        return promotionTypeName == NO_PROMOTION_LABEL ||
                !findPromotionType(promotionTypeName).isPromotionActive() &&
                product.nonPromotionProduct == null
    }

    private fun updatePromotionProduct(
        product: Product,
        price: String,
        quantity: String,
        promotionTypeName: String
    ) {
        product.updatePromotionProduct(parsePromotionProduct(price, quantity, promotionTypeName))
    }

    private fun updateNonPromotionProduct(product: Product, price: String, quantity: String) {
        product.updateNonPromotionProduct(parseNonPromotionProduct(price, quantity))
    }

    private fun increaseNonPromotionQuantity(product: Product, quantity: String) {
        product.updateNonPromotionQuantity(quantity.toInt())
    }

    private fun parsePromotionProduct(
        price: String,
        quantity: String,
        promotionTypeName: String,
    ): PromotionProduct {
        val promotionType = findPromotionType(promotionTypeName)
        return PromotionProduct(price.toInt(), quantity.toInt(), promotionType)
    }

    private fun findPromotionType(promotionTypeName: String): PromotionType {
        val promotionTypes = promotionTypeController.loadPromotionType()
        return promotionTypes.find { it.name == promotionTypeName }!!
    }

    private fun parseNonPromotionProduct(price: String, quantity: String): NonPromotionProduct {
        return NonPromotionProduct(price.toInt(), quantity.toInt())
    }

    companion object {
        private const val PRODUCTS_FILE_PATH = "src/main/resources/products.md"
        private const val HEADER_NAME = "name"
        private const val DELIMITER = ","
        private const val NO_PROMOTION_LABEL = "null"
        private const val ZERO = "0"
    }
}