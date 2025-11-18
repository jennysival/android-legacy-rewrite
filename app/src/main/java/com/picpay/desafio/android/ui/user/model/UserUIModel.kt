package com.picpay.desafio.android.ui.user.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserUIModel(
    val img: String,
    val name: String,
    val id: Int,
    val username: String
) : Parcelable
