package com.l0123137.tesprojek.ui.screen.editEvent

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l0123137.tesprojek.data.model.Event
import com.l0123137.tesprojek.data.repository.EventRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class EditEventViewModel(
    savedStateHandle: SavedStateHandle,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val eventId: Long = checkNotNull(savedStateHandle["eventId"])

    var uiState by mutableStateOf(EditEventState())
        private set

    val categoryList = listOf("Meeting", "Task", "Social", "Travelling")

    init {
        viewModelScope.launch {
            eventRepository.getEventById(eventId).collect { event ->
                if (event != null) {
                    uiState = uiState.copy(
                        eventName = event.name,
                        selectedCategory = event.category,
                        date = Instant.ofEpochMilli(event.date).atZone(ZoneId.systemDefault()).toLocalDate(),
                        description = event.description ?: "",
                        isLoading = false
                    )
                } else {
                    uiState = uiState.copy(isLoading = false, errorMessage = "Event not found.")
                }
            }
        }
    }

    fun updateEventName(newName: String) {
        uiState = uiState.copy(eventName = newName)
    }
    fun updateCategory(newCategory: String) {
        uiState = uiState.copy(selectedCategory = newCategory)
    }
    fun updateDate(newDate: LocalDate) {
        uiState = uiState.copy(date = newDate)
    }
    fun updateDescription(newDescription: String) {
        uiState = uiState.copy(description = newDescription)
    }

    fun updateEvent() {
        if (uiState.eventName.isBlank() || uiState.selectedCategory.isBlank() || uiState.date == null) {
            uiState = uiState.copy(errorMessage = "Please fill all required fields.")
            return
        }

        viewModelScope.launch {
            val originalEvent = eventRepository.getEventById(eventId).first()
            if (originalEvent == null) {
                uiState = uiState.copy(errorMessage = "Error: Original event not found.")
                return@launch
            }

            val updatedEvent = Event(
                id = eventId,
                name = uiState.eventName,
                category = uiState.selectedCategory,
                date = uiState.date!!.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                description = uiState.description,
                isComplete = originalEvent.isComplete,
                userId = originalEvent.userId
            )

            try {
                eventRepository.updateEvent(updatedEvent)
                uiState = uiState.copy(isEventUpdated = true)
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = "Failed to update event: ${e.message}")
            }
        }
    }
}