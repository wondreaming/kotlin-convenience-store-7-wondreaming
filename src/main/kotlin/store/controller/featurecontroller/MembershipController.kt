package store.controller.featurecontroller

import store.controller.validator.userInput.UserInputValidator
import store.model.Membership

class MembershipController(
    private val userInteractionController: UserInteractionController,
    private val userInputValidator: UserInputValidator,
) {
    fun applyMembershipDiscount(membership: Membership): Membership {
        val userResponse = userInteractionController.handleMembershipDiscount()
        userResponse.let {
            userInputValidator.validateUserInput(it)
            if (it == YES) membership.activateMembership()
        }
        return membership
    }

    companion object {
        private const val YES = "Y"
    }
}