package store.controller.validator.userInput

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class UserInputValidatorTest {

    private val validator = UserInputValidator()

    @ParameterizedTest
    @ValueSource(strings = ["Y", "N"])
    @DisplayName("유효한 입력 - Y 또는 N")
    fun `유효한 입력이 정상적으로 처리되어야 함`(input: String) {
        assertDoesNotThrow {
            validator.validateUserInput(input)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = [""])
    @DisplayName("빈 입력 또는 공백 입력 - 예외 발생")
    fun `빈 입력이 입력될 경우 예외가 발생해야 함`(input: String) {
        val exception = assertThrows<IllegalArgumentException> {
            validator.validateUserInput(input)
        }
        assertEquals(UserInputErrorType.EMPTY_INPUT.errorMessage, exception.message)
    }

    @ParameterizedTest
    @ValueSource(strings = ["A", "1", "yes", "No", "Yy", "nN"])
    @DisplayName("잘못된 입력 - Y 또는 N 이외의 문자")
    fun `잘못된 입력이 입력될 경우 예외가 발생해야 함`(input: String) {
        val exception = assertThrows<IllegalArgumentException> {
            validator.validateUserInput(input)
        }
        assertEquals(UserInputErrorType.INVALID_CHAR.errorMessage, exception.message)
    }
}
