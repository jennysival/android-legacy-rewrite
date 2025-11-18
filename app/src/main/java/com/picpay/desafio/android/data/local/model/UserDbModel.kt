package com.picpay.desafio.android.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserDbModel(
    var img: String,
    var name: String,
    @PrimaryKey
    var id: Int,
    var username: String
)
