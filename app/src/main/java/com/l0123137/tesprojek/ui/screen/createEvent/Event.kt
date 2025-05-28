package com.l0123137.tesprojek.ui.screen.createEvent

import java.util.UUID

data class Event(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val category: String,
    val date: String,
    val description: String,
    val isCompleted: Boolean = false
)
