package com.l0123137.tesprojek.ui.screen.calendar

import com.l0123137.tesprojek.data.model.Event
import java.time.LocalDate

data class CalendarState(
    val eventsByDate: Map<LocalDate, List<Event>> = emptyMap(),
    val selectedDate: LocalDate = LocalDate.now(),
    val eventsForSelectedDate: List<Event> = emptyList(),
    val isLoading: Boolean = true
)
