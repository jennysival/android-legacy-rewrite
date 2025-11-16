package com.picpay.desafio.android.domain.mapper

import com.picpay.desafio.android.data.remote.model.User
import com.picpay.desafio.android.domain.model.UserModel

class UserDtoMapper {

    fun mapUsersModelList(users: List<User>) : List<UserModel> = users.map {
        UserModel(
            img = it.img,
            name = it.name,
            id = it.id,
            username = it.username
        )
    }
}
