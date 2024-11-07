package store.view

import camp.nextstep.edu.missionutils.Console

class InputView {
    fun getPurchaseInput(): String {
        val purchaseInput = getInput()
        return purchaseInput
    }

    fun getPromotionConfirmation(): String {
        val userInput = getInput()
        return userInput
    }

    fun getFullPriceConfirmation(): String {
        val userInput = getInput()
        return userInput
    }

    fun getMembershipDiscount(): String {
        val userInput = getInput()
        return userInput
    }

    fun getAdditionalPurchase(): String {
        val userInput = getInput()
        return userInput
    }

    private fun getInput(): String {
        val input = Console.readLine().trim()
        return input
    }

    fun closeInput() {
        Console.close()
    }
}