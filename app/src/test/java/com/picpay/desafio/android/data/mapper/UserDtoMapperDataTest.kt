package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.android.userApiList
import com.picpay.desafio.android.userDbModelList
import com.picpay.desafio.android.userDomainList
import org.junit.Assert.*
import org.junit.Test

class UserDtoMapperDataTest {

    private val subject = UserDtoMapperData()

    @Test
    fun `when mapUserDbToUserDomain is called should return a UserDomainModel list `() {
        val result = subject.mapUserDbToUserDomain(userDbModelList)

        assertEquals(1, result.size)
        assertEquals(userDomainList, result)
    }

    @Test
    fun `when mapApiUsersListToUserDb is called should return a UserDbModel list `() {
        val result = subject.mapApiUsersListToUserDb(userApiList)

        assertEquals(1, result.size)
        assertEquals(userDbModelList, result)
    }
}
