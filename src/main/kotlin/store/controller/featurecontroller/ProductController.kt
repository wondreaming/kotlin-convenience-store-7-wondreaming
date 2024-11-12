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
            .forEach { processLine(it, products) }
        return products
    }

    private fun processLine(line: String, products: MutableMap<String, Product>) {
        val (name, price, quantity, promotionTypeName) = line.split(DELIMITER)
        val product = products[name] ?: createProduct(name, price, quantity, promotionTypeName)
        products[name] = product.apply {
            if (products.containsKey(name)) updateExistingProduct(this, price, quantity, promotionTypeName)
        }
    }

    private fun createProduct(name: String, price: String, quantity: String, promotionTypeName: String): Product {
        return if (isPromotionAvailable(promotionTypeName))
            Product(name, parsePromotionProduct(price, quantity, promotionTypeName), parseNonPromotionProduct(price, ZERO))
        else Product(name, null, parseNonPromotionProduct(price, quantity))
    }

    private fun updateExistingProduct(product: Product, price: String, quantity: String, promotionTypeName: String) {
        when {
            isPromotionProductUpdateNeeded(product, promotionTypeName) -> updatePromotionProduct(product, price, quantity, promotionTypeName)
            isNonPromotionProductCreationNeeded(product, promotionTypeName) -> updateNonPromotionProduct(product, price, quantity)
            else -> increaseNonPromotionQuantity(product, quantity)
        }
    }

    private fun isPromotionAvailable(promotionTypeName: String) = promotionTypeName != NO_PROMOTION_LABEL && findPromotionType(promotionTypeName).isPromotionActive()

    private fun isPromotionProductUpdateNeeded(product: Product, promotionTypeName: String) =
        isPromotionAvailable(promotionTypeName) && product.promotionProduct == null

    private fun isNonPromotionProductCreationNeeded(product: Product, promotionTypeName: String) =
        (promotionTypeName == NO_PROMOTION_LABEL || !isPromotionAvailable(promotionTypeName)) && product.nonPromotionProduct == null

    private fun updatePromotionProduct(product: Product, price: String, quantity: String, promotionTypeName: String) {
        product.updatePromotionProduct(parsePromotionProduct(price, quantity, promotionTypeName))
    }

    private fun updateNonPromotionProduct(product: Product, price: String, quantity: String) {
        product.updateNonPromotionProduct(parseNonPromotionProduct(price, quantity))
    }

    private fun increaseNonPromotionQuantity(product: Product, quantity: String) {
        product.updateNonPromotionQuantity(quantity.toInt())
    }

    private fun parsePromotionProduct(price: String, quantity: String, promotionTypeName: String): PromotionProduct {
        return PromotionProduct(price.toInt(), quantity.toInt(), findPromotionType(promotionTypeName))
    }

    private fun findPromotionType(promotionTypeName: String): PromotionType {
        return promotionTypeController.loadPromotionType().find { it.name == promotionTypeName }!!
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
