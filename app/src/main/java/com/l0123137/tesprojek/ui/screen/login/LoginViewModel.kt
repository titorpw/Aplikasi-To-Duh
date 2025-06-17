package com.l0123137.tesprojek.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l0123137.tesprojek.data.repository.UserRepository
import com.l0123137.tesprojek.data.repository.SessionRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

class LoginViewModel(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    var uiState by mutableStateOf(LoginState())
        private set

    fun onUsernameChange(username: String) {
        uiState = uiState.copy(username = username)
    }

    fun onPasswordChange(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun onLoginClick(onSuccess: () -> Unit) {
        uiState = uiState.copy(errorMessage = null)

        // Validasi input dasar
        if (uiState.username.isBlank() || uiState.password.isBlank()) {
            uiState = uiState.copy(errorMessage = "Username and password cannot be empty.")
            return
        }

        viewModelScope.launch{
            val user = userRepository.getUserByUsername(uiState.username).first()

            if (user == null){
                uiState = uiState.copy(errorMessage = "Incorrect username or password.")
            } else{
                if(BCrypt.checkpw(uiState.password, user.passwordHash)){
                    sessionRepository.saveLoginSession(user.id)
                    onSuccess()
                }else{
                    uiState = uiState.copy(errorMessage = "Incorrect username or password.")
                }
            }
        }
    }
}