package store.controller.adapter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import store.model.*
import java.time.LocalDateTime


class ProductAdapterTest {

    private val productAdapter = ProductAdapter()

    @Test
    @DisplayName("프로모션 제품이 올바르게 표시되어야 함")
    fun `프로모션 제품 표시 테스트`() {
        val promotionType = PromotionType("탄산2+1", 2, 1, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1))
        val promotionProduct = PromotionProduct(price = 1000, _quantity = 5, _promotionType = promotionType)
        val products = mapOf("콜라" to Product("콜라", promotionProduct, null))

        val adaptedProducts = productAdapter.adaptProducts(products)
        assertEquals(listOf("- 콜라 1,000원 5개 탄산2+1"), adaptedProducts)
    }

    @Test
    @DisplayName("일반 제품이 올바르게 표시되어야 함")
    fun `일반 제품 표시 테스트`() {
        val nonPromotionProduct = NonPromotionProduct(price = 1500, _quantity = 3)
        val products = mapOf("사이다" to Product("사이다", null, nonPromotionProduct))

        val adaptedProducts = productAdapter.adaptProducts(products)
        assertEquals(listOf("- 사이다 1,500원 3개 "), adaptedProducts)
    }

    @Test
    @DisplayName("재고 없음이 올바르게 표시되어야 함")
    fun `재고 없음 표시 테스트`() {
        val nonPromotionProduct = NonPromotionProduct(price = 1500, _quantity = 0)
        val products = mapOf("사이다" to Product("사이다", null, nonPromotionProduct))

        val adaptedProducts = productAdapter.adaptProducts(products)
        assertEquals(listOf("- 사이다 1,500원 재고 없음 "), adaptedProducts)
    }
}
