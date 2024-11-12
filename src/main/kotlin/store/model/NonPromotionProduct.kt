package store.model

data class NonPromotionProduct(
    override val price: Int,
    private var _quantity: Int,
) : ProductType {
    override val quantity: Int
        get() = _quantity
}