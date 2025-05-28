package com.l0123137.tesprojek.ui.screen.createEvent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CreateEventViewModel : ViewModel() {
    var eventName by mutableStateOf("")
        private set

    var selectedCategory by mutableStateOf("")
        private set

    var date by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var showValidationError by mutableStateOf(false)
        private set

    val categoryList = listOf("Meeting", "Task", "Social", "Travelling")

    // Update functions for each input field
    fun updateEventName(newName: String) {
        eventName = newName
    }

    fun updateCategory(newCategory: String) {
        selectedCategory = newCategory
    }

    fun updateDate(newDate: String) {
        date = newDate
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun validateInput(): Boolean {
        showValidationError = eventName.isBlank() || selectedCategory.isBlank() || date.isBlank()
        return !showValidationError
    }

    fun resetForm() {
        eventName = ""
        selectedCategory = ""
        date = ""
        description = ""
        showValidationError = false
    }

    fun onSubmit(eventViewModel: EventViewModel) {
        if (validateInput()) {
            val newEvent = Event(
                name = eventName,
                category = selectedCategory,
                date = date,
                description = description
            )
            eventViewModel.addEvent(newEvent)
            resetForm()
        }
    }
}
