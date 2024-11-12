package store.controller.featurecontroller

import store.view.InputView
import store.view.OutputView

class UserInteractionController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun handlePurchaseInput(productsInfo: List<String>): String {
        outputView.showProductInfo(productsInfo)
        val purchaseInput = inputView.getInput()
        return purchaseInput
    }

    fun handlePromotionConfirmation(name: String, quantity: Int): String {
        outputView.showPromotionAdditionalOffer(name, quantity)
        val userInput = inputView.getInput()
        return userInput
    }

    fun handleFullPriceConfirmation(name: String, quantity: Int): String {
        outputView.showPromotionStockShortage(name, quantity)
        val userInput = inputView.getInput()
        return userInput
    }

    fun handleMembershipDiscount(): String {
        outputView.showMembershipDiscount()
        val userInput = inputView.getInput()
        return userInput
    }

    fun handleAdditionalPurchase(): String {
        outputView.showAdditionalPurchase()
        val userInput = inputView.getInput()
        return userInput
    }
}