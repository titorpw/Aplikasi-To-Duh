package com.l0123137.tesprojek.ui.screen.createEvent

import java.time.LocalDate

data class CreateEventState(
    val eventName: String = "",
    val selectedCategory: String = "",
    val date: LocalDate? = null,
    val description: String = "",
    val errorMessage: String? = null,
    val isEventSaved: Boolean = false
)
