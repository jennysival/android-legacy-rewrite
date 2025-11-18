package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.mapper.UserDtoMapperData
import com.picpay.desafio.android.domain.model.UserDomainModel
import com.picpay.desafio.android.domain.repository.UserRepository

class UserRepositoryImpl(
    private val apiService: PicPayService,
    private val userMapper: UserDtoMapperData,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUsers(): List<UserDomainModel> {
        val cachedUsers = userDao.getUsers()
        if (cachedUsers.isNotEmpty()) {
            return userMapper.mapUserDbToUserDomain(cachedUsers)
        }

        val remoteUsers = apiService.getUsers()
        val mappedUsersDb = userMapper.mapApiUsersListToUserDb(remoteUsers)

        userDao.insertUsers(mappedUsersDb)

        val mappedUsers = userMapper.mapUserDbToUserDomain(userDao.getUsers())
        return mappedUsers
    }
}
