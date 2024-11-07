package store.controller.validator.purchaseInput

enum class PurchaseInputErrorType(
    private val _errorMessage: String
){
    EMPTY_INPUT("상품과 수량에 빈 문자를 입력했습니다. 다시 입력해 주세요"),
    N0_COMMA("쉼표(,)가 입력되지 않았습니다. 다시 입력해 주세요"),
    INVALID_BRACKET_FORMAT("개별 상품은 대괄호([])로 묶어야 합니다. 다시 입력해 주세요"),
    NO_HYPHEN("하이픈(-)이 입력되지 않았습니다. 다시 입력해 주세요"),
    NOT_INTEGER("수량은 1보다 큰 정수만 입력 가능합니다. 다시 입력해주세요."),
    ZERO_AMOUNT("수량에 0을 입력했습니다. 수량은 1보다 큰 정수만 입력 가능합니다. 다시 입력해주세요."),
    NEGATIVE_NUMBER("수량에 음수를 입력했습니다. 수량은 1보다 큰 정수만 입력 가능합니다. 다시 입력해주세요."),
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    DUPLICATE_PRODUCT("동일한 상품을 2번이상 입력했습니다. 다시 입력해 주세요."),
    EXCEEDS_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),;

    val errorMessage: String
        get() = ERROR_MESSAGE + _errorMessage

    companion object {
        private const val ERROR_MESSAGE = "[ERROR] "
    }
}