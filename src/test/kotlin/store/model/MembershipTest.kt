package store.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MembershipTest {

    @Test
    fun `멤버십이 비활성 상태로 초기화되어야 함`() {
        val membership = Membership()
        assertFalse(membership.isMember)
    }

    @Test
    fun `멤버십을 활성화할 수 있어야 함`() {
        val membership = Membership()
        membership.activateMembership()
        assertTrue(membership.isMember)
    }

    @Test
    fun `일일 할인 한도가 8000으로 설정되어야 함`() {
        val membership = Membership()
        assertEquals(8000, membership.dailyLimit)
    }
}