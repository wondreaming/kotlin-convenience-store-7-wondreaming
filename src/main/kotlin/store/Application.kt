package store

import store.controller.StoreController
import store.controller.featurecontroller.ProductController
import store.controller.featurecontroller.PromotionTypeController
import store.controller.featurecontroller.PurchaseController
import store.controller.featurecontroller.UserInteractionController
import store.controller.adapter.ProductAdapter
import store.controller.validator.userInput.UserInputValidator
import store.view.InputView
import store.view.OutputView

fun main() {
    // TODO: 프로그램 구현
    val inputView = InputView()
    val outputView = OutputView()
    val productAdapter = ProductAdapter()
    val promotionTypeController = PromotionTypeController()
    val productController = ProductController(promotionTypeController, productAdapter)
    val userInteractionController = UserInteractionController(inputView, outputView)
    val userInputValidator = UserInputValidator()
    val purchaseController = PurchaseController(userInteractionController, userInputValidator)

    val storeController = StoreController(
        inputView = inputView,
        outputView = outputView,
        productAdapter = productAdapter,
        promotionTypeController = promotionTypeController,
        productController = productController,
        userInteractionController = userInteractionController,
        userInputValidator = userInputValidator,
        purchaseController = purchaseController
    )

    storeController.run()
}
