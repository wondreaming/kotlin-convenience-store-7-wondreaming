package store.controller.validator.purchaseInput

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

import org.junit.jupiter.api.Assertions.fail

class ItemValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = ["[콜라-3]", "[사이다-2]", "[물-1]"])
    @DisplayName("올바른 형식의 항목 - 예외 없음")
    fun `올바른 형식의 항목은 예외 없이 통과해야 함`(item: String) {
        val validator = ItemValidator(item)
        try {
            validator.validate()
        } catch (e: Exception) {
            fail("예외가 발생하지 않아야 합니다. 발생한 예외: ${e.message}")
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["콜라-3", "사이다-2", "물-1"])
    @DisplayName("잘못된 괄호 형식 - 예외 발생")
    fun `잘못된 괄호 형식의 항목은 예외가 발생해야 함`(item: String) {
        val validator = ItemValidator(item)
        val exception = assertThrows<IllegalArgumentException> {
            validator.validate()
        }
        assertEquals(PurchaseInputErrorType.INVALID_BRACKET_FORMAT.errorMessage, exception.message)
    }

    @ParameterizedTest
    @ValueSource(strings = ["[콜라3]", "[사이다 2]", "[물 1]"])
    @DisplayName("하이픈 구분자가 없는 항목 - 예외 발생")
    fun `하이픈 구분자가 없는 항목은 예외가 발생해야 함`(item: String) {
        val validator = ItemValidator(item)
        val exception = assertThrows<IllegalArgumentException> {
            validator.validate()
        }
        assertEquals(PurchaseInputErrorType.NO_HYPHEN.errorMessage, exception.message)
    }
}
