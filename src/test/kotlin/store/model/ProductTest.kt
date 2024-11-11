package store.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import store.util.DateUtils

class ProductTest {

    @Test
    fun `재고가 충분한지 확인`() {
        val startDay = DateUtils.stringToLocalDateTime("2024-11-01")
        val endDay = DateUtils.stringToLocalDateTime("2024-11-30")
        val promotionType = PromotionType(name = "탄산2+1", buyQuantity = 2, freeQuantity = 1, startDate = startDay, endDate = endDay)
        val promotionProduct = PromotionProduct(price = 1000, _quantity = 5, _promotionType = promotionType)
        val nonPromotionProduct = NonPromotionProduct(price = 1000, _quantity = 10)
        val product = Product(name = "콜라", _promotionProduct = promotionProduct, _nonPromotionProduct = nonPromotionProduct)

        assertTrue(product.checkStock(10))
        assertFalse(product.checkStock(20))
    }

    @Test
    fun `프로모션 제품을 업데이트할 수 있어야 함`() {
        val startDay = DateUtils.stringToLocalDateTime("2024-11-01")
        val endDay = DateUtils.stringToLocalDateTime("2024-11-30")
        val promotionType = PromotionType(name = "탄산2+1", buyQuantity = 2, freeQuantity = 1, startDate = startDay, endDate = endDay)
        val product = Product(name = "콜라", _promotionProduct = null, _nonPromotionProduct = null)
        val newPromotionProduct = PromotionProduct(price = 1000, _quantity = 5, _promotionType= promotionType)

        product.updatePromotionProduct(newPromotionProduct)
        assertEquals(newPromotionProduct, product.promotionProduct)
    }

    @Test
    fun `일반 제품을 업데이트할 수 있어야 함`() {
        val product = Product(name =  "콜라", _promotionProduct = null, _nonPromotionProduct = null)
        val newNonPromotionProduct = NonPromotionProduct(price = 1000, _quantity = 10)

        product.updateNonPromotionProduct(newNonPromotionProduct)
        assertEquals(newNonPromotionProduct, product.nonPromotionProduct, "일반 제품이 올바르게 업데이트되어야 합니다.")
    }

    @Test
    fun `프로모션 및 일반 제품의 수량이 요청된 수량만큼 감소해야 함`() {
        val startDay = DateUtils.stringToLocalDateTime("2024-11-01")
        val endDay = DateUtils.stringToLocalDateTime("2024-11-30")
        val promotionType = PromotionType(name = "탄산2+1", buyQuantity = 2, freeQuantity = 1, startDate = startDay, endDate = endDay)
        val promotionProduct = PromotionProduct(price = 1000, _quantity = 5, _promotionType = promotionType)
        val nonPromotionProduct = NonPromotionProduct(price = 1000, _quantity = 10)
        val product = Product(name =  "콜라", _promotionProduct = promotionProduct, _nonPromotionProduct = nonPromotionProduct)

        product.reduceQuantity(8)
        assertEquals(0, product.promotionProduct!!.quantity)
        assertEquals(7, product.nonPromotionProduct!!.quantity)
    }

    @Test
    fun `일반 제품 수량이 요청된 만큼 증가해야 함`() {
        val nonPromotionProduct = NonPromotionProduct(price = 1000, _quantity = 10)
        val product = Product(name =  "콜라", _promotionProduct = null, _nonPromotionProduct = nonPromotionProduct)

        product.updateNonPromotionQuantity(5)
        assertEquals(15, product.nonPromotionProduct!!.quantity)
    }

    @Test
    fun `제품이 Map에 존재하는지 확인`() {
        val product = Product(name =  "콜라", _promotionProduct = null, _nonPromotionProduct = null)
        val products = mapOf( "콜라" to product)

        assertTrue(Product.isProductAvailable( "콜라", products))
        assertFalse(Product.isProductAvailable("NonExistent", products))
    }
}