package store.controller.adapter

import store.model.PurchaseInfo

class PurchaseInfoAdapter(
    private val purchaseInput: String
) {
    private val purchaseItems = purchaseInput.split(DELIMITER).map { it.trim() }

    fun adaptPurchaseInfo(): List<PurchaseInfo> {
        return purchaseItems.map { item ->
            val (product, quantity) = item
                .removeSurrounding(OPEN_BRACKET, CLOSE_BRACKET)
                .split(HYPHEN_DELIMITER)
            PurchaseInfo(product, quantity.toInt())
        }
    }

    companion object {
        private const val DELIMITER = ","
        private const val OPEN_BRACKET = "["
        private const val CLOSE_BRACKET = "]"
        private const val HYPHEN_DELIMITER = "-"
    }
}