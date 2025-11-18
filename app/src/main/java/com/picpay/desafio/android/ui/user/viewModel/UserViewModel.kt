package com.picpay.desafio.android.ui.user.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.ui.ViewState
import com.picpay.desafio.android.ui.user.mapper.UserDtoMapperUI
import com.picpay.desafio.android.ui.user.model.UserUIModel
import kotlinx.coroutines.launch

class UserViewModel(
    private val useCase: GetUsersUseCase,
    private val mapperUI: UserDtoMapperUI
) : ViewModel() {

    private val _userListState = MutableLiveData<ViewState<List<UserUIModel>>>()
    val userListState: LiveData<ViewState<List<UserUIModel>>> = _userListState

    fun getUsers() {
        viewModelScope.launch {
            _userListState.value = ViewState.Loading
            runCatching {
                useCase.getUsers()
            }.onSuccess { users ->
                val uiUsers = mapperUI.mapUserDomainToUserUI(users)
                _userListState.value = ViewState.Success(uiUsers)
            }.onFailure { error ->
                _userListState.value = ViewState.Error(error)
            }
        }
    }
}
