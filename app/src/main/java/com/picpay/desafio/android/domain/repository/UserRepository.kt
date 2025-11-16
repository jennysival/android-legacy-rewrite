package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.domain.model.UserModel

interface UserRepository {

    suspend fun getUsers(): List<UserModel>
}
