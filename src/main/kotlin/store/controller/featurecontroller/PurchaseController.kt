package store.controller.featurecontroller

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
                    if (product.promotionProduct!!.isPromotionStockSufficient(info.quantity + product.promotionProduct!!.promotionType.freeQuantity)) handlePromotionConfirmation(
                        info,
                        product
                    )
                }
        }
        return purchaseInfos
    }

    fun checkPromotionStockSufficient(
        purchaseInfos: List<PurchaseInfo>,
        products: Map<String, Product>
    ): List<PurchaseInfo> {
        purchaseInfos.forEach { info ->
            products[info.name]
                ?.takeIf { product -> product.checkStock(info.quantity) }
                ?.promotionProduct
                ?.takeIf { it.isPromotionActive() && it.quantity > ZERO }
                ?.let { promotionProduct ->
                    handleStockConfirmation(info, promotionProduct)
                }
        }
        return purchaseInfos
    }

    fun checkBonusProducts(purchaseInfos: List<PurchaseInfo>, products: Map<String, Product>): List<PurchaseInfo> {
        val bonusProducts = mutableListOf<PurchaseInfo>()

        purchaseInfos.forEach { info ->
            products[info.name]?.promotionProduct?.takeIf { it.isPromotionActive() }?.let { promotionProduct ->
                val bonusQuantity = promotionProduct.calculateBonusQuantity(info.quantity)
                if (bonusQuantity > ZERO) bonusProducts.add(PurchaseInfo(info.name, bonusQuantity))
            }
        }
        return bonusProducts
    }

    private fun handlePromotionConfirmation(info: PurchaseInfo, product: Product) {
        val missingQuantity = product.promotionProduct!!.missingPromotionQuantity(info.quantity)
        while (true) {
            try {
                val userResponse = userInteractionController.handlePromotionConfirmation(info.name, missingQuantity)
                userResponse.let {
                    userInputValidator.validateUserInput(it)
                    if (it == YES) info.addQuantity(missingQuantity)
                }
                return
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
        }
    }

    private fun handleStockConfirmation(info: PurchaseInfo, promotionProduct: PromotionProduct) {
        val quantityNeeded = info.quantity - promotionProduct.calculateEligiblePromotionQuantity()
        while (quantityNeeded > ZERO) {
            try {
                val userResponse = userInteractionController.handleFullPriceConfirmation(info.name, quantityNeeded)
                userResponse.let {
                    userInputValidator.validateUserInput(it)
                    if (it == NO) info.minusQuantity(quantityNeeded)
                }
                return
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
        }
    }


    fun handleAdditionalPurchaseConfirmation(): Boolean {
        while (true) {
            try {
                val userResponse = userInteractionController.handleAdditionalPurchase()
                userResponse.let {
                    userInputValidator.validateUserInput(it)
                    if (it == YES) return true
                }
                return false
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
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
