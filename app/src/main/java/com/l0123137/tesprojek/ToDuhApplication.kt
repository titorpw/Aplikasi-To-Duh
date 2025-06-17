package com.l0123137.tesprojek

import android.app.Application
import com.l0123137.tesprojek.data.database.AppDatabase
import com.l0123137.tesprojek.data.repository.EventRepository
import com.l0123137.tesprojek.data.repository.SessionRepository
import com.l0123137.tesprojek.data.repository.UserRepository

class ToDuhApplication : Application() {
    private val database by lazy { AppDatabase.getDatabase(this) }

    val userRepository by lazy { UserRepository(database.userDao()) }
    val sessionRepository by lazy { SessionRepository(database.sessionDao()) }
    val eventRepository by lazy { EventRepository(database.eventDao()) }
}