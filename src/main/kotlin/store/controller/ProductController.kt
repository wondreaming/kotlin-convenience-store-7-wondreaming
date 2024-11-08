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
            val promoProduct = parsePromotionProduct(price, quantity, promotionTypeName)
            return Product(name, promoProduct, null)
        }
        val nonPromoProduct = parseNonPromotionProduct(price, quantity)
        return Product(name, null, nonPromoProduct)
    }

    private fun updateExistingProduct(
        product: Product,
        price: String,
        quantity: String,
        promotionTypeName: String
    ) {
        if (promotionTypeName != NO_PROMOTION_LABEL && product.promotionProduct == null) {
            product.updatePromotionProduct(parsePromotionProduct(price, quantity, promotionTypeName))
            return
        }
        if (promotionTypeName == NO_PROMOTION_LABEL && product.nonPromotionProduct == null) {
            product.updateNonPromotionProduct(parseNonPromotionProduct(price, quantity))
        }
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
        return promotionTypes.find { it.name == promotionTypeName }
            ?: throw IllegalArgumentException(String.format(PROMOTION_TYPE_NOT_FOUND_ERROR, promotionTypeName))
    }

    private fun parseNonPromotionProduct(price: String, quantity: String): NonPromotionProduct {
        return NonPromotionProduct(price.toInt(), quantity.toInt())
    }

    fun adaptProducts(products: Map<String, Product>): List<String> {
        return productAdapter.adaptProducts(products)
    }

    companion object {
        private const val PRODUCTS_FILE_PATH = "src/main/resources/products.md"
        private const val HEADER_NAME = "name"
        private const val DELIMITER = ","
        private const val PROMOTION_TYPE_NOT_FOUND_ERROR = "프로모션 '%s'는 찾을 수 없습니다."
        private const val NO_PROMOTION_LABEL = "null"

    }
}