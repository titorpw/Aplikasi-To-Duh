package com.l0123137.tesprojek.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l0123137.tesprojek.data.repository.SessionRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val sessionRepository: SessionRepository
) : ViewModel() {


    //Menjalankan fungsi untuk menghapus sesi login dari DataStore.
    fun logout() {
        viewModelScope.launch {
            sessionRepository.clearSession()
        }
    }
}