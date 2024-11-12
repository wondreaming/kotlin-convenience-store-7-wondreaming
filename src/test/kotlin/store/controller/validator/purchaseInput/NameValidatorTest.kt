package store.controller.validator.purchaseInput

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import store.model.Product
import store.model.NonPromotionProduct


class NameValidatorTest {

    private val products = mapOf(
        "콜라" to Product("콜라", null, NonPromotionProduct(1000, 10)),
        "사이다" to Product("사이다", null, NonPromotionProduct(1500, 5))
    )

    @Test
    @DisplayName("유효한 제품명이 정상적으로 처리되어야 함")
    fun `유효한 제품명이 정상적으로 검증되어야 함`() {
        val validator = NameValidator("콜라", products)
        var isValid = true
        try {
            validator.validate()
        } catch (e: Exception) {
            isValid = false
        }
        assertTrue(isValid)
    }

    @Test
    @DisplayName("존재하지 않는 제품명이 입력될 경우 예외가 발생해야 함")
    fun `존재하지 않는 제품명은 예외가 발생해야 함`() {
        val validator = NameValidator("없는제품", products)
        val exception = assertThrows<IllegalArgumentException> {
            validator.validate()
        }
        assertEquals(PurchaseInputErrorType.PRODUCT_NOT_FOUND.errorMessage, exception.message)
    }
}
