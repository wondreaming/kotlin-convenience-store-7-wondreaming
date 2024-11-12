package store.controller.validator.purchaseInput

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import store.model.Product
import store.model.NonPromotionProduct
import org.junit.jupiter.api.Assertions.fail

class PurchaseInputValidatorTest {

    private lateinit var products: Map<String, Product>

    init {
        products = mapOf(
            "콜라" to Product("콜라", null, NonPromotionProduct(1000, 10)),
            "사이다" to Product("사이다", null, NonPromotionProduct(1500, 5))
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["[콜라-3]", "[사이다-2]", "[콜라-5], [사이다-3]"])
    @DisplayName("유효한 입력 - 형식이 올바르고 수량이 재고 이내인 경우")
    fun `유효한 입력이 정상적으로 처리되어야 함`(input: String) {
        val validator = PurchaseInputValidator(input, products)
        try {
            validator.validate()
        } catch (e: Exception) {
            fail("예외가 발생하지 않아야 합니다. 발생한 예외: ${e.message}")
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["[콜라-3], [콜라-2]", "[사이다-1], [사이다-4], [사이다-2]"])
    @DisplayName("중복된 제품명이 포함된 입력 - 예외 발생")
    fun `중복된 제품명이 포함된 입력은 예외가 발생해야 함`(input: String) {
        val validator = PurchaseInputValidator(input, products)
        val exception = assertThrows<IllegalArgumentException> {
            validator.validate()
        }
        assertEquals(PurchaseInputErrorType.DUPLICATE_PRODUCT.errorMessage, exception.message)
    }

    @ParameterizedTest
    @ValueSource(strings = ["[콜라-11]", "[사이다-6]", "[콜라-8], [사이다-6]"])
    @DisplayName("재고를 초과한 수량이 포함된 입력 - 예외 발생")
    fun `재고를 초과한 수량이 포함된 입력은 예외가 발생해야 함`(input: String) {
        val validator = PurchaseInputValidator(input, products)
        val exception = assertThrows<IllegalArgumentException> {
            validator.validate()
        }
        assertEquals(PurchaseInputErrorType.EXCEEDS_STOCK.errorMessage, exception.message)
    }

    @ParameterizedTest
    @ValueSource(strings = ["콜라-3", "[콜라,3]", "콜라3"])
    @DisplayName("잘못된 형식의 입력 - 예외 발생")
    fun `잘못된 형식의 입력은 예외가 발생해야 함`(input: String) {
        val validator = PurchaseInputValidator(input, products)
        val exception = assertThrows<IllegalArgumentException> {
            validator.validate()
        }
        assertEquals(PurchaseInputErrorType.INVALID_BRACKET_FORMAT.errorMessage, exception.message)
    }

    @ParameterizedTest
    @ValueSource(strings = ["[콜라-]"])
    @DisplayName("잘못된 형식의 입력 - 예외 발생")
    fun `잘못된 수량의 입력은 예외가 발생해야 함`(input: String) {
        val validator = PurchaseInputValidator(input, products)
        val exception = assertThrows<IllegalArgumentException> {
            validator.validate()
        }
        assertEquals(PurchaseInputErrorType.NOT_INTEGER.errorMessage, exception.message)
    }
}

