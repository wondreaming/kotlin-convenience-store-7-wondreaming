package store.controller.validator

import store.controller.validator.purchaseInput.PurchaseInputErrorType.DUPLICATE_PRODUCT
import store.controller.validator.purchaseInput.ItemValidator
import store.controller.validator.purchaseInput.NameValidator
import store.controller.validator.purchaseInput.PurchaseInputErrorType.EXCEEDS_STOCK
import store.controller.validator.purchaseInput.QuantityValidator
import store.controller.validator.purchaseInput.SentenceValidator
import store.model.Product


class PurchaseInputValidator(
    private val purchaseInput: String,
    private val products: Map<String, Product>,
) {
    private val splitPurchaseItems: List<String> = purchaseInput.split(DELIMITER).map { it.trim() }

    fun validate() {
        SentenceValidator(purchaseInput, splitPurchaseItems.size).validate()
        validateEachPurchaseItem()
        checkDuplicateNames()
    }

    private fun validateEachPurchaseItem() {
        splitPurchaseItems.forEach { item ->
            ItemValidator(item).validate()
            val (product, quantity) = splitProductAndQuantity(item)
            NameValidator(product, products).validate()
            QuantityValidator(quantity).validate()
            checkStock(product, quantity)
        }
    }

    private fun splitProductAndQuantity(purchaseItem: String): Pair<String, String> {
        val (product, quantity) = purchaseItem
            .removeSurrounding(OPEN_BRACKET, CLOSE_BRACKET)
            .split(HYPHEN_DELIMITER)
        return Pair(product, quantity)
    }

    private fun checkDuplicateNames() {
        val productNames = splitPurchaseItems.map { it.extractProductName() }
        val duplicates = productNames.groupingBy { it }.eachCount().filter { it.value > 1 }
        require(duplicates.isEmpty()) { DUPLICATE_PRODUCT.errorMessage }
    }

    private fun String.extractProductName(): String {
        return this.removeSurrounding(OPEN_BRACKET, CLOSE_BRACKET)
            .split(HYPHEN_DELIMITER)[NAME]
    }

    private fun checkStock(name: String, quantity: String) {
        val product = products.values.find { it.name == name }
        require(product!!.checkStock(quantity.toInt())) { EXCEEDS_STOCK.errorMessage }
    }

    companion object {
        private const val DELIMITER: String = ","
        private const val OPEN_BRACKET = "["
        private const val CLOSE_BRACKET = "]"
        private const val HYPHEN_DELIMITER = "-"
        private const val NAME: Int = 0
    }
}