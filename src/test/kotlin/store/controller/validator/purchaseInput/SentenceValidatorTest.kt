package store.controller.validator.purchaseInput

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SentenceValidatorTest {

    @Test
    @DisplayName("유효한 입력 - 예외 없음")
    fun `유효한 입력이 정상적으로 처리되어야 함`() {
        val validator = SentenceValidator("[콜라-3], [사이다-2]", 2)
        var isValid = true
        try {
            validator.validate()
        } catch (e: Exception) {
            isValid = false
        }
        assertTrue(isValid)
    }

    @Test
    @DisplayName("빈 입력 - 예외 발생")
    fun `빈 입력이 입력될 경우 예외가 발생해야 함`() {
        val validator = SentenceValidator("", 0)
        val exception = assertThrows<IllegalArgumentException> {
            validator.validate()
        }
        assertEquals(PurchaseInputErrorType.EMPTY_INPUT.errorMessage, exception.message)
    }

    @Test
    @DisplayName("구분자가 없는 경우 - 예외 발생")
    fun `구분자가 필요한 경우 구분자가 없으면 예외가 발생해야 함`() {
        val validator = SentenceValidator("[콜라-3] [사이다-2]", 2)
        val exception = assertThrows<IllegalArgumentException> {
            validator.validate()
        }
        assertEquals(PurchaseInputErrorType.N0_COMMA.errorMessage, exception.message)
    }
}
