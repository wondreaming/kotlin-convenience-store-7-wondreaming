package store.controller.validator.purchaseInput

import store.controller.validator.purchaseInput.PurchaseInputErrorType.NOT_INTEGER
import store.controller.validator.purchaseInput.PurchaseInputErrorType.ZERO_AMOUNT
import store.controller.validator.purchaseInput.PurchaseInputErrorType.NEGATIVE_NUMBER

class QuantityValidator(private val quantity: String) {
    fun validate() {
        checkInteger()
        checkIsZero()
        checkNegativeNumber()
    }

    private fun checkInteger() {
        require(quantity.toIntOrNull() != null) { NOT_INTEGER.errorMessage }
    }

    private fun checkIsZero() {
        require(quantity.toInt() != ZERO) { ZERO_AMOUNT.errorMessage }
    }

    private fun checkNegativeNumber() {
        require(quantity.toInt() > ZERO) { NEGATIVE_NUMBER.errorMessage }
    }

    companion object {
        private const val ZERO: Int = 0
    }
}