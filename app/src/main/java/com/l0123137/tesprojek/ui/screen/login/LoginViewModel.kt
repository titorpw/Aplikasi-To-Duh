package com.l0123137.tesprojek.ui.screen.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l0123137.tesprojek.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var showError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun onLoginClick(onSuccess: () -> Unit) {

        showError = false
        errorMessage = ""

        if (username.isBlank()) {
            showError = true
            errorMessage = "Username is required"
            return
        }

        if (password.isBlank()) {
            showError = true
            errorMessage = "Password is required"
            return
        }

        viewModelScope.launch {
            val savedUsername = userPreferences.usernameFlow.first() ?: ""
            val savedPassword = userPreferences.passwordFlow.first() ?: ""

            if (username != savedUsername || password != savedPassword) {
                showError = true
                errorMessage = "Username and Password don’t match"
            } else {
                showError = false
                onSuccess()
            }
        }
    }
}