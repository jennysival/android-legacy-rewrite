package com.picpay.desafio.android.ui.user.mapper

import com.picpay.desafio.android.userDomainList
import com.picpay.desafio.android.userUIList
import org.junit.Assert.*
import org.junit.Test

class UserDtoMapperUITest {

    private val subject = UserDtoMapperUI()

    @Test
    fun `when mapUserDomainToUserUI is called should return a UserUiModel list `() {
        val result = subject.mapUserDomainToUserUI(userDomainList)

        assertEquals(1, result.size)
        assertEquals(userUIList, result)
    }
}
