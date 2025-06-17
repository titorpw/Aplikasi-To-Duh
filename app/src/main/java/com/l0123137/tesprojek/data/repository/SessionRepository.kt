package com.l0123137.tesprojek.data.repository

import com.l0123137.tesprojek.data.dao.SessionDao
import com.l0123137.tesprojek.data.model.Session
import kotlinx.coroutines.flow.Flow

class SessionRepository (private val sessionDao: SessionDao) {

    fun getSession(): Flow<Session?> {
        return sessionDao.getSession()
    }

    suspend fun saveLoginSession(userId: Long) {
        val session = Session(loggedInUserId = userId)
        sessionDao.saveSession(session)
    }

    suspend fun clearLoginSession() {
        sessionDao.clearSession()
    }
}