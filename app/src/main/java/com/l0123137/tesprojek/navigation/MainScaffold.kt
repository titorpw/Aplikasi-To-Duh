package com.l0123137.tesprojek.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.l0123137.tesprojek.R
import com.l0123137.tesprojek.ui.screen.calendar.CalendarScreen
import com.l0123137.tesprojek.ui.screen.createEvent.CreateEventScreen
import com.l0123137.tesprojek.ui.screen.editEvent.EditEventScreen
import com.l0123137.tesprojek.ui.screen.eventList.EventScreen
import com.l0123137.tesprojek.ui.screen.search.SearchScreen
import com.l0123137.tesprojek.ui.screen.settings.SettingsScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    parentNavController: NavHostController,
    darkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    val internalNavController = rememberNavController()

    Scaffold(
        topBar = {
            Surface(
                shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp),
                shadowElevation = 4.dp,
                color = MaterialTheme.colorScheme.onBackground
            ) {
                TopAppBar(
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.logo_to_duh),
                            contentDescription = "Logo",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(50.dp)
                                .scale(4f)
                                .padding(25.dp, 8.dp, 0.dp, 0.dp),
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.height(80.dp)
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(internalNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = internalNavController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main") {
                EventScreen(navController = internalNavController)
            }
            composable("create_event") {
                CreateEventScreen(navController = internalNavController)
            }
            composable("CalendarScreen") {
                CalendarScreen(navController = internalNavController)
            }
            composable(
                route = "edit_event/{eventId}",
                arguments = listOf(navArgument("eventId") { type = NavType.LongType })
            ) {
                EditEventScreen(navController = internalNavController)
            }

            composable("settings") {
                SettingsScreen(
                    navController = internalNavController,
                    parentNavController = parentNavController,
                    darkMode = darkMode,
                    onToggleDarkMode = onToggleDarkMode
                )
            }

            composable("search") {
                SearchScreen(navController = internalNavController)
            }
        }
    }
}

