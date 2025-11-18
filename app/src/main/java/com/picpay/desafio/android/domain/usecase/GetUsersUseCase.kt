package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.model.UserDomainModel
import com.picpay.desafio.android.domain.repository.UserRepository

class GetUsersUseCase(private val userRepository: UserRepository) {

    suspend fun getUsers() : List<UserDomainModel> = userRepository.getUsers()
}
