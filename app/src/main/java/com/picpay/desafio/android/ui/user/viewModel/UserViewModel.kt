package com.picpay.desafio.android.ui.user.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.model.UserModel
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.ui.ViewState
import kotlinx.coroutines.launch

class UserViewModel(private val useCase: GetUsersUseCase) : ViewModel() {

    private val _userListState = MutableLiveData<ViewState<List<UserModel>>>()
    val userListState: LiveData<ViewState<List<UserModel>>> = _userListState

    fun getUsers() {
        viewModelScope.launch {
            _userListState.value = ViewState.Loading

            runCatching {
                useCase.getUsers()
            }.onSuccess { users ->
                _userListState.value = ViewState.Success(users)
            }.onFailure { error ->
                _userListState.value = ViewState.Error(error)
            }
        }
    }
}
