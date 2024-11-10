package store.view

import store.model.ReceiptInfo

class OutputView {
    fun showProductInfo(productsInfo: List<String>) {
        println(WELCOME_MESSAGE)
        println(PRODUCT_AVAILABILITY_MESSAGE + NEW_LINE)
        println(productsInfo.joinToString(NEW_LINE))
        println(NEW_LINE + PURCHASE_PROMPT_MESSAGE)
    }

    fun showPromotionAdditionalOffer(name: String, quantity: Int) {
        val formattedMessage = String.format(PROMOTION_ADDITIONAL_OFFER_MESSAGE, name, quantity)
        println(formattedMessage)
    }

    fun showPromotionStockShortage(name: String, quantity: Int) {
        val formattedMessage = String.format(PROMOTION_STOCK_SHORTAGE_MESSAGE, name, quantity)
        println(formattedMessage)
    }

    fun showMembershipDiscount() {
        println(NEW_LINE + MEMBERSHIP_DISCOUNT_MESSAGE)
    }

    fun showAdditionalPurchase() {
        println(NEW_LINE + ADDITIONAL_PURCHASE_MESSAGE)
    }

    fun showReceipt(receiptInfo: ReceiptInfo) {
        println(RECEIPT_HEADER)
        showItems(receiptInfo)
        showBonusItems(receiptInfo)
        showTotal(receiptInfo)
    }

    private fun showItems(receiptInfo: ReceiptInfo) {
        println(RECEIPT_HEADER_FORMAT.format(NAME, QUANTITY, PRICE))
        receiptInfo.items.forEach { item ->
            val itemTotalAmount = receiptInfo.getItemPrice(item.name) * item.quantity
            println(RECEIPT_ITEM_FORMAT.format(item.name, item.quantity, itemTotalAmount))
        }
    }

    private fun showBonusItems(receiptInfo: ReceiptInfo) {
        println(RECEIPT_BONUS_HEADER)
        receiptInfo.bonusItems.forEach { bonusItem ->
            println(RECEIPT_BONUS_FORMAT.format(bonusItem.name, bonusItem.quantity))
        }
    }

    private fun showTotal(receiptInfo: ReceiptInfo) {
        println(RECEIPT_DIVIDER)
        println(RECEIPT_TOTAL_FORMAT.format(TOTAL_QUANTITY, receiptInfo.totalQuantity, receiptInfo.totalAmount))
        println(RECEIPT_DISCOUNT_FORMAT.format(PROMOTION_DISCOUNT, formatDiscount(receiptInfo.promotionDiscount)))
        println(RECEIPT_DISCOUNT_FORMAT.format(MEMBERSHIP_DISCOUNT, formatDiscount(receiptInfo.membershipDiscount)))
        println(RECEIPT_DISCOUNT_FORMAT.format(FINAL_AMOUNT, formatFinalAmount(receiptInfo.finalAmount)))
    }

    private fun formatDiscount(discount: Int): String {
        return DISCOUNT_FORMAT.format(discount)
    }

    private fun formatFinalAmount(discount: Int): String {
        return FINAL_AMOUNT_FORMAT.format(discount)
    }

    companion object {
        private const val WELCOME_MESSAGE: String = "안녕하세요. W편의점입니다."
        private const val PRODUCT_AVAILABILITY_MESSAGE: String = "현재 보유하고 있는 상품입니다."
        private const val NEW_LINE = "\n"
        private const val PURCHASE_PROMPT_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"
        private const val PROMOTION_ADDITIONAL_OFFER_MESSAGE = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"
        private const val PROMOTION_STOCK_SHORTAGE_MESSAGE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"
        private const val MEMBERSHIP_DISCOUNT_MESSAGE = "멤버십 할인을 받으시겠습니까? (Y/N)"
        private const val ADDITIONAL_PURCHASE_MESSAGE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"
        private const val RECEIPT_HEADER = "\n==============W 편의점================"
        private const val RECEIPT_HEADER_FORMAT = "%-15s %5s %8s"
        private const val RECEIPT_ITEM_FORMAT = "%-15s %5d %,8d"
        private const val RECEIPT_BONUS_FORMAT = "%-8s %,8d"
        private const val RECEIPT_TOTAL_FORMAT = "%-15s %5d %,8d"
        private const val RECEIPT_DISCOUNT_FORMAT = "%-15s %8s"
        private const val RECEIPT_BONUS_HEADER = "=============증\t\t정==============="
        private const val RECEIPT_DIVIDER = "===================================="
        private const val TOTAL_QUANTITY = "총구매액"
        private const val PROMOTION_DISCOUNT = "총구매액"
        private const val MEMBERSHIP_DISCOUNT = "맴버십할인"
        private const val FINAL_AMOUNT = "내실돈"
        private const val NAME = "상품명"
        private const val QUANTITY = "수량"
        private const val PRICE = "금액"
        private const val DISCOUNT_FORMAT = "-%,d"
        private const val FINAL_AMOUNT_FORMAT = "%,d"
    }
}