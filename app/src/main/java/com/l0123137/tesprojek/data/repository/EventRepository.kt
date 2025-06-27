package com.l0123137.tesprojek.data.repository

import com.l0123137.tesprojek.data.dao.EventDao
import com.l0123137.tesprojek.data.model.Event
import kotlinx.coroutines.flow.Flow

class EventRepository (private val eventDao: EventDao) {

    suspend fun insertEvent(event: Event) {
        eventDao.insertEvent(event)
    }

    suspend fun updateEvent(event: Event) {
        eventDao.updateEvent(event)
    }

    suspend fun deleteEvent(event: Event) {
        eventDao.deleteEvent(event)
    }

    fun getEventsForUser(userId: Long): Flow<List<Event>> {
        return eventDao.getEventsForUser(userId)
    }

    fun searchEventsForUser(searchQuery: String, userId: Long): Flow<List<Event>> {
        return eventDao.searchEventsForUser(searchQuery, userId)
    }

    fun getEventsByCategory(category: String): Flow<List<Event>> {
        return eventDao.getEventsByCategory(category)
    }

    fun getEventById(eventId: Long): Flow<Event?> {
        return eventDao.getEventById(eventId)
    }

    fun getUniqueCategoriesForUser(userId: Long): Flow<List<String>> {
        return eventDao.getUniqueCategoriesForUser(userId)
    }

    fun getEvents(userId: Long, searchQuery: String, category: String?): Flow<List<Event>> {
        return eventDao.searchAndFilterEventsForUser(userId, searchQuery, category)
    }
}