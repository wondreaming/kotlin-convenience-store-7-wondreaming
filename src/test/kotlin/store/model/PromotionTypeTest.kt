package store.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import store.model.PromotionType
import store.util.DateUtils
import java.time.LocalDateTime

class PromotionTypeTest {

    @Test
    fun `프로모션이 활성 상태여야 함`() {
        val startDay = DateUtils.stringToLocalDateTime("2024-11-01")
        val endDay = DateUtils.stringToLocalDateTime("2024-11-30")
        val promotionType = PromotionType(
            name = "탄산2+1",
            buyQuantity = 2,
            freeQuantity = 1,
            startDate = startDay,
            endDate = endDay
        )
        assertTrue(promotionType.isPromotionActive())
    }

    @Test
    fun `프로모션이 시작되기 전이면 비활성 상태여야 함`() {
        val startDate = LocalDateTime.now().plusDays(1)
        val endDate = LocalDateTime.now().plusDays(2)
        val promotionType = PromotionType(
            name = "탄산2+1",
            buyQuantity = 2,
            freeQuantity = 1,
            startDate = startDate,
            endDate = endDate
        )
        assertFalse(promotionType.isPromotionActive())
    }

    @Test
    fun `프로모션이 종료된 후에는 비활성 상태여야 함`() {
        val startDate = LocalDateTime.now().minusDays(2)
        val endDate = LocalDateTime.now().minusDays(1)
        val promotionType = PromotionType(
            name = "탄산2+1",
            buyQuantity = 2,
            freeQuantity = 1,
            startDate = startDate,
            endDate = endDate
        )
        assertFalse(promotionType.isPromotionActive())
    }
}
