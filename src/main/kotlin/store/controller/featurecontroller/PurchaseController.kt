package store.controller.featurecontroller

import store.controller.validator.userInput.UserInputValidator
import store.model.Product
import store.model.PromotionProduct
import store.model.PurchaseInfo

class PurchaseController(
    private val userInteractionController: UserInteractionController,
    private val userInputValidator: UserInputValidator
) {
    fun checkPromotionPurchase(
        purchaseInfos: List<PurchaseInfo>,
        products: Map<String, Product>
    ): List<PurchaseInfo> {
        purchaseInfos.forEach { info ->
            val product = products[info.name]
            if (shouldConfirmPromotion(product, info.quantity)) {
                handlePromotionConfirmation(info, product!!)
            }
        }
        return purchaseInfos
    }

    private fun shouldConfirmPromotion(product: Product?, quantity: Int): Boolean {
        return product != null &&
                isPromotionActive(product) &&
                hasMissingQuantity(product, quantity) &&
                isPromotionStockSufficient(product, quantity)
    }

    private fun isPromotionStockSufficient(product: Product, quantity: Int): Boolean {
        val promotionProduct = product.promotionProduct
        return promotionProduct?.isPromotionStockSufficient(quantity + promotionProduct.promotionType.freeQuantity) == true
    }

    fun checkPromotionStockSufficient(
        purchaseInfos: List<PurchaseInfo>,
        products: Map<String, Product>
    ): List<PurchaseInfo> {
        purchaseInfos.forEach { info ->
            val product = products[info.name]
            if (isStockSufficient(product, info.quantity)) {
                handleStockConfirmation(info, product!!.promotionProduct!!)
            }
        }
        return purchaseInfos
    }

    private fun isStockSufficient(product: Product?, quantity: Int): Boolean {
        val promotionProduct = product?.promotionProduct
        return product != null &&
                product.checkStock(quantity) &&
                promotionProduct?.isPromotionActive() == true &&
                promotionProduct.quantity > ZERO
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
        retryConfirmationValidInput { processPromotionConfirmation(info, missingQuantity) }
    }

    private fun processPromotionConfirmation(info: PurchaseInfo, missingQuantity: Int): Boolean {
        val userResponse = userInteractionController.handlePromotionConfirmation(info.name, missingQuantity)
        userInputValidator.validateUserInput(userResponse)
        if (userResponse == YES) info.addQuantity(missingQuantity)
        return true
    }

    private fun handleStockConfirmation(info: PurchaseInfo, promotionProduct: PromotionProduct) {
        val quantityNeeded = info.quantity - promotionProduct.calculateEligiblePromotionQuantity()
        if (quantityNeeded <= ZERO) return
        retryConfirmationValidInput { processStockConfirmation(info, quantityNeeded) }
    }

    private fun processStockConfirmation(info: PurchaseInfo, quantityNeeded: Int): Boolean {
        val userResponse = userInteractionController.handleFullPriceConfirmation(info.name, quantityNeeded)
        userInputValidator.validateUserInput(userResponse)
        if (userResponse == NO) info.minusQuantity(quantityNeeded)
        return true
    }

    private fun retryConfirmationValidInput(action: () -> Boolean) {
        while (true) {
            try {
                if (action()) return
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
        }
    }

    fun handleAdditionalPurchaseConfirmation(): Boolean {
        return retryUntilValidInput { processAdditionalPurchase() }
    }

    private fun processAdditionalPurchase(): Boolean {
        val userResponse = userInteractionController.handleAdditionalPurchase()
        userInputValidator.validateUserInput(userResponse)
        return userResponse == YES
    }

    private fun retryUntilValidInput(action: () -> Boolean): Boolean {
        while (true) {
            try {
                return action()
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