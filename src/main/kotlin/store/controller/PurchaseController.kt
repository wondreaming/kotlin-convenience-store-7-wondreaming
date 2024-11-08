package store.controller

import store.model.Product
import store.model.PurchaseInfo

class PurchaseController(
    private val userInteractionController: UserInteractionController,
) {
    fun checkPromotionPurchase(purchaseInfo: List<PurchaseInfo>, products: Map<String, Product>){
        purchaseInfo.forEach { purchaseInfo ->
            val product = products[purchaseInfo.name]
            val promotionProduct = product!!.promotionProduct
            if (promotionProduct != null && promotionProduct.isPromotionActive()) {
                val missingQuality = promotionProduct.missingPromotionQuantity(purchaseInfo.quantity)
                if (missingQuality > 0) userInteractionController.handlePromotionConfirmation(purchaseInfo.name, missingQuality)
            }
        }
    }
}