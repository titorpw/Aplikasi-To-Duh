package com.l0123137.tesprojek.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.l0123137.tesprojek.data.UserSession

class LoginViewModel : ViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var showError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun onLoginClick(onSuccess: () -> Unit) {
        when {
            username.isBlank() -> {
                showError = true
                errorMessage = "Username is required"
            }
            password.isBlank() -> {
                showError = true
                errorMessage = "Password is required"
            }
            username != UserSession.registeredUsername || password != UserSession.registeredPassword -> {
                showError = true
                errorMessage = "Username and Password don’t match"
            }
            else -> {
                showError = false
                onSuccess()
            }
        }
    }

}