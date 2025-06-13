package com.l0123137.tesprojek.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "born_date") val bornDate: Long,
    @ColumnInfo(name = "password") val passwordHash: String
)
