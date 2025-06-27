package com.l0123137.tesprojek.ui.screen.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l0123137.tesprojek.data.model.User
import com.l0123137.tesprojek.data.repository.UserRepository
import kotlinx.coroutines.launch
import com.l0123137.tesprojek.ui.screen.signup.SignUpState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.mindrot.jbcrypt.BCrypt
import java.time.ZoneId

class SignUpViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var uiState by mutableStateOf(SignUpState())
        private set

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage.asSharedFlow()

    fun onFirstNameChange(value: String) {
        uiState = uiState.copy(firstName = value, firstNameError = value.isBlank())
    }

    fun onLastNameChange(value: String) {
        uiState = uiState.copy(lastName = value, lastNameError = value.isBlank())
    }

    fun onUsernameChange(value: String) {
        uiState = uiState.copy(username = value, usernameError = value.isBlank())
    }

    fun onBornDateChanged(date: java.time.LocalDate) {
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

    fun onSubmit() {
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
            uiState = uiState.copy(
                bornDateError = uiState.bornDate == null,
                errorMessage = "Tolong isi field dengan benar."
            )
            onPasswordChange(uiState.password)
            onConfirmPasswordChange(uiState.confirmPassword)
            return
        }

        uiState = uiState.copy(errorMessage = null)

        viewModelScope.launch {
            try {
                val newUser = User(
                    firstName = uiState.firstName,
                    lastName = uiState.lastName,
                    username = uiState.username,
                    bornDate = uiState.bornDate!!.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    passwordHash = BCrypt.hashpw(uiState.password, BCrypt.gensalt())
                )

                userRepository.insertUser(newUser)

                // Mengirim sinyal sukses melalui Flow
                _snackbarMessage.emit("Registrasi berhasil!")

            } catch (e: Exception) {
                // Menampilkan error di UI melalui state
                uiState = uiState.copy(errorMessage = "Failed to save user data: ${e.message}")
            }
        }
    }
}
