package com.l0123137.tesprojek.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l0123137.tesprojek.data.repository.EventRepository
import com.l0123137.tesprojek.data.repository.SessionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModel(
    private val sessionRepository: SessionRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val uiState: StateFlow<SearchState> = _searchQuery
        .debounce(300)
        .combine(sessionRepository.getSession()) { query, session ->
            Pair(query, session)
        }
        .flatMapLatest { (query, session) ->
            if (query.isBlank()) {
                flowOf(SearchState(query = query, results = emptyList()))
            } else {
                val userId = session?.loggedInUserId
                if (userId != null) {
                    eventRepository.searchEventsForUser(query, userId)
                        .map { results -> SearchState(query = query, results = results) }
                } else {
                    flowOf(SearchState(query = query, results = emptyList()))
                }
            }
        }
        .catch { throwable ->
            emit(SearchState(errorMessage = throwable.message))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchState()
        )

    /**
     * Fungsi yang dipanggil oleh UI setiap kali teks pencarian berubah.
     */
    fun onQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}