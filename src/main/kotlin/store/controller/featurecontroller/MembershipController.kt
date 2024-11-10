package store.controller.featurecontroller

import store.controller.validator.userInput.UserInputValidator
import store.model.Membership

class MembershipController(
    private val userInteractionController: UserInteractionController,
    private val userInputValidator: UserInputValidator,
) {
    fun applyMembershipDiscount(membership: Membership): Membership {
        while (true) {
            val userResponse = getUserResponse()
            if (isValidUserInput(userResponse)) {
                activateMembershipIfYes(userResponse, membership)
                return membership
            }
        }
    }

    private fun getUserResponse(): String {
        return try {
            userInteractionController.handleMembershipDiscount()
        } catch (e: IllegalArgumentException) {
            println(e.message)
            ""
        }
    }

    private fun isValidUserInput(userResponse: String): Boolean {
        return try {
            userInputValidator.validateUserInput(userResponse)
            true
        } catch (e: IllegalArgumentException) {
            println(e.message)
            false
        }
    }

    private fun activateMembershipIfYes(userResponse: String, membership: Membership) {
        if (userResponse == YES) membership.activateMembership()
    }

    companion object {
        private const val YES = "Y"
    }
}