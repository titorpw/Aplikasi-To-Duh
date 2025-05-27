package com.l0123137.tesprojek.ui.screen.createEvent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CreateEventViewModel : ViewModel() {
    var eventName by mutableStateOf("")
    var selectedCategory by mutableStateOf("")
    var date by mutableStateOf("")
    var description by mutableStateOf("")
    var showValidationError by mutableStateOf(false)

    val categoryList = listOf("Meeting", "Task", "Social", "Travelling")

    fun onSubmit() {
        if (eventName.isBlank() || selectedCategory.isBlank() || date.isBlank()) {
            showValidationError = true
        } else {
            println("Event created: $eventName, $selectedCategory, $date, $description")
            showValidationError = false
        }
    }
}