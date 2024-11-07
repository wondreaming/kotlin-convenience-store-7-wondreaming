package store.model

class Product(
    val name: String,
    val promotionProduct: PromotionProduct?,
    val nonPromotionProduct: NonPromotionProduct?,
)