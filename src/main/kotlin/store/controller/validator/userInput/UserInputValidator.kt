package store.controller.validator.userInput

import store.controller.validator.userInput.UserInputErrorType.*

class UserInputValidator(
    private val userInput: String,
) {
    fun validateUserInput() {
        require(userInput.isNotEmpty()) { EMPTY_INPUT.errorMessage }
        require(userInput == "Y" || userInput == "N") { INVALID_CHAR.errorMessage }
    }
}