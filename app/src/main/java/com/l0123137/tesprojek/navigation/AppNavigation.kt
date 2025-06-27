package com.l0123137.tesprojek.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.l0123137.tesprojek.ToDuhApplication
import com.l0123137.tesprojek.data.model.Session
import com.l0123137.tesprojek.ui.screen.login.LoginScreen
import com.l0123137.tesprojek.ui.screen.signup.SignUpScreen

// Objek unik untuk menandakan status awal sebelum data dari Flow diterima.
private object InitialValue

@Composable
fun AppNavigator(
    darkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    // Ambil SessionRepository dari Application context
    val application = LocalContext.current.applicationContext as ToDuhApplication
    val sessionRepository = application.sessionRepository

    // Kumpulkan status sesi dari DataStore.
    // Gunakan objek InitialValue sebagai penanda status loading.
    val sessionState by sessionRepository.getSession().collectAsState(initial = InitialValue)

    // Tampilkan UI berdasarkan status navigasi
    when (val state = sessionState) {
        is InitialValue -> {
            // Tampilkan layar loading HANYA jika nilainya masih InitialValue
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is Session -> {
            // Jika state adalah object Session, berarti user sudah login.
            AppNavHost(
                startDestination = "scaffold",
                darkMode = darkMode,
                onToggleDarkMode = onToggleDarkMode
            )
        }
        null -> {
            // Jika state adalah null, berarti proses selesai dan user belum login.
            AppNavHost(
                startDestination = "login",
                darkMode = darkMode,
                onToggleDarkMode = onToggleDarkMode
            )
        }
    }
}

/**
 * Memisahkan NavHost ke dalam composable sendiri agar lebih rapi.
 */
@Composable
private fun AppNavHost(
    startDestination: String,
    darkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = startDestination) {
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