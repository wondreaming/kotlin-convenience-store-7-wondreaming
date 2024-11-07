package store.controller.validator.purchaseInput

import store.controller.validator.purchaseInput.PurchaseInputErrorType.INVALID_BRACKET_FORMAT
import store.controller.validator.purchaseInput.PurchaseInputErrorType.NO_HYPHEN

class ItemValidator(
    private val item: String,
) {
    fun validate() {
        checkBrackets()
        checkDelimiter()
    }

    private fun checkBrackets() {
        require(item.startsWith(OPEN_BRACKET) && item.endsWith(CLOSE_BRACKET)) {
            INVALID_BRACKET_FORMAT.errorMessage
        }
    }

    private fun checkDelimiter() {
        require(DELIMITER in item) { NO_HYPHEN.errorMessage }
    }

    companion object {
        private const val OPEN_BRACKET = "["
        private const val CLOSE_BRACKET = "]"
        private const val DELIMITER = "-"
    }
}