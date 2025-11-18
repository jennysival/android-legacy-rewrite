package com.picpay.desafio.android.ui.user.mapper

import com.picpay.desafio.android.domain.model.UserDomainModel
import com.picpay.desafio.android.ui.user.model.UserUIModel

class UserDtoMapperUI {

    fun mapUserDomainToUserUI(users: List<UserDomainModel>) : List<UserUIModel> = users.map {
        UserUIModel(
            img = it.img,
            name = it.name,
            id = it.id,
            username = it.username
        )
    }
}
