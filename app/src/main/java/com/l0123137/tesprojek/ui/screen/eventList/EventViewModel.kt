package com.l0123137.tesprojek.ui.screen.eventList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l0123137.tesprojek.data.model.Event
import com.l0123137.tesprojek.data.repository.EventRepository
import com.l0123137.tesprojek.data.repository.SessionRepository
import com.l0123137.tesprojek.ui.screen.EventListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class EventViewModel(
    private val sessionRepository: SessionRepository,
    private val eventRepository: EventRepository
) : ViewModel() {
    val uiState: StateFlow<EventListState> = sessionRepository.getSession()
        .flatMapLatest { session ->
            if (session?.loggedInUserId != null) {
                eventRepository.getEventsForUser(session.loggedInUserId)
                    .map { events -> EventListState(events = events, isLoading = false) }
            } else {
                flowOf(EventListState(events = emptyList(), isLoading = false))
            }
        }
        .catch { throwable ->
            emit(EventListState(isLoading = false, errorMessage = throwable.message))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = EventListState(isLoading = true)
        )

    fun toggleEventCompletion(event: Event) {
        viewModelScope.launch {
            val updatedEvent = event.copy(isComplete = !event.isComplete)
            eventRepository.updateEvent(updatedEvent)
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.deleteEvent(event)
        }
    }
}