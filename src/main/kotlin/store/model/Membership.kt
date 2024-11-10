package store.model

data class Membership(
    private var _isMember: Boolean = false,
    private var _dailyLimitUsed: Int = 0,
    val dailyLimit: Int = 8000
) {
    val isMember: Boolean
        get() = _isMember

    fun activateMembership() {
        _isMember = true
    }
}