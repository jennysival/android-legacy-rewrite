package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.local.model.UserDbModel
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.remote.model.User
import com.picpay.desafio.android.data.mapper.UserDtoMapperData
import com.picpay.desafio.android.domain.model.UserDomainModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class UserRepositoryImplTest {

    private val apiMock = mockk<PicPayService>()
    private val mapperMock = mockk<UserDtoMapperData>()
    private val daoMock = mockk<UserDao>()

    private val userDbModelList = listOf(UserDbModel("img", "name", 1, "username"))
    private val userModelList = listOf(UserDomainModel("img", "name", 1, "username"))
    private val userList = listOf(User("img", "name", 1, "username"))

    private val subject = UserRepositoryImpl(
        apiService = apiMock,
        userMapper = mapperMock,
        userDao = daoMock
    )

    @Test
    fun `when getUsers is called with cached data should get users from database and map to UserModel`() {
        runTest {
            val cachedList = userDbModelList
            coEvery { daoMock.getUsers() } returns cachedList
            coEvery { mapperMock.mapUserDbToUserDomain(cachedList) } returns userModelList

            val result = subject.getUsers()

            coVerify(exactly = 0) { apiMock.getUsers() }
            Assert.assertEquals(userModelList, result)
        }
    }

    @Test
    fun `when getUsers is called without cached data should get users from api and insert in database and map to UserModel`() {
        runTest {
            val apiUserList = userList
            coEvery { daoMock.getUsers() } returnsMany listOf(emptyList(), userDbModelList)
            coEvery { apiMock.getUsers() } returns apiUserList
            coEvery { mapperMock.mapApiUsersListToUserDb(apiUserList) } returns userDbModelList
            coEvery { daoMock.insertUsers(userDbModelList) } just Runs
            coEvery { mapperMock.mapUserDbToUserDomain(userDbModelList) } returns userModelList

            val result = subject.getUsers()

            Assert.assertEquals(userModelList, result)
            coVerify(exactly = 2) { daoMock.getUsers() }
            coVerify(exactly = 1) { apiMock.getUsers() }
        }
    }
}
