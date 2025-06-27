package com.l0123137.tesprojek.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.l0123137.tesprojek.data.repository.EventRepository
import com.l0123137.tesprojek.data.repository.SessionRepository
import com.l0123137.tesprojek.data.repository.UserRepository
import com.l0123137.tesprojek.ui.screen.calendar.CalendarViewModel
import com.l0123137.tesprojek.ui.screen.createEvent.CreateEventViewModel
import com.l0123137.tesprojek.ui.screen.editEvent.EditEventViewModel
import com.l0123137.tesprojek.ui.screen.eventList.EventViewModel
import com.l0123137.tesprojek.ui.screen.login.LoginViewModel
import com.l0123137.tesprojek.ui.screen.search.SearchViewModel
import com.l0123137.tesprojek.ui.screen.settings.SettingsViewModel
import com.l0123137.tesprojek.ui.screen.signup.SignUpViewModel

class ViewModelFactory (
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    private val eventRepository: EventRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return when {
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository, sessionRepository) as T
            }
            modelClass.isAssignableFrom(CreateEventViewModel::class.java) -> {
                CreateEventViewModel(sessionRepository, eventRepository) as T
            }
            modelClass.isAssignableFrom(EventViewModel::class.java) -> {
                EventViewModel(sessionRepository, eventRepository, userRepository) as T
            }
            modelClass.isAssignableFrom(EditEventViewModel::class.java) -> {
                val savedStateHandle = extras.createSavedStateHandle()
                EditEventViewModel(savedStateHandle, eventRepository) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(sessionRepository, eventRepository) as T
            }
            modelClass.isAssignableFrom(CalendarViewModel::class.java) -> {
                CalendarViewModel(sessionRepository, eventRepository) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(sessionRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}