package store.controller

import store.controller.validator.userInput.UserInputValidator
import store.model.Product
import store.model.PurchaseInfo

class PurchaseController(
    private val userInteractionController: UserInteractionController,
    private val userInputValidator: UserInputValidator
) {
    fun checkPromotionPurchase(purchaseInfos: List<PurchaseInfo>, products: Map<String, Product>): List<PurchaseInfo> {
        purchaseInfos.forEach { info ->
            val product = products[info.name] ?: return@forEach
            if (isPromotionActive(product) && hasMissingQuantity(product, info.quantity)) {
                val missingQuantity = product.promotionProduct!!.missingPromotionQuantity(info.quantity)
                val userResponse = userInteractionController.handlePromotionConfirmation(info.name, missingQuantity)

                if (userResponse != null) {
                    userInputValidator.validateUserInput(userResponse)
                    if (userResponse == "Y") {
                        info.addQuantity(missingQuantity)
                    }
                }
            }
        }
        return purchaseInfos
    }

    fun checkPromotionStockSufficient(purchaseInfos: List<PurchaseInfo>, products: Map<String, Product>): List<PurchaseInfo> {
        purchaseInfos.forEach { info ->
            val product = products[info.name] ?: return@forEach
            if (product.promotionProduct?.isPromotionActive() == true && !(product.promotionProduct?.isPromotionStockSufficient(info.quantity))!!) {
                val quantity = info.quantity - product.promotionProduct?.quantity!!
                val userResponse = userInteractionController.handleFullPriceConfirmation(info.name, quantity)
                if (userResponse != null) {
                    userInputValidator.validateUserInput(userResponse)
                    if (userResponse == "N") {
                        info.minusQuantity(quantity)
                    }
                }
            }
        }
        return purchaseInfos
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