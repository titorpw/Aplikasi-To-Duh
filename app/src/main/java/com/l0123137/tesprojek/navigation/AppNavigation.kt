package com.l0123137.tesprojek.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.l0123137.tesprojek.ui.screen.login.LoginScreen
import com.l0123137.tesprojek.ui.screen.signup.SignUpScreen

@Composable
fun AppNavigator(
    darkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("signup") {
            SignUpScreen(navController = navController)
        }
        composable("scaffold") {
            MainScaffold(
                parentNavController = navController,
                darkMode = darkMode,
                onToggleDarkMode = onToggleDarkMode
            )
        }
    }
}