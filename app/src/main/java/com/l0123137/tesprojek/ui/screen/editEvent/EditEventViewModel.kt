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

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage.asSharedFlow()

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
                    uiState = uiState.copy(isLoading = false, errorMessage = "Event Tidak Ditemukan.")
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
            uiState = uiState.copy(errorMessage = "Field Tidak Boleh Kosong.")
            return
        }

        viewModelScope.launch {
            val originalEvent = eventRepository.getEventById(eventId).first()
            if (originalEvent == null) {
                uiState = uiState.copy(errorMessage = "Error: Maaf Event Tidak Ditemukan.")
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
                _snackbarMessage.emit("Event berhasil diupdate!")
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = "Gagal Update Eventt: ${e.message}")
            }
        }
    }
}