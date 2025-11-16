package com.picpay.desafio.android.domain.mapper

import com.picpay.desafio.android.data.local.UserDbModel
import com.picpay.desafio.android.data.remote.model.User
import com.picpay.desafio.android.domain.model.UserModel

class UserDtoMapper {

    fun mapCachedUsersModelList(users: List<UserDbModel>) : List<UserModel> = users.map {
        UserModel(
            img = it.img,
            name = it.name,
            id = it.id,
            username = it.username
        )
    }

    fun mapRemoteUsersListToDb(users: List<User>) : List<UserDbModel> = users.map {
        UserDbModel(
            img = it.img,
            name = it.name,
            id = it.id,
            username = it.username
        )
    }
}
