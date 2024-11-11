import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import store.model.PromotionProduct
import store.model.PromotionType
import store.util.DateUtils

class PromotionProductTest {

    private lateinit var promotionProduct: PromotionProduct

    @BeforeEach
    fun setup() {
        val startDay = DateUtils.stringToLocalDateTime("2024-11-01")
        val endDay = DateUtils.stringToLocalDateTime("2024-11-30")
        val promotionType = PromotionType(name = "탄산2+1", buyQuantity = 2, freeQuantity = 1, startDate = startDay, endDate = endDay)
        promotionProduct = PromotionProduct(price = 1000, _quantity = 10, _promotionType = promotionType)
    }

    @Test
    fun `프로모션이 활성 상태인지 확인`() {
        assertTrue(promotionProduct.isPromotionActive())
    }

    @Test
    fun `프로모션 부족 수량 계산`() {
        val missingQuantity = promotionProduct.missingPromotionQuantity(buyQuantity = 2)
        assertEquals(1, missingQuantity)
    }

    @Test
    fun `요청된 수량에 대해 재고가 충분한지 확인`() {
        assertTrue(promotionProduct.isPromotionStockSufficient(requiredQuantity = 5))
        assertFalse(promotionProduct.isPromotionStockSufficient(requiredQuantity = 15))
    }

    @Test
    fun `적격 프로모션 수량을 올바르게 계산`() {
        val eligiblePromotionQuantity = promotionProduct.calculateEligiblePromotionQuantity()
        assertEquals(9, eligiblePromotionQuantity)
    }

    @Test
    fun `구매한 수량에 따른 보너스 수량 계산`() {
        val bonusQuantity = promotionProduct.calculateBonusQuantity(purchasedQuantity = 6)
        assertEquals(2, bonusQuantity)
    }
}
