package store.controller.validator.userInput

enum class UserInputErrorType(
    private val _errorMessage: String,
) {
    EMPTY_INPUT("잘못된 입력입니다. 다시 입력해 주세요. 빈 문자를 입력했습니다."),
    INVALID_CHAR("잘못된 입력입니다. 다시 입력해 주세요. Y 또는 N만 입력이 가능합니다.");

    val errorMessage: String
        get() = ERROR_MESSAGE + _errorMessage

    companion object {
        private const val ERROR_MESSAGE = "[ERROR] "
    }
}