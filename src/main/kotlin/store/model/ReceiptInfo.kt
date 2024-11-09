package store.model

data class ReceiptInfo(
    val items: List<PurchaseInfo>,
    val totalQuantity: Int,
    val totalAmount: Int,
    val additionalItems: List<Product> = listOf(),
    val eventDiscount: Int = 0,
    val membershipDiscount: Int = 0,
) {
    val finalAmount: Int
        get() = totalAmount - eventDiscount - membershipDiscount
}

