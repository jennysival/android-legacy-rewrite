package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.remote.RetrofitService
import com.picpay.desafio.android.domain.mapper.UserDtoMapper
import com.picpay.desafio.android.domain.model.UserModel
import com.picpay.desafio.android.domain.repository.UserRepository

class UserRepositoryImpl(private val userMapper: UserDtoMapper) : UserRepository {

    private var cache: List<UserModel>? = null

    override suspend fun getUsers(): List<UserModel> {
        cache?.let { return it }

        val response = RetrofitService.apiService.getUsers()
        val mappedUsers = userMapper.mapUsersModelList(response)

        cache = mappedUsers

        return mappedUsers
    }
}
