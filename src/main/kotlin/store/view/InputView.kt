package store.view

import camp.nextstep.edu.missionutils.Console

object InputView {
    fun getPurchaseInput(): String {
        val purchaseInput = getInput()
        return purchaseInput
    }

    private fun getInput(): String {
        val input = Console.readLine()
        return input
    }

    fun closeInput() {
        Console.close()
    }
}