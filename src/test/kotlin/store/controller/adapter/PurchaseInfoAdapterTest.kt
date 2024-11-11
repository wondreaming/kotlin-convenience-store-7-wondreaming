package store.controller.adapter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import store.model.PurchaseInfo

class PurchaseInfoAdapterTest {

    @Test
    @DisplayName("올바른 형식의 입력이 PurchaseInfo 객체로 변환되어야 함")
    fun `올바른 형식의 입력 처리 테스트`() {
        val adapter = PurchaseInfoAdapter("[콜라-2]")
        val purchaseInfoList = adapter.adaptPurchaseInfo()
        assertEquals(listOf(PurchaseInfo("콜라", 2)), purchaseInfoList)
    }

    @Test
    @DisplayName("여러 개의 항목이 올바르게 변환되어야 함")
    fun `여러 개의 항목 처리 테스트`() {
        val adapter = PurchaseInfoAdapter("[콜라-2], [사이다-3]")
        val purchaseInfoList = adapter.adaptPurchaseInfo()
        assertEquals(
            listOf(
                PurchaseInfo("콜라", 2),
                PurchaseInfo("사이다", 3)
            ), purchaseInfoList
        )
    }
}
