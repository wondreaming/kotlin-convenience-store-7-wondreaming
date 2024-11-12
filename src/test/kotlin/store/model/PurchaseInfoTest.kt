package store.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PurchaseInfoTest {

    @Test
    fun `초기 수량이 올바르게 설정되어야 함`() {
        val purchaseInfo = PurchaseInfo(name = "콜라", _quantity = 10)
        assertEquals(10, purchaseInfo.quantity)
    }

    @Test
    fun `수량이 올바르게 추가되어야 함`() {
        val purchaseInfo = PurchaseInfo(name = "콜라", _quantity = 10)
        purchaseInfo.addQuantity(5)
        assertEquals(15, purchaseInfo.quantity)
    }

    @Test
    fun `수량이 올바르게 감소되어야 함`() {
        val purchaseInfo = PurchaseInfo(name = "콜라", _quantity = 10)
        purchaseInfo.minusQuantity(3)
        assertEquals(7, purchaseInfo.quantity)
    }
}
