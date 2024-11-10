package store.controller.validator.purchaseInput

enum class PurchaseInputErrorType(
    private val _errorMessage: String
) {
    EMPTY_INPUT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요. 상품과 수량에 빈 문자를 입력했습니다."),
    N0_COMMA("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요. 쉼표(,)가 입력되지 않았습니다."),
    INVALID_BRACKET_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요. 개별 상품은 대괄호([])로 묶어야 합니다."),
    NO_HYPHEN("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요. 하이픈(-)이 입력되지 않았습니다."),
    NOT_INTEGER("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요. 수량은 1보다 큰 정수만 입력 가능합니다."),
    ZERO_AMOUNT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요. 수량에 0을 입력했습니다. 수량은 1보다 큰 정수만 입력 가능합니다."),
    NEGATIVE_NUMBER("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요. 수량에 음수를 입력했습니다. 수량은 1보다 큰 정수만 입력 가능합니다."),
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    DUPLICATE_PRODUCT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요. 동일한 상품을 2번이상 입력했습니다."),
    EXCEEDS_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    val errorMessage: String
        get() = ERROR_MESSAGE + _errorMessage

    companion object {
        private const val ERROR_MESSAGE = "[ERROR] "
    }
}