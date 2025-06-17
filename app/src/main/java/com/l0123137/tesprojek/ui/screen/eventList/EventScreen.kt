package com.l0123137.tesprojek.ui.screen.eventList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.l0123137.tesprojek.R
import com.l0123137.tesprojek.ToDuhApplication
import com.l0123137.tesprojek.data.model.Event
import com.l0123137.tesprojek.ui.ViewModelFactory
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import com.l0123137.tesprojek.ui.screen.eventList.EventViewModel
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(navController: NavController) {
    val application = LocalContext.current.applicationContext as ToDuhApplication
    val viewModel: EventViewModel = viewModel(
        factory = ViewModelFactory(
            application.userRepository,
            application.sessionRepository,
            application.eventRepository
        )
    )
    val uiState by viewModel.uiState.collectAsState()

    val upcomingEvents = uiState.events.filter { !it.isComplete }
    val completedEvents = uiState.events.filter { it.isComplete }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Event List",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (upcomingEvents.isNotEmpty()) {
                    item {
                        Spacer(Modifier.height(32.dp))
                        SectionTitle("Upcoming")
                    }
                    items(upcomingEvents, key = { it.id }) { event ->
                        EventItem(
                            event = event,
                            onEdit = { navController.navigate("edit_event/${event.id}") },
                            onDelete = { viewModel.deleteEvent(event) },
                            onMarkAsDone = { viewModel.toggleEventCompletion(event) }
                        )
                    }
                }

                if (completedEvents.isNotEmpty()) {
                    item {
                        Spacer(Modifier.height(32.dp))
                        SectionTitle("Completed")
                    }
                    items(completedEvents, key = { it.id }) { event ->
                        CompletedEventItem(event = event)
                    }
                }
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val instant = Instant.ofEpochMilli(timestamp)
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy").withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

@Composable
fun SectionTitle(title: String) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun EventItem(
    event: Event,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onMarkAsDone: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = event.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_calendar),
                            contentDescription = "Date",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp).padding(end = 4.dp)
                        )
                        Text(
                            text = formatDate(event.date),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(onClick = onEdit, enabled = !event.isComplete) { // <-- Menggunakan isComplete
                        Icon(
                            painter = painterResource(id = R.drawable.icon_edit),
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(onClick = onDelete) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_delete),
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(onClick = onMarkAsDone) {
                        Icon(
                            painter = painterResource(
                                id = if (event.isComplete) R.drawable.icon_checkbox_checked
                                else R.drawable.icon_checkbox_unchecked
                            ),
                            contentDescription = if (event.isComplete) "Completed" else "Mark as Done",
                            tint = if (event.isComplete) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CompletedEventItem(event: Event) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(event.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(formatDate(event.date), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}