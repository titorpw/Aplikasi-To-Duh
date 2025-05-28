package com.l0123137.tesprojek.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.l0123137.tesprojek.data.UserPreferences
import com.l0123137.tesprojek.ui.screen.login.LoginScreen
import com.l0123137.tesprojek.ui.screen.signup.SignUpScreen

@Composable
fun AppNavigator(
    darkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)

    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController, userPreferences = userPreferences)
        }
        composable("signup") {
            SignUpScreen(navController = navController, userPreferences = userPreferences)
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