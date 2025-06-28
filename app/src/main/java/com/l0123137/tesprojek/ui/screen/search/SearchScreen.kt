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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onQueryChange(it) },
            label = { Text("Cari event...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${uiState.errorMessage}")
            }
        } else {
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
                            Text(
                                text = "Kategori: ${event.category}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                            Text(
                                text = sdf.format(Date(event.date)),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                if (searchQuery.isNotBlank() && uiState.results.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Tidak ada event ditemukan untuk '$searchQuery'.")
                        }
                    }
                }
            }
        }
    }
}
