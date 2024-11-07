package store.model

class Product(
    val name: String,
    val promotionProduct: PromotionalProduct?,
    val nonPromotionProduct: NonPromotionalProduct?,
)