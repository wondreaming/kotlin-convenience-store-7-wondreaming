package store.controller

import store.controller.adapter.ProductAdapter
import store.controller.adapter.PurchaseInfoAdapter
import store.controller.featurecontroller.*
import store.controller.validator.purchaseInput.PurchaseInputValidator
import store.controller.validator.userInput.UserInputValidator
import store.model.Membership
import store.model.Product
import store.model.PurchaseInfo
import store.model.ReceiptInfo
import store.view.InputView
import store.view.OutputView
import java.io.File

class StoreController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView(),
    private val productAdapter: ProductAdapter = ProductAdapter(),
    private val promotionTypeController: PromotionTypeController = PromotionTypeController(),
    private val productController: ProductController = ProductController(promotionTypeController),
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
) {
    fun run() {
        var again = true
        val storeProducts = productController.loadProducts()
        while (again) {
            try {
                handlePurchaseProcess(storeProducts)
                again = purchaseController.handleAdditionalPurchaseConfirmation()
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
        }
    }

    private fun handlePurchaseProcess(storeProducts: Map<String, Product>) {
        val displayProductsInfo = productAdapter.adaptProducts(storeProducts)
        val purchaseInput = validatePurchaseInput(displayProductsInfo, storeProducts)
        val purchaseInfo = adaptPurchaseInfo(purchaseInput)
        val finalPurchaseInfo = processPromotionAndStock(purchaseInfo, storeProducts)
        generateAndDisplayReceipt(finalPurchaseInfo, storeProducts)
        updateProductQuantities(finalPurchaseInfo, storeProducts)
    }

    private fun validatePurchaseInput(displayProductsInfo:List<String>, storeProducts: Map<String, Product>): String {
        while (true) {
            try {
                val purchaseInput = userInteractionController.handlePurchaseInput(displayProductsInfo)
                PurchaseInputValidator(purchaseInput, storeProducts).validate()
                return purchaseInput
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
        }
    }

    private fun adaptPurchaseInfo(purchaseInput: String): List<PurchaseInfo> {
        return PurchaseInfoAdapter(purchaseInput).adaptPurchaseInfo()
    }

    private fun processPromotionAndStock(
        purchaseInfo: List<PurchaseInfo>,
        storeProducts: Map<String, Product>
    ): List<PurchaseInfo> {
        val updatedPurchaseInfo = purchaseController.checkPromotionPurchase(purchaseInfo, storeProducts)
        return purchaseController.checkPromotionStockSufficient(updatedPurchaseInfo, storeProducts)
    }

    private fun generateAndDisplayReceipt(finalPurchaseInfo: List<PurchaseInfo>, storeProducts: Map<String, Product>) {
        val receiptInfo = createReceiptInfo(finalPurchaseInfo, storeProducts)
        outputView.showReceipt(receiptInfo)
    }

    private fun createReceiptInfo(finalPurchaseInfo: List<PurchaseInfo>, storeProducts: Map<String, Product>): ReceiptInfo {
        val bonusProducts = purchaseController.checkBonusProducts(finalPurchaseInfo, storeProducts)
        val membership = applyMembershipDiscount()
        return ReceiptInfo(
            _items = finalPurchaseInfo,
            bonusItems = bonusProducts,
            membership = membership,
            storeProducts = storeProducts
        )
    }

    private fun applyMembershipDiscount(): Membership {
        val membership = Membership()
        membershipController.applyMembershipDiscount(membership)
        return membership
    }


    private fun updateProductQuantities(finalPurchaseInfo: List<PurchaseInfo>, storeProducts: Map<String, Product>) {
        finalPurchaseInfo.forEach { purchase ->
            storeProducts[purchase.name]?.reduceQuantity(purchase.quantity)
        }
    }
}