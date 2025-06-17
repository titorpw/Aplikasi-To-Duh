package com.l0123137.tesprojek.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.l0123137.tesprojek.ToDuhApplication
import com.l0123137.tesprojek.ui.ViewModelFactory

@Composable
fun SearchScreen(
    navController: NavController
) {
    val application = LocalContext.current.applicationContext as ToDuhApplication

    val viewModel: SearchViewModel = viewModel(
        factory = ViewModelFactory(
            application.userRepository,
            application.sessionRepository,
            application.eventRepository
        )
    )

    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = uiState.query,
            onValueChange = { viewModel.onQueryChange(it) },
            label = { Text("Cari event...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(uiState.results, key = { it.id }) { event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            navController.navigate("edit_event/${event.id}")
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = event.name, style = MaterialTheme.typography.titleMedium)
                        event.description?.let {
                            Text(text = it, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            if (uiState.query.isNotBlank() && uiState.results.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Tidak ada event ditemukan.")
                    }
                }
            }
        }
    }
}
