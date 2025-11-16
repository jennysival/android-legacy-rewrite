package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.local.UserDbModel
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.remote.model.User
import com.picpay.desafio.android.domain.mapper.UserDtoMapper
import com.picpay.desafio.android.domain.model.UserModel
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
    private val mapperMock = mockk<UserDtoMapper>()
    private val daoMock = mockk<UserDao>()

    private val userDbModelList = listOf(UserDbModel("img", "name", 1, "username"))
    private val userModelList = listOf(UserModel("img", "name", 1, "username"))
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
            coEvery { mapperMock.mapCachedUsersModelList(cachedList) } returns userModelList

            val result = subject.getUsers()

            coVerify(exactly = 0) { apiMock.getUsers() }
            Assert.assertEquals(userModelList, result)
        }
    }

    @Test
    fun `when getUsers is called without cached data should get users from api and insert in database and map to UserModel`() {
        runTest {
            val apiUserList = userList
            coEvery { daoMock.getUsers() } returns emptyList()
            coEvery { apiMock.getUsers() } returns apiUserList
            coEvery { mapperMock.mapRemoteUsersListToDb(apiUserList) } returns userDbModelList
            coEvery { daoMock.insertUsers(userDbModelList) } just Runs
            coEvery { daoMock.getUsers() } returns userDbModelList
            coEvery { mapperMock.mapCachedUsersModelList(userDbModelList) } returns userModelList

            val result = subject.getUsers()

            Assert.assertEquals(userModelList, result)
        }
    }
}
