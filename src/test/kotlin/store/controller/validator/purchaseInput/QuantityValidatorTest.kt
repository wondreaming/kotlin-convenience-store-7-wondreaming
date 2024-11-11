package store.controller.validator.purchaseInput

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class QuantityValidatorTest {

    @Test
    @DisplayName("유효한 정수 입력 - 예외 없음")
    fun `유효한 정수 입력이 정상적으로 처리되어야 함`() {
        val validator = QuantityValidator("5")
        var isValid = true
        try {
            validator.validate()
        } catch (e: Exception) {
            isValid = false
        }
        assertTrue(isValid)
    }

    @Test
    @DisplayName("정수가 아닌 입력 - 예외 발생")
    fun `정수가 아닌 입력은 예외가 발생해야 함`() {
        val validator = QuantityValidator("5.5")
        val exception = assertThrows<IllegalArgumentException> {
            validator.validate()
        }
        assertEquals(PurchaseInputErrorType.NOT_INTEGER.errorMessage, exception.message)
    }

    @Test
    @DisplayName("0인 수량 입력 - 예외 발생")
    fun `0인 수량 입력은 예외가 발생해야 함`() {
        val validator = QuantityValidator("0")
        val exception = assertThrows<IllegalArgumentException> {
            validator.validate()
        }
        assertEquals(PurchaseInputErrorType.ZERO_AMOUNT.errorMessage, exception.message)
    }

    @Test
    @DisplayName("음수인 수량 입력 - 예외 발생")
    fun `음수인 수량 입력은 예외가 발생해야 함`() {
        val validator = QuantityValidator("-1")
        val exception = assertThrows<IllegalArgumentException> {
            validator.validate()
        }
        assertEquals(PurchaseInputErrorType.NEGATIVE_NUMBER.errorMessage, exception.message)
    }
}
