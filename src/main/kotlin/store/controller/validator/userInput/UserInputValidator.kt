package store.controller.validator.userInput

import store.controller.validator.userInput.UserInputErrorType.*

class UserInputValidator {
    fun validateUserInput(userInput: String) {
        require(userInput.isNotEmpty()) { EMPTY_INPUT.errorMessage }
        require(userInput == YES || userInput == NO) { INVALID_CHAR.errorMessage }
    }

    companion object {
        private const val YES = "Y"
        private const val NO = "N"
    }
}