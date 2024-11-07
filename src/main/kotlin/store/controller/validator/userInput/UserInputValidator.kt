package store.controller.validator.userInput

import store.controller.validator.userInput.UserInputErrorType.*

class UserInputValidator(
    private val userInput: String,
) {
    fun validateUserInput() {
        require(userInput.isNotEmpty()) { EMPTY_INPUT.errorMessage }
        require(userInput != "y" && userInput != "n") { LOWERCASE.errorMessage }
        require(userInput != "네" && userInput != "아니요") { KOREAN.errorMessage }
        require(userInput != "Y" && userInput != "N") { INVALID_CHAR.errorMessage }
    }
}