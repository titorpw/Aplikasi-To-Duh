package com.l0123137.tesprojek.ui.screen.editEvent

import java.time.LocalDate

data class EditEventState(
    val eventName: String = "",
    val selectedCategory: String = "",
    val date: LocalDate? = null,
    val description: String = "",
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
