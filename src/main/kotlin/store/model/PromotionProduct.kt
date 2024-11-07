package store.model

data class PromotionProduct(
    override val price: Int,
    private var _quantity: Int,
    private var _promotionType: PromotionType
) : ProductType {
    override val quantity: Int
        get() = _quantity
    val promotionType: PromotionType
        get() = _promotionType
}