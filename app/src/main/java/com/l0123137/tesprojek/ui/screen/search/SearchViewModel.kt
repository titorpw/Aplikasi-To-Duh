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
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class)
    val uiState: StateFlow<SearchState> = sessionRepository.getSession()
        .combine(_searchQuery.debounce(300)) { session, query ->
            Pair(session, query)
        }
        .flatMapLatest { (session, query) ->
            if (query.isBlank()) {
                return@flatMapLatest flowOf(SearchState(query = query, results = emptyList()))
            }

            val userId = session?.loggedInUserId
            if (userId == null) {
                return@flatMapLatest flowOf(SearchState(query = query, results = emptyList()))
            }

            // Jika ada query dan user, baru lakukan pencarian
            eventRepository.searchEventsForUser(query, userId)
                .map { results -> SearchState(query = query, results = results, isLoading = false) }
        }
        .onStart { emit(SearchState(isLoading = true)) }
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