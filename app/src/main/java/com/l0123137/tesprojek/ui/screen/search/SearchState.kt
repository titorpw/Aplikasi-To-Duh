package com.l0123137.tesprojek.ui.screen.search

import com.l0123137.tesprojek.data.model.Event

data class SearchState(
    val query: String = "",
    val results: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
