package store.controller.validator.purchaseInput

import store.model.Product
import store.controller.validator.purchaseInput.PurchaseInputErrorType.PRODUCT_NOT_FOUND

class NameValidator(private val name: String, private val products: Map<String, Product>) {
    fun validate() {
        checkProductInStock()
    }

    private fun checkProductInStock() {
        require(Product.isProductAvailable(name, products)) { PRODUCT_NOT_FOUND.errorMessage }
    }
}
