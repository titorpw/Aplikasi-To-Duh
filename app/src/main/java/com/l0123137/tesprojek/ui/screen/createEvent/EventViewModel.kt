package com.l0123137.tesprojek.ui.screen.createEvent

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class EventViewModel : ViewModel() {
    private val _eventList = mutableStateListOf<Event>()
    val eventList: List<Event> get() = _eventList

    fun addEvent(event: Event) {
        _eventList.add(event)
    }

    fun toggleCompletedById(eventId: String) {
        val index = _eventList.indexOfFirst { it.id == eventId }
        if (index != -1) {
            val currentEvent = _eventList[index]
            _eventList[index] = currentEvent.copy(isCompleted = !currentEvent.isCompleted)
        }
    }

    fun deleteEventById(eventId: String) {
        _eventList.removeAll { it.id == eventId }
    }

    fun getUpcomingEvents(): List<Event> {
        return _eventList.filter { !it.isCompleted }
    }

    fun getCompletedEvents(): List<Event> {
        return _eventList.filter { it.isCompleted }
    }

    fun updateEvent(updatedEvent: Event) {
        val index = _eventList.indexOfFirst { it.id == updatedEvent.id }
        if (index != -1) {
            _eventList[index] = updatedEvent
        }
    }
}
