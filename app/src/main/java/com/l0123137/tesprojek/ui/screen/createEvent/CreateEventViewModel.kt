package com.l0123137.tesprojek.ui.screen.createEvent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l0123137.tesprojek.data.model.Event
import com.l0123137.tesprojek.data.repository.EventRepository
import com.l0123137.tesprojek.data.repository.SessionRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

class CreateEventViewModel(
    private val sessionRepository: SessionRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    var uiState by mutableStateOf(CreateEventState())
        private set

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage.asSharedFlow()

    val categoryList = listOf("Meeting", "Task", "Social", "Travelling")

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

    fun saveEvent() {
        //Validasi Input
        if (uiState.eventName.isBlank() || uiState.selectedCategory.isBlank() || uiState.date == null) {
            uiState = uiState.copy(errorMessage = "Field Tidak Boleh Kosong.")
            return
        }

        viewModelScope.launch {
            val loggedInUserId = sessionRepository.getSession().first()?.loggedInUserId

            if(loggedInUserId == null){
                uiState = uiState.copy(errorMessage = "Error: User Tidak Login.")
                return@launch
            }

            val newEvent = Event(
                name = uiState.eventName,
                category = uiState.selectedCategory,
                date = uiState.date!!.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                description = uiState.description,
                isComplete = false,
                userId = loggedInUserId
            )

            try{
                eventRepository.insertEvent(newEvent)
                _snackbarMessage.emit("Event berhasil dibuat!")
            } catch(e: Exception){
                uiState = uiState.copy(errorMessage = "Gagal untuk menyimpan event: ${e.message}")
            }
        }
    }
}
