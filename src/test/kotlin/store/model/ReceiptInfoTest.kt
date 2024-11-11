package store.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import store.util.DateUtils

class ReceiptInfoTest {

    @Test
    fun `총 금액이 올바르게 계산되어야 함`() {
        val startDay = DateUtils.stringToLocalDateTime("2024-11-01")
        val endDay = DateUtils.stringToLocalDateTime("2024-11-30")
        val items = listOf(
            PurchaseInfo("콜라", 2),
            PurchaseInfo("사이다", 3)
        )
        val storeProducts = mapOf(
            "콜라" to Product("콜라", PromotionProduct(1000, 5, PromotionType("콜라", 2, 1, startDay, endDay)), null),
            "사이다" to Product("사이다", null, NonPromotionProduct(1500, 10))
        )
        val receiptInfo = ReceiptInfo(items, membership = Membership(), storeProducts = storeProducts)

        assertEquals(6500, receiptInfo.totalAmount)
    }

    @Test
    fun `프로모션 할인이 올바르게 계산되어야 함`() {
        val startDay = DateUtils.stringToLocalDateTime("2024-11-01")
        val endDay = DateUtils.stringToLocalDateTime("2024-11-30")
        val items = listOf(
            PurchaseInfo("콜라", 2)
        )
        val bonusItems = listOf(
            PurchaseInfo("콜라", 1)
        )
        val storeProducts = mapOf(
            "콜라" to Product("콜라", PromotionProduct(1000, 5, PromotionType("탄산2+1", 2, 1, startDay, endDay)), null)
        )
        val receiptInfo =
            ReceiptInfo(items, bonusItems = bonusItems, membership = Membership(), storeProducts = storeProducts)

        assertEquals(1000, receiptInfo.promotionDiscount)
    }

    @Test
    fun `멤버십 할인이 올바르게 계산되어야 함`() {
        val items = listOf(
            PurchaseInfo("콜라", 5)
        )
        val storeProducts = mapOf(
            "콜라" to Product("콜라", null, NonPromotionProduct(2000, 10))
        )
        val membership = Membership().apply { activateMembership() }
        val receiptInfo = ReceiptInfo(items, membership = membership, storeProducts = storeProducts)

        assertEquals(3000, receiptInfo.membershipDiscount)
    }

    @Test
    fun `총 수량이 올바르게 계산되어야 함`() {
        val items = listOf(
            PurchaseInfo("콜라", 2),
            PurchaseInfo("사이다", 3)
        )
        val storeProducts = mapOf(
            "콜라" to Product("콜라", null, NonPromotionProduct(1000, 10)),
            "사이다" to Product("사이다", null, NonPromotionProduct(1500, 10))
        )
        val receiptInfo = ReceiptInfo(items, membership = Membership(), storeProducts = storeProducts)

        assertEquals(5, receiptInfo.totalQuantity)
    }

    @Test
    fun `최종 결제 금액이 올바르게 계산되어야 함`() {
        val startDay = DateUtils.stringToLocalDateTime("2024-11-01")
        val endDay = DateUtils.stringToLocalDateTime("2024-11-30")
        val items = listOf(
            PurchaseInfo("콜라", 2),
            PurchaseInfo("사이다", 3)
        )
        val bonusItems = listOf(
            PurchaseInfo("콜라", 1)
        )
        val storeProducts = mapOf(
            "콜라" to Product("콜라", PromotionProduct(1000, 5, PromotionType("탄산2+1", 2, 1, startDay, endDay)), null),
            "사이다" to Product("사이다", null, NonPromotionProduct(1500, 10))
        )
        val membership = Membership().apply { activateMembership() }
        val receiptInfo =
            ReceiptInfo(items, bonusItems = bonusItems, membership = membership, storeProducts = storeProducts)

        assertEquals(4450, receiptInfo.finalAmount)
    }
}