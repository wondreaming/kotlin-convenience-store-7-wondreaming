package store.model

data class Membership (
    private var _isMember: Boolean = false,
    private var _dailyLimitUsed: Int = 0,
    val dailyLimit: Int = 8000
){
    val isMember: Boolean
        get() = _isMember

    val dailyLimitUsed: Int
        get() = _dailyLimitUsed

    fun activateMembership() {
        _isMember = true
    }

    fun canUse(amount: Int): Boolean = (_dailyLimitUsed + amount <= dailyLimit)

    fun addUsage(amount: Int) {
        if (canUse(amount)) {
            _dailyLimitUsed += amount
        } else {
            throw IllegalArgumentException("일일 한도 초과: 남은 한도는 ${dailyLimit - _dailyLimitUsed}원입니다.")
        }
    }

    fun resetDailyLimit() {
        _dailyLimitUsed = 0
    }
}