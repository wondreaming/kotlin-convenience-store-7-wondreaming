package store.controller.validator.purchaseInput

import store.controller.validator.purchaseInput.PurchaseInputErrorType.N0_COMMA
import store.controller.validator.purchaseInput.PurchaseInputErrorType.EMPTY_INPUT

class SentenceValidator(private val sentence: String) {
    fun validate() {
        checkEmpty()
        checkIsDelimiter()
    }

    private fun checkEmpty() {
        require(sentence.isNotEmpty()) { EMPTY_INPUT.errorMessage }
    }

    private fun checkIsDelimiter() {
        require(DELIMITER in sentence) { N0_COMMA.errorMessage }
    }

    companion object {
        private const val DELIMITER = ","
    }
}