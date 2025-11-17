package com.picpay.desafio.android.domain.mapper

import com.picpay.desafio.android.userApiList
import com.picpay.desafio.android.userDbModelList
import com.picpay.desafio.android.userModelList
import org.junit.Assert.*
import org.junit.Test

class UserDtoMapperTest {

    private val subject = UserDtoMapper()

    @Test
    fun `when mapCachedUsersModelList is called should return a UserModel list `() {
        val result = subject.mapCachedUsersModelList(userDbModelList)

        assertEquals(1, result.size)
        assertEquals(userModelList, result)
    }

    @Test
    fun `when mapRemoteUsersListToDb is called should return a UserDbModel list `() {
        val result = subject.mapRemoteUsersListToDb(userApiList)

        assertEquals(1, result.size)
        assertEquals(userDbModelList, result)
    }
}