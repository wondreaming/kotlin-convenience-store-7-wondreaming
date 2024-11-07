package store.controller.adapter

import store.model.PurchaseInfo

class PurchaseInfoAdapter(
    val purchaseInput: String
) {
    val purchaseItems = purchaseInput.split(",").map { it.trim() }

    fun adaptPurchaseInfo(): List<PurchaseInfo> {
        return purchaseItems.map { item ->
            val (product, quantity) = item
                .removeSurrounding(OPEN_BRACKET, CLOSE_BRACKET)
                .split(HYPHEN_DELIMITER)
            PurchaseInfo(product, quantity.toInt())
        }
    }

    companion object {
        private const val OPEN_BRACKET = "["
        private const val CLOSE_BRACKET = "]"
        private const val HYPHEN_DELIMITER = "-"
    }
}