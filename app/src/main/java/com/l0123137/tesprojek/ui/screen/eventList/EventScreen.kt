package com.l0123137.tesprojek.ui.screen.eventList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.l0123137.tesprojek.ui.screen.createEvent.EventViewModel
import com.l0123137.tesprojek.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(parentNavController: NavController, viewModel: EventViewModel) {
    val upcomingEvents = viewModel.getUpcomingEvents()
    val completedEvents = viewModel.getCompletedEvents()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "Event List",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (upcomingEvents.isNotEmpty()) {
                item {
                    SectionTitle("Upcoming")
                }
                items(upcomingEvents) { event ->
                    EventItem(
                        title = event.name,
                        date = event.date,
                        isCompleted = event.isCompleted,
                        onEdit = { parentNavController.navigate("edit_event/${event.id}") },
                        onDelete = { viewModel.deleteEventById(event.id) },
                        onMarkAsDone = { viewModel.toggleCompletedById(event.id) }
                    )
                }
            }

            if (completedEvents.isNotEmpty()) {
                item {
                    SectionTitle("Completed")
                }
                items(completedEvents) { event ->
                    CompletedEventItem(
                        title = event.name,
                        date = event.date
                    )
                }
            }
        }
    }
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
    title: String,
    date: String,
    isCompleted: Boolean,
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
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_calendar),
                            contentDescription = "Date",
                            tint =  MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(end = 4.dp)
                        )
                        Text(
                            text = date,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = onEdit,
                        enabled = !isCompleted
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_edit),
                            contentDescription = "Edit",
                            tint = Color(0xFF333333),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(
                        onClick = onDelete,
                        enabled = !isCompleted
                    ) {
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
                                id = if (isCompleted) R.drawable.icon_checkbox_checked
                                else R.drawable.icon_checkbox_unchecked
                            ),
                            contentDescription = if (isCompleted) "Completed" else "Mark as Done",
                            tint = if (isCompleted) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CompletedEventItem(title: String, date: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = 	MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(date, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}
