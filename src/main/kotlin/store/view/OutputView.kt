package store.view

class OutputView {
    fun showProductInfo(productsInfo: List<String>) {
        println(WELCOME_MESSAGE)
        println(PRODUCT_AVAILABILITY_MESSAGE + NEW_LINE)
        println(productsInfo.joinToString("\n"))
        println(NEW_LINE + PURCHASE_PROMPT_MESSAGE)
    }

    companion object {
        private const val WELCOME_MESSAGE: String = "안녕하세요. W편의점입니다."
        private const val PRODUCT_AVAILABILITY_MESSAGE: String = "현재 보유하고 있는 상품입니다."
        private const val NEW_LINE = "\n"
        private const val PURCHASE_PROMPT_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"

    }
}