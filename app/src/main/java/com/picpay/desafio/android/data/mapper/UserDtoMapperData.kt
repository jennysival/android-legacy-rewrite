package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.android.data.local.model.UserDbModel
import com.picpay.desafio.android.data.remote.model.User
import com.picpay.desafio.android.domain.model.UserDomainModel

class UserDtoMapperData {

    fun mapUserDbToUserDomain(users: List<UserDbModel>) : List<UserDomainModel> = users.map {
        UserDomainModel(
            img = it.img,
            name = it.name,
            id = it.id,
            username = it.username
        )
    }

    fun mapApiUsersListToUserDb(users: List<User>) : List<UserDbModel> = users.map {
        UserDbModel(
            img = it.img,
            name = it.name,
            id = it.id,
            username = it.username
        )
    }
}
