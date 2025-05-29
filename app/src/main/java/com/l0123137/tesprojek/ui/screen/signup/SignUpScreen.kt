package com.l0123137.tesprojek.ui.screen.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.l0123137.tesprojek.R
import com.l0123137.tesprojek.data.UserPreferences

@Composable
fun SignUpScreen(
    navController: NavController,
    userPreferences: UserPreferences
) {
    val viewModel = remember { SignUpViewModel(userPreferences) }
    val state = viewModel.uiState

    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_to_duh),
            contentDescription = "Logo",
            modifier = Modifier
                .height(100.dp)
                .scale(2f)
                .padding(10.dp, 4.dp, 10.dp, 0.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CustomTextField(
                label = "First Name",
                value = state.firstName,
                onValueChange = viewModel::onFirstNameChange,
                error = state.firstNameError,
                modifier = Modifier.weight(1f)
            )
            CustomTextField(
                label = "Last Name",
                value = state.lastName,
                onValueChange = viewModel::onLastNameChange,
                error = state.lastNameError,
                modifier = Modifier.weight(1f)
            )
        }

        CustomTextField(
            label = "Username",
            value = state.username,
            onValueChange = viewModel::onUsernameChange,
            error = state.usernameError,
            modifier = Modifier.fillMaxWidth()
        )

        CustomDatePicker(
            selectedDate = state.bornDate,
            onDateSelected = viewModel::onBornDateChanged,
            showError = state.bornDateError,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomPasswordField(
                label = "Password",
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                error = state.passwordError,
                modifier = Modifier.weight(1f)
            )
            CustomPasswordField(
                label = "Verify Password",
                value = state.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                error = state.confirmPasswordError,
                modifier = Modifier.weight(1f)
            )
        }

        if (listOf(
                state.firstNameError,
                state.lastNameError,
                state.usernameError,
                state.bornDateError,
                state.passwordError,
                state.confirmPasswordError
            ).any { it }) {
            Text(
                text = "*This field is required",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.Start)
            )
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }

        Button(
            onClick = {
                viewModel.onSubmit(
                    onSuccess = {
                        errorMessage = ""
                        navController.popBackStack()
                    },
                    onError = { msg ->
                        errorMessage = msg
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Text(text = "Sign up", color = MaterialTheme.colorScheme.onPrimaryContainer)
        }

        TextButton(onClick = { navController.navigate("login") }) {
            Text(
                text = "Already have an account? Login here!",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
