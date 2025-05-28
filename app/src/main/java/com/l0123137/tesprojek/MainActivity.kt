package com.l0123137.tesprojek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.l0123137.tesprojek.navigation.AppNavigator
import com.l0123137.tesprojek.ui.theme.TesProjekTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var darkMode by rememberSaveable { mutableStateOf(false) }

            TesProjekTheme (darkTheme = darkMode) {
                AppNavigator(
                    darkMode = darkMode,
                    onToggleDarkMode = { darkMode = it }
                )
            }
        }
    }
}
