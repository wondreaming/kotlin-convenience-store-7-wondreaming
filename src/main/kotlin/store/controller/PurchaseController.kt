package store.controller

import store.model.Product
import store.model.PurchaseInfo

class PurchaseController(
    private val userInteractionController: UserInteractionController,
) {
    fun checkPromotionPurchase(purchaseInfos: List<PurchaseInfo>, products: Map<String, Product>) {
        purchaseInfos.forEach { info ->
            val product = products[info.name] ?: return@forEach
            if (isPromotionActive(product) && hasMissingQuantity(product, info.quantity)) {
                val missingQuantity = product.promotionProduct!!.missingPromotionQuantity(info.quantity)
                userInteractionController.handlePromotionConfirmation(info.name, missingQuantity)
            }
        }
    }

    private fun isPromotionActive(product: Product): Boolean {
        return product.promotionProduct?.isPromotionActive() == true
    }

    private fun hasMissingQuantity(product: Product, quantity: Int): Boolean {
        val promotionProduct = product.promotionProduct ?: return false
        return promotionProduct.missingPromotionQuantity(quantity) > ZERO
    }

    companion object {
        private const val ZERO = 0
    }
}