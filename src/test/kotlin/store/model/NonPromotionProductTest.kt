package store.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import store.model.NonPromotionProduct

class NonPromotionProductTest {

    @Test
    fun `NonPromotionProduct 생성 시 가격이 올바르게 설정되어야 함`() {
        val product = NonPromotionProduct(price = 1500, _quantity = 10)
        assertEquals(1500, product.price)
    }

    @Test
    fun `NonPromotionProduct 생성 시 수량이 올바르게 설정되어야 함`() {
        val product = NonPromotionProduct(price = 1500, _quantity = 10)
        assertEquals(10, product.quantity)
    }
}