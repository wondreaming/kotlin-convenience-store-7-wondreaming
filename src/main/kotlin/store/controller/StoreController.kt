package store.controller

import store.controller.adapter.ProductAdapter
import store.controller.adapter.PurchaseInfoAdapter
import store.controller.featurecontroller.*
import store.controller.validator.PurchaseInputValidator
import store.controller.validator.userInput.UserInputValidator
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
    )
) {
    fun run() {
        val storeProducts = productController.loadProducts()
        val displayProductsInfo = productController.adaptProducts(storeProducts)
        val purchaseInput = userInteractionController.handlePurchaseInput(displayProductsInfo)
        // 사용자가 입력한 구매 데이터가 타당한지 확인하는 로직ㄱ
        val purchaseInputValidator = PurchaseInputValidator(purchaseInput, storeProducts)
        purchaseInputValidator.validate()
        // 사용자가 입력한 구매 String 데이터 -> PurchaseInfo로 데이터 타입 변환
        val purchaseInfoAdapter = PurchaseInfoAdapter(purchaseInput)
        val purchaseInfo = purchaseInfoAdapter.adaptPurchaseInfo()

        // 프로모션 확인과 재고 확인 처리
        val updatedPurchaseInfo = purchaseController.checkPromotionPurchase(purchaseInfo, storeProducts)
        val finalPurchaseInfos = purchaseController.checkPromotionStockSufficient(updatedPurchaseInfo, storeProducts)
    }
}