package store.controller

import store.controller.validator.userInput.UserInputValidator
import store.model.Product
import store.model.PromotionProduct
import store.model.PurchaseInfo

class PurchaseController(
    private val userInteractionController: UserInteractionController,
    private val userInputValidator: UserInputValidator
) {
    fun checkPromotionPurchase(purchaseInfos: List<PurchaseInfo>, products: Map<String, Product>): List<PurchaseInfo> {
        purchaseInfos.forEach { info ->
            products[info.name]?.takeIf { isPromotionActive(it) && hasMissingQuantity(it, info.quantity) }
                ?.let { product ->
                    handlePromotionConfirmation(info, product)
                }
        }
        return purchaseInfos
    }

    fun checkPromotionStockSufficient(
        purchaseInfos: List<PurchaseInfo>,
        products: Map<String, Product>
    ): List<PurchaseInfo> {
        purchaseInfos.forEach { info ->
            products[info.name]?.promotionProduct
                ?.takeIf { it.isPromotionActive() && !it.isPromotionStockSufficient(info.quantity) }
                ?.let { promotionProduct ->
                    handleStockConfirmation(info, promotionProduct)
                }
        }
        return purchaseInfos
    }

    private fun handlePromotionConfirmation(info: PurchaseInfo, product: Product) {
        val missingQuantity = product.promotionProduct!!.missingPromotionQuantity(info.quantity)
        val userResponse = userInteractionController.handlePromotionConfirmation(info.name, missingQuantity)
        userResponse.let {
            userInputValidator.validateUserInput(it)
            if (it == YES) info.addQuantity(missingQuantity)
        }
    }

    private fun handleStockConfirmation(info: PurchaseInfo, promotionProduct: PromotionProduct) {
        val quantityNeeded = info.quantity - promotionProduct.quantity
        val userResponse = userInteractionController.handleFullPriceConfirmation(info.name, quantityNeeded)

        userResponse.let {
            userInputValidator.validateUserInput(it)
            if (it == NO) info.minusQuantity(quantityNeeded)
        }
    }

    private fun isPromotionActive(product: Product): Boolean =
        product.promotionProduct?.isPromotionActive() == true

    private fun hasMissingQuantity(product: Product, quantity: Int): Boolean =
        product.promotionProduct?.missingPromotionQuantity(quantity)?.let { it > ZERO } ?: false

    companion object {
        private const val ZERO = 0
        private const val YES = "Y"
        private const val NO = "N"
    }
}
