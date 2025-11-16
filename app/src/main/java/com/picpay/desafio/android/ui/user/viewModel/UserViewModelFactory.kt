package com.picpay.desafio.android.ui.user.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase

class UserViewModelFactory(private val getUsersUseCase: GetUsersUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(useCase = getUsersUseCase) as T
    }
}