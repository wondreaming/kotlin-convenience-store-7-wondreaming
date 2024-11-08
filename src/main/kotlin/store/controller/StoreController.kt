package store.controller

import store.controller.adapter.ProductAdapter
import store.controller.adapter.PurchaseInfoAdapter
import store.controller.featurecontroller.ProductController
import store.controller.featurecontroller.PromotionTypeController
import store.controller.featurecontroller.PurchaseController
import store.controller.featurecontroller.UserInteractionController
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
    private val purchaseController: PurchaseController = PurchaseController(userInteractionController, userInputValidator),
) {
    fun run() {
        val products = productController.loadProducts()
        val productsInfo = productController.adaptProducts(products)

        val purchaseInput = userInteractionController.handlePurchaseInput(productsInfo)
        val purchaseInputValidator = PurchaseInputValidator(purchaseInput, products)

        purchaseInputValidator.validate()

        val purchaseInfoAdapter = PurchaseInfoAdapter(purchaseInput)
        val purchaseInfos = purchaseInfoAdapter.adaptPurchaseInfo()

        // 프로모션 확인과 재고 확인 처리
        val updatedPurchaseInfos = purchaseController.checkPromotionPurchase(purchaseInfos, products)
        val finalPurchaseInfos = purchaseController.checkPromotionStockSufficient(updatedPurchaseInfos, products)
    }
}