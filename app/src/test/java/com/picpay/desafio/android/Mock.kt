package com.picpay.desafio.android

import com.picpay.desafio.android.data.local.UserDbModel
import com.picpay.desafio.android.data.remote.model.User
import com.picpay.desafio.android.domain.model.UserModel

private val stringTest = "test"
private val intTest = 1

val userApiList = listOf(
    User(
        img = stringTest,
        name = stringTest,
        id = intTest,
        username = stringTest
    )
)

val userDbModelList = listOf(
    UserDbModel(
        img = stringTest,
        name = stringTest,
        id = intTest,
        username = stringTest
    )
)

val userModelList = listOf(
    UserModel(
        img = stringTest,
        name = stringTest,
        id = intTest,
        username = stringTest
    )
)
