package store.view

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


    companion object {
        private const val WELCOME_MESSAGE: String = "안녕하세요. W편의점입니다."
        private const val PRODUCT_AVAILABILITY_MESSAGE: String = "현재 보유하고 있는 상품입니다."
        private const val NEW_LINE = "\n"
        private const val PURCHASE_PROMPT_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"
        private const val PROMOTION_ADDITIONAL_OFFER_MESSAGE = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"
        private const val PROMOTION_STOCK_SHORTAGE_MESSAGE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"

    }
}