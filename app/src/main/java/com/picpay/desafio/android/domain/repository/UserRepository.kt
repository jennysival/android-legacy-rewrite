package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.domain.model.UserDomainModel

interface UserRepository {

    suspend fun getUsers(): List<UserDomainModel>
}
