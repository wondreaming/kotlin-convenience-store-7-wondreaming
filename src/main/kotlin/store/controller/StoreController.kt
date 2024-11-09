package store.controller

import store.controller.adapter.ProductAdapter
import store.controller.adapter.PurchaseInfoAdapter
import store.controller.featurecontroller.*
import store.controller.validator.PurchaseInputValidator
import store.controller.validator.userInput.UserInputValidator
import store.model.Membership
import store.model.ReceiptInfo
import store.view.InputView
import store.view.OutputView

class StoreController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView(),
    private val productAdapter: ProductAdapter = ProductAdapter(),
    private val promotionTypeController: PromotionTypeController = PromotionTypeController(),
    private val productController: ProductController = ProductController(promotionTypeController, productAdapter),
    private val userInteractionController: UserInteractionController = UserInteractionController(inputView, outputView),
    private val userInputValidator: UserInputValidator = UserInputValidator(),
    private val purchaseController: PurchaseController = PurchaseController(
        userInteractionController,
        userInputValidator
    ),
    private val membershipController: MembershipController = MembershipController(
        userInteractionController,
        userInputValidator
    ),
    private val receiptController: ReceiptController = ReceiptController()
) {
    fun run() {
        var again = true
        val storeProducts = productController.loadProducts()
        while(again) {

            val displayProductsInfo = productController.adaptProducts(storeProducts)
            val purchaseInput = userInteractionController.handlePurchaseInput(displayProductsInfo)
            // 사용자가 입력한 구매 데이터가 타당한지 확인하는 로직
            val purchaseInputValidator = PurchaseInputValidator(purchaseInput, storeProducts)
            purchaseInputValidator.validate()
            // 사용자가 입력한 구매 String 데이터 -> PurchaseInfo로 데이터 타입 변환
            val purchaseInfoAdapter = PurchaseInfoAdapter(purchaseInput)
            val purchaseInfo = purchaseInfoAdapter.adaptPurchaseInfo()

            // 프로모션 확인과 재고 확인 처리 -> 여기서 프로모션 적용 여부 알려줌
            val updatedPurchaseInfo = purchaseController.checkPromotionPurchase(purchaseInfo, storeProducts)
            val finalPurchaseInfo = purchaseController.checkPromotionStockSufficient(updatedPurchaseInfo, storeProducts)

            // 총 구매한거 나옴
            // 프로모션 증정 제품 계산 로직 불러오기
            val bonusProducts = purchaseController.checkBonusProducts(finalPurchaseInfo, storeProducts)
            // 멤버십 할인 적용하기
            val membership = Membership()
            val userMembership = membershipController.applyMembershipDiscount(membership)
            println(finalPurchaseInfo)
            println(bonusProducts)
            println(finalPurchaseInfo)
            println(userMembership)

            val receiptInfo = ReceiptInfo(
                _items = finalPurchaseInfo,
                bonusItems = bonusProducts,
                membership = membership,
                storeProducts = storeProducts
            )
            println("==============W 편의점================")
            println("%-8s %5s %8s".format("상품명", "수량", "금액"))
            receiptInfo.items.forEach { item ->
                val itemTotalAmount = receiptInfo.getItemPrice(item.name) * item.quantity
                println(String.format("%-8s %5d %,8d", item.name, item.quantity, itemTotalAmount))
            }
            println("=============증\t\t정===============")
            receiptInfo.bonusItems.forEach { bonusItem ->
                println(String.format("%-8s %,8d", bonusItem.name, bonusItem.quantity))
            }
            println("====================================")
            println(
                String.format(
                    "%-8s %5d %,8d",
                    "총구매액",
                    receiptInfo.totalQuantity,
                    receiptInfo.totalAmount,

                    )
            )
            if (receiptInfo.promotionDiscount != 0) {
                println(String.format("%-8s %,8d", "행사할인", receiptInfo.promotionDiscount))
            }
            if (receiptInfo.membershipDiscount != 0) {
                println(String.format("%-8s %,8d", "멤버십할인", receiptInfo.membershipDiscount))
            }

            println(String.format("%-8s %,8d", "내실돈", receiptInfo.finalAmount))

            again = purchaseController.handleAdditionalPurchaseConfirmation()
            // storeProducts수량 조절

        }

    }
}