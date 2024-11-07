package store.controller.validator.userInput

enum class UserInputErrorType(
    private val _errorMessage: String,
) {
    EMPTY_INPUT("빈 문자를 입력했습니다. 다시 입력해 주세요."),
    LOWERCASE("소문자를 입력했습니다. 다시 입력해 주세요"),
    KOREAN("한국어를 입력했습니다. 다시 입력해 주세요"),
    INVALID_CHAR("Y 또는 N만 입력이 가능합니다. 다시 입력해 주세요");

    val errorMessage: String
        get() = ERROR_MESSAGE + _errorMessage

    companion object {
        private const val ERROR_MESSAGE = "[ERROR] "
    }
}