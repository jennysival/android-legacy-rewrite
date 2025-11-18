package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.userDomainList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import kotlin.test.assertFailsWith

class GetUsersUseCaseTest {

    private val repoMock = mockk<UserRepositoryImpl>()
    private val subject = GetUsersUseCase(repoMock)

    @Test
    fun `when getUsers is called with successful result should return UserModel list`() {
        runTest {
            coEvery { repoMock.getUsers() } returns userDomainList
            val result = subject.getUsers()

            assertEquals(userDomainList, result)
        }
    }

    @Test
    fun `when getUsers is called with failure result should throw Exception`() {
        runTest {
            val expectedException = RuntimeException("Network Error")
            coEvery { repoMock.getUsers() } throws expectedException

            val actualException = assertFailsWith<RuntimeException> {
                subject.getUsers()
            }

            assertEquals(expectedException, actualException)
        }
    }
}
