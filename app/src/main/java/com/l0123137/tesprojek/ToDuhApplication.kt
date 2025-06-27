package com.l0123137.tesprojek

import android.app.Application
import com.l0123137.tesprojek.data.UserSessionManager
import com.l0123137.tesprojek.data.database.AppDatabase
import com.l0123137.tesprojek.data.repository.EventRepository
import com.l0123137.tesprojek.data.repository.SessionRepository
import com.l0123137.tesprojek.data.repository.UserRepository

class ToDuhApplication : Application() {
    private val database by lazy { AppDatabase.getDatabase(this) }

    private val userSessionManager by lazy {
        UserSessionManager(this)
    }

    val userRepository by lazy { UserRepository(database.userDao()) }
    val eventRepository by lazy { EventRepository(database.eventDao()) }

    val sessionRepository by lazy {
        SessionRepository(userSessionManager)
    }
}