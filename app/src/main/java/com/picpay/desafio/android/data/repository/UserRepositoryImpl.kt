package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.domain.mapper.UserDtoMapper
import com.picpay.desafio.android.domain.model.UserModel
import com.picpay.desafio.android.domain.repository.UserRepository

class UserRepositoryImpl(
    private val apiService: PicPayService,
    private val userMapper: UserDtoMapper,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUsers(): List<UserModel> {
        return try {
            val cachedUsers = userDao.getUsers()
            if (cachedUsers.isNotEmpty()) {
                return userMapper.mapCachedUsersModelList(cachedUsers)
            }

            val remoteUsers = apiService.getUsers()
            val mappedUsersDb = userMapper.mapRemoteUsersListToDb(remoteUsers)

            userDao.insertUsers(mappedUsersDb)

            val mappedUsers = userMapper.mapCachedUsersModelList(userDao.getUsers())
            mappedUsers
        } catch (e: Exception) {
            throw e
        }
    }
}
