package com.l0123137.tesprojek.ui.screen.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l0123137.tesprojek.data.repository.EventRepository
import com.l0123137.tesprojek.data.repository.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class CalendarViewModel(
    private val sessionRepository: SessionRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalendarState())
    val uiState: StateFlow<CalendarState> = _uiState.asStateFlow()

    init {
        loadUserEvents()
    }

    private fun loadUserEvents() {
        viewModelScope.launch {
            val userId = sessionRepository.getSession().first()?.loggedInUserId
            if (userId != null) {
                eventRepository.getEventsForUser(userId).collect { events ->
                    val eventsByDate = events.groupBy {
                        Instant.ofEpochMilli(it.date).atZone(ZoneId.systemDefault()).toLocalDate()
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            eventsByDate = eventsByDate,
                            isLoading = false,
                            eventsForSelectedDate = eventsByDate[currentState.selectedDate] ?: emptyList()
                        )
                    }
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedDate = date,
                eventsForSelectedDate = currentState.eventsByDate[date] ?: emptyList()
            )
        }
    }
}