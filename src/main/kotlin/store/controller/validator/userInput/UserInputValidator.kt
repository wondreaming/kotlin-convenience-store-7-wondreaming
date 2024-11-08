package store.controller.validator.userInput

import store.controller.validator.userInput.UserInputErrorType.*

class UserInputValidator {
    fun validateUserInput(userInput: String) {
        require(userInput.isNotEmpty()) { EMPTY_INPUT.errorMessage }
        require(userInput == "Y" || userInput == "N") { INVALID_CHAR.errorMessage }
    }
}