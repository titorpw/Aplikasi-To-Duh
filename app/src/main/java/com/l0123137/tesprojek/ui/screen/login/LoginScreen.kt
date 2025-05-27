package com.l0123137.tesprojek.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.l0123137.tesprojek.R

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), navController: NavHostController) {
    val username = viewModel.username
    val password = viewModel.password
    val showError = viewModel.showError
    val errorMessage = viewModel.errorMessage

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo_to_duh),
                contentDescription = "Logo TO-DUH",
                modifier = Modifier
                    .height(60.dp)
                    .scale(3f)
                    .padding(10.dp, 4.dp, 10.dp, 0.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { viewModel.username = it },
                label = { Text("Username*") },
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            )

            if (showError && username.isBlank()) {
                Text("Username is required", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.password = it },
                label = { Text("Password*") },
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            if (showError && password.isBlank()) {
                Text("Password is required", color = Color.Red, fontSize = 12.sp)
            }

            if (showError && username.isNotBlank() && password.isNotBlank()) {
                Text(errorMessage, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.onLoginClick {
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1061b0)),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Log in")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = {
                navController.navigate("signup")
            }) {
                Text("Not on To-Duh yet? Sign up here!", fontSize = 12.sp, color = Color.Black)
            }
        }
    }
}
