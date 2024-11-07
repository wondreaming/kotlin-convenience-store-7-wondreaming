package store.view

import camp.nextstep.edu.missionutils.Console

class InputView {
    fun getInput(): String {
        val input = Console.readLine().trim()
        return input
    }

    fun closeInput() {
        Console.close()
    }
}