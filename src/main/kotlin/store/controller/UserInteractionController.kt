package store.controller

import store.view.InputView
import store.view.OutputView

class UserInteractionController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun handlePurchaseInput(productsInfo: List<String>): String {
        outputView.showProductInfo(productsInfo)
        val purchaseInput = inputView.getPurchaseInput()
        return purchaseInput
    }

    fun handlePromotionConfirmation(name: String, quantity: Int): String {
        outputView.showPromotionAdditionalOffer(name, quantity)
        val userInput = inputView.getPromotionConfirmation()
        return userInput
    }

    fun handleFullPriceConfirmation(name: String, quantity: Int): String {
        outputView.showPromotionStockShortage(name, quantity)
        val userInput = inputView.getFullPriceConfirmation()
        return userInput
    }

    fun handleMembershipDiscount(): String {
        outputView.showMembershipDiscount()
        val userInput = inputView.getMembershipDiscount()
        return userInput
    }

    fun handleAdditionalPurchase(): String {
        outputView.showAdditionalPurchase()
        val userInput = inputView.getAdditionalPurchase()
        return userInput
    }
}