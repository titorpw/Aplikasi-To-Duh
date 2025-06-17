package com.l0123137.tesprojek.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.l0123137.tesprojek.data.model.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSession(session: Session)

    @Query("SELECT * FROM session WHERE id = 1")
    fun getSession(): Flow<Session?>

    @Query("DELETE FROM session")
    suspend fun clearSession()
}