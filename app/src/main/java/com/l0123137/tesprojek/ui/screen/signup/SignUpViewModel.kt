package com.l0123137.tesprojek.ui.screen.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class SignUpViewModel : ViewModel() {

    var uiState by mutableStateOf(SignUpState())
        private set

    fun onFirstNameChange(value: String) {
        uiState = uiState.copy(firstName = value, firstNameError = value.isBlank())
    }

    fun onLastNameChange(value: String) {
        uiState = uiState.copy(lastName = value, lastNameError = value.isBlank())
    }

    fun onUsernameChange(value: String) {
        uiState = uiState.copy(username = value, usernameError = value.isBlank())
    }

    fun onBornDateChanged(date: LocalDate) {
        uiState = uiState.copy(bornDate = date, bornDateError = false)
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value, passwordError = value.isBlank())
    }

    fun onConfirmPasswordChange(value: String) {
        uiState = uiState.copy(
            confirmPassword = value,
            confirmPasswordError = value != uiState.password
        )
    }

    fun onSubmit(): Boolean {
        val isValid = listOf(
            uiState.firstName.isNotBlank(),
            uiState.lastName.isNotBlank(),
            uiState.username.isNotBlank(),
            uiState.bornDate != null,
            uiState.password.isNotBlank(),
            uiState.password == uiState.confirmPassword
        ).all { it }

        if (!isValid) {
            onFirstNameChange(uiState.firstName)
            onLastNameChange(uiState.lastName)
            onUsernameChange(uiState.username)
            uiState = uiState.copy(bornDateError = uiState.bornDate == null)
            onPasswordChange(uiState.password)
            onConfirmPasswordChange(uiState.confirmPassword)
        }

        return isValid
    }
}
