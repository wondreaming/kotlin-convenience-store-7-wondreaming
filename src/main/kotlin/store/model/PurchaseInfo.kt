package store.model

data class PurchaseInfo(
    val name: String,
    private var _quantity: Int,
) {
    val quantity: Int
        get() = _quantity

    fun addQuantity(amount: Int) {
        _quantity += amount
    }

    fun minusQuantity(amount: Int) {
        _quantity -= amount
    }
}