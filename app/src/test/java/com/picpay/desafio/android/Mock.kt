package com.picpay.desafio.android

import com.picpay.desafio.android.data.local.model.UserDbModel
import com.picpay.desafio.android.data.remote.model.User
import com.picpay.desafio.android.domain.model.UserDomainModel
import com.picpay.desafio.android.ui.user.model.UserUIModel

private const val STRING_TEST = "test"
private const val INT_TEST = 1

val userApiList = listOf(
    User(
        img = STRING_TEST,
        name = STRING_TEST,
        id = INT_TEST,
        username = STRING_TEST
    )
)

val userDbModelList = listOf(
    UserDbModel(
        img = STRING_TEST,
        name = STRING_TEST,
        id = INT_TEST,
        username = STRING_TEST
    )
)

val userDomainList = listOf(
    UserDomainModel(
        img = STRING_TEST,
        name = STRING_TEST,
        id = INT_TEST,
        username = STRING_TEST
    )
)

val userUIList = listOf(
    UserUIModel(
        img = STRING_TEST,
        name = STRING_TEST,
        id = INT_TEST,
        username = STRING_TEST
    )
)
