package com.picpay.desafio.android.ui.user.viewModel

import androidx.lifecycle.Observer
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.testRulesCoroutinesLiveData
import com.picpay.desafio.android.ui.ViewState
import com.picpay.desafio.android.ui.user.mapper.UserDtoMapperUI
import com.picpay.desafio.android.ui.user.model.UserUIModel
import com.picpay.desafio.android.userDomainList
import com.picpay.desafio.android.userUIList
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserViewModelTest {

    private val useCaseMock = mockk<GetUsersUseCase>()
    private val mapperMock = mockk<UserDtoMapperUI>()
    private val subject = UserViewModel(
        useCase = useCaseMock,
        mapperUI = mapperMock
    )
    private val userListObserver = mockk<Observer<ViewState<List<UserUIModel>>>>()

    @get:Rule
    val rule = testRulesCoroutinesLiveData

    @Before
    fun setUp() {
        every { userListObserver.onChanged(any()) } just Runs
    }

    @After
    fun tearDown() {
        subject.userListState.removeObserver(userListObserver)
    }

    @Test
    fun `userListState should be Loading then Success when useCase returns a list`() {
        coEvery { useCaseMock.getUsers() } returns userDomainList
        coEvery { mapperMock.mapUserDomainToUserUI(userDomainList) } returns userUIList
        subject.userListState.observeForever(userListObserver)

        subject.getUsers()

        verifyOrder {
            userListObserver.onChanged(ViewState.Loading)
            userListObserver.onChanged(ViewState.Success(userUIList))
        }

    }

    @Test
    fun `userListState should be Loading then Error when useCase throws an exception`() {
        val exception = RuntimeException("Network Error")
        coEvery { useCaseMock.getUsers() } throws exception
        subject.userListState.observeForever(userListObserver)

        subject.getUsers()

        verifyOrder {
            userListObserver.onChanged(ViewState.Loading)
            userListObserver.onChanged(ViewState.Error(exception))
        }
    }
}
