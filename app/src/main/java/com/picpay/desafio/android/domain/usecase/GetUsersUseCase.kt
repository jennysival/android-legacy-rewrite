package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.model.UserModel
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.ui.ViewState

class GetUsersUseCase(private val userRepository: UserRepository) {

    suspend fun getUsers() : ViewState<List<UserModel>> {
        return runCatching {
            userRepository.getUsers()
        }.fold(
            onSuccess = { users ->
                ViewState.Success(users)
            },
            onFailure = { error ->
                ViewState.Error(error)
            }
        )
    }
}
