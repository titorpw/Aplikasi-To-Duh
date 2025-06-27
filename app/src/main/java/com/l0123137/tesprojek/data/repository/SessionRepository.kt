package com.l0123137.tesprojek.data.repository

import com.l0123137.tesprojek.data.UserSessionManager
import com.l0123137.tesprojek.data.model.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionRepository (private val userSessionManager: UserSessionManager) {

    suspend fun saveSession(userId: Long) {
        userSessionManager.saveUserId(userId)
    }

    suspend fun clearSession() {
        userSessionManager.clearUserId()
    }

    fun getSession(): Flow<Session?> {
        return userSessionManager.loggedInUserIdFlow.map { userId ->
            if (userId != null) {
                Session(loggedInUserId = userId)
            } else {
                null
            }
        }
    }
}