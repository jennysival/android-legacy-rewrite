package com.picpay.desafio.android.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val img: String,
    val name: String,
    val id: Int,
    val username: String
) : Parcelable
