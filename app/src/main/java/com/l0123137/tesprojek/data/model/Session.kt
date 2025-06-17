package com.l0123137.tesprojek.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class Session(
    @PrimaryKey
    val id: Int = 1,
    val loggedInUserId: Long?
)
