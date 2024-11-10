package store.model

data class Membership(
    private var _isMember: Boolean = false,
    val dailyLimit: Int = 8000
) {
    val isMember: Boolean
        get() = _isMember

    fun activateMembership() {
        _isMember = true
    }
}