package com.l0123137.tesprojek.ui.screen.eventList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l0123137.tesprojek.data.model.Event
import com.l0123137.tesprojek.data.model.User
import com.l0123137.tesprojek.data.repository.EventRepository
import com.l0123137.tesprojek.data.repository.SessionRepository
import com.l0123137.tesprojek.data.repository.UserRepository
import com.l0123137.tesprojek.ui.screen.eventList.EventListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalCoroutinesApi::class)
class EventViewModel(
    private val sessionRepository: SessionRepository,
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private data class CombinedData(
        val user: User?,
        val query: String,
        val category: String?,
        val dismissed: Boolean
    )

    // State untuk menampung query pencarian dari UI
    private val _searchQuery = MutableStateFlow("")

    // State untuk menampung kategori yang dipilih dari UI.
    private val _selectedCategory = MutableStateFlow<String?>(null)

    // Mendapatkan flow session user yang sedang login
    private val _session = sessionRepository.getSession()

    private val _birthdayCardDismissed = MutableStateFlow(false)

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage.asSharedFlow()

    // State Utama untuk UI (Daftar Event)
    val uiState: StateFlow<EventListState> = _session
        .flatMapLatest { session ->
            session?.loggedInUserId?.let { userId ->
                combine(
                    userRepository.getUserById(userId),
                    _searchQuery,
                    _selectedCategory,
                    _birthdayCardDismissed
                ) { user, query, category, dismissed ->
                    CombinedData(user, query, category, dismissed)
                }.flatMapLatest { combinedData ->
                    val (user, query, category, dismissed) = combinedData

                    eventRepository.getEvents(userId, query, category)
                        .map { events ->
                            val birthdayMessage = user?.let { u -> checkBirthday(u) }
                            val isVisible = birthdayMessage != null && !dismissed

                            EventListState(
                                events = events,
                                isLoading = false,
                                birthdayMessage = birthdayMessage,
                                isBirthdayCardVisible = isVisible
                            )
                        }
                }
            } ?: flowOf(EventListState(isLoading = false))
        }
        .catch { throwable ->
            emit(EventListState(isLoading = false, errorMessage = throwable.message))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = EventListState(isLoading = true)
        )

    val categories: StateFlow<List<String>> = _session.flatMapLatest { session ->
        session?.loggedInUserId?.let { userId ->
            eventRepository.getUniqueCategoriesForUser(userId)
        } ?: flowOf(emptyList())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun dismissBirthdayCard() {
        _birthdayCardDismissed.value = true
    }

    private fun checkBirthday(user: com.l0123137.tesprojek.data.model.User): String? {
        val userBirthday = Instant.ofEpochMilli(user.bornDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val today = LocalDate.now()

        return if (userBirthday.monthValue == today.monthValue && userBirthday.dayOfMonth == today.dayOfMonth) {
            "Selamat Ulang Tahun, ${user.firstName}!"
        } else {
            null
        }
    }


    // --- Fungsi yang dipanggil dari UI ---

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategoryFilter(category: String?) {
        _selectedCategory.value = category
    }

    fun toggleEventCompletion(event: Event) {
        viewModelScope.launch {
            val updatedEvent = event.copy(isComplete = !event.isComplete)
            eventRepository.updateEvent(updatedEvent)
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.deleteEvent(event)
            _snackbarMessage.emit("Event berhasil dihapus")
        }
    }
}