package com.l0123137.tesprojek.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.l0123137.tesprojek.ui.screen.createEvent.EventViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    eventViewModel: EventViewModel
) {
    var query by remember { mutableStateOf("") }

    val filteredEvents = if (query.isNotBlank()) {
        eventViewModel.eventList.filter {
            it.name.contains(query, ignoreCase = true)
        }
    } else emptyList()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Cari event...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(filteredEvents) { event ->
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
                        Text(text = event.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            if (query.isNotBlank() && filteredEvents.isEmpty()) {
                item {
                    Text("Tidak ada event ditemukan.", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}
