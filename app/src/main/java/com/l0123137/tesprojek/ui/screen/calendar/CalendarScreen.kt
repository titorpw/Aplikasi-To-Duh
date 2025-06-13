package com.l0123137.tesprojek.ui.screen.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.ui.Alignment
import com.l0123137.tesprojek.ui.screen.createEvent.EventViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavHostController,
    eventViewModel: EventViewModel
) {
    val allEvents = eventViewModel.eventList
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val datePickerState = rememberDatePickerState()
    val selectedDateMillis = datePickerState.selectedDateMillis

    val selectedDate = selectedDateMillis?.let {
        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    val eventsOnSelectedDate = selectedDate?.let { selected ->
        allEvents.filter {
            try {
                LocalDate.parse(it.date, formatter) == selected
            } catch (e: Exception) {
                false
            }
        }
    } ?: emptyList()

    // Menyimpan status expand untuk tiap event berdasarkan ID
    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        DatePicker(state = datePickerState)
        Spacer(modifier = Modifier.height(16.dp))

        if (selectedDate != null) {
            Text("Event Tanggal $selectedDate:", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            if (eventsOnSelectedDate.isEmpty()) {
                Text("Tidak ada event.", fontSize = 16.sp, fontWeight = FontWeight.Normal)
            } else {
                eventsOnSelectedDate.forEach { event ->
                    val isExpanded = expandedStates[event.id] ?: false
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                expandedStates[event.id] = !isExpanded
                            },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                            Text(
                                text = event.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = if (isExpanded) "Collapse" else "Expand"
                            ) }
                            if (isExpanded) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Category   : ${event.category}", fontSize = 14.sp)
                                Text(text = "Description: ${event.description}", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        } else {
            Text(
                text = "Pilih tanggal untuk melihat event.",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
