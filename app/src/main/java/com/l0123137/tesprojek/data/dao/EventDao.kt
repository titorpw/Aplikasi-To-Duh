package com.l0123137.tesprojek.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.l0123137.tesprojek.data.model.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(event: Event): Long

    @Update
    suspend fun updateEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("SELECT * FROM events WHERE user_id = :userId ORDER BY date ASC")
    fun getEventsForUser(userId: Long): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE is_complete = :isComplete")
    fun getEventsByCompletionStatus(isComplete: Boolean): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE category = :category AND is_complete = 0 ORDER BY date ASC")
    fun getEventsByCategory(category: String): Flow<List<Event>>

    @Query("""
    SELECT * FROM events 
    WHERE user_id = :userId AND name LIKE '%' || :searchQuery || '%' COLLATE NOCASE 
    ORDER BY date ASC
""")
    fun searchEventsForUser(searchQuery: String, userId: Long): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE id = :eventId")
    fun getEventById(eventId: Long): Flow<Event?>
}