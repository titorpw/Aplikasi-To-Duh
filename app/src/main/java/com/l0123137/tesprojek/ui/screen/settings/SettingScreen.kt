package com.l0123137.tesprojek.ui.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.l0123137.tesprojek.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.l0123137.tesprojek.ToDuhApplication
import com.l0123137.tesprojek.ui.ViewModelFactory

@Composable
fun SettingsScreen(
    navController: NavHostController,
    parentNavController: NavHostController,
    darkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {

    val application = LocalContext.current.applicationContext as ToDuhApplication
    val viewModel: SettingsViewModel = viewModel(
        factory = ViewModelFactory(
            userRepository = application.userRepository,
            sessionRepository = application.sessionRepository,
            eventRepository = application.eventRepository
        )
    )
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(46.dp))

        Text(
            text = "Settings",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Mode",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.width(10.dp))

            Switch(
                checked = darkMode,
                onCheckedChange = onToggleDarkMode,
                thumbContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_moon),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF1061b0),
                    checkedTrackColor = Color(0xFFB0BEC5),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFFB0BEC5)
                )
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                    // Menghapus session dari DataStore
                    viewModel.logout()

                    // Setelah sesi dihapus, baru navigasi ke halaman login
                    parentNavController.navigate("login") {
                    popUpTo(parentNavController.graph.startDestinationId) {
                        inclusive = true
                    }
                        launchSingleTop = true
                }
            }
                .padding(vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Logout, contentDescription = "Logout", tint = MaterialTheme.colorScheme.secondary)
            Spacer(Modifier.width(5.dp))
            Text(
                text = "Logout",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(Modifier.height(15.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Info, contentDescription = "About Us", tint = MaterialTheme.colorScheme.secondary)
            Spacer(Modifier.width(8.dp))
            Text(
                text = "About Us",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(Modifier.height(3.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)
        ) {
            Text(
                text = "To-Duh adalah aplikasi manajemen event yang membantu pengguna mengatur agenda secara terorganisir untuk meningkatkan produktivitas. Dengan antarmuka yang intuitif dan mudah diakses, aplikasi ini cocok digunakan dalam konteks personal maupun profesional.",
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 24.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
