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
            color = Color(0xFF0055CC),
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
                        onEdit = { /* TODO: Implement edit screen navigation */ },
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
        color = Color(0xFFD0E7FF),
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
                color = Color(0xFF003366),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun EventItem(
    title: String,
    date: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onMarkAsDone: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB2F5F0)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(date, fontSize = 12.sp, color = Color.Gray)
                }
                IconButton(onClick = onEdit) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_edit),
                        contentDescription = "Edit",
                        tint = Color.Black,
                        modifier = Modifier.size(40.dp)
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_delete),
                        contentDescription = "Delete",
                        tint = Color.Black,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            Button(
                onClick = onMarkAsDone,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.End)
            ) {
                Text("Mark as Done", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun CompletedEventItem(title: String, date: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0c3d4a)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(date, fontSize = 12.sp, color = Color.Gray)
        }
    }
}
