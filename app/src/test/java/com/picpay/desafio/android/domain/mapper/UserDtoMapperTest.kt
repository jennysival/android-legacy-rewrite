package com.picpay.desafio.android.domain.mapper

import com.picpay.desafio.android.data.local.UserDbModel
import com.picpay.desafio.android.data.remote.model.User
import com.picpay.desafio.android.domain.model.UserModel
import org.junit.Assert.*
import org.junit.Test

class UserDtoMapperTest {

    private val subject = UserDtoMapper()

    private val stringTest = "test"
    private val intTest = 1

    private val userApiList = listOf(
        User(
            img = stringTest,
            name = stringTest,
            id = intTest,
            username = stringTest
        )
    )

    private val userDbModelList = listOf(
        UserDbModel(
            img = stringTest,
            name = stringTest,
            id = intTest,
            username = stringTest
        )
    )

    private val userModelList = listOf(
        UserModel(
            img = stringTest,
            name = stringTest,
            id = intTest,
            username = stringTest
        )
    )

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