package store

import store.controller.ProductController
import store.controller.PromotionTypeController
import store.controller.PurchaseController
import store.controller.UserInteractionController
import store.controller.adapter.ProductAdapter
import store.controller.adapter.PurchaseInfoAdapter
import store.controller.validator.PurchaseInputValidator
import store.controller.validator.userInput.UserInputValidator
import store.view.InputView
import store.view.OutputView

fun main() {
    // TODO: 프로그램 구현
    val promotionTypeController = PromotionTypeController()

    val productAdapter = ProductAdapter()
    val productsController = ProductController(promotionTypeController, productAdapter)
    val productsInfol = productsController.loadProducts()

    val productsInfo = productsController.adaptProducts(productsInfol)
    val inputView = InputView()
    val outputView = OutputView()
    val userInteractionController = UserInteractionController(inputView, outputView)

    val purchaseInput = userInteractionController.handlePurchaseInput(productsInfo)
    val purchaseInputValidator = PurchaseInputValidator(purchaseInput, productsInfol)
    purchaseInputValidator.validate()
    val purchaseInfoAdapter = PurchaseInfoAdapter(purchaseInput)
    val purchaseInfo = purchaseInfoAdapter.adaptPurchaseInfo()
    val userInputValidator = UserInputValidator()
    val purchaseController = PurchaseController(userInteractionController, userInputValidator)
    val changeInfo = purchaseController.checkPromotionPurchase(purchaseInfo, productsInfol)
    val changeInfo2 = purchaseController.checkPromotionStockSufficient(changeInfo, productsInfol)
    println(changeInfo2)

    // 프로모션 제품 수량이 충분한지 검사
}
