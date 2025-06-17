package com.l0123137.tesprojek.ui.screen.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.l0123137.tesprojek.ToDuhApplication
import com.l0123137.tesprojek.ui.ViewModelFactory
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavHostController
) {
    val application = LocalContext.current.applicationContext as ToDuhApplication

    val viewModel: CalendarViewModel = viewModel(
        factory = ViewModelFactory(
            application.userRepository,
            application.sessionRepository,
            application.eventRepository
        )
    )
    val uiState by viewModel.uiState.collectAsState()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val selectedDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
            viewModel.onDateSelected(selectedDate)
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        DatePicker(state = datePickerState)
        Spacer(modifier = Modifier.height(16.dp))

        val selectedDateFormatted = uiState.selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
        Text("Event Tanggal $selectedDateFormatted:", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.eventsForSelectedDate.isEmpty()) {
            Text("Tidak ada event.", fontSize = 16.sp, fontWeight = FontWeight.Normal)
        } else {
            val expandedStates = remember { mutableStateMapOf<Long, Boolean>() }
            uiState.eventsForSelectedDate.forEach { event ->
                val isExpanded = expandedStates[event.id] ?: false
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { expandedStates[event.id] = !isExpanded },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = event.name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = "Expand"
                            )
                        }
                        if (isExpanded) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Category: ${event.category}", fontSize = 14.sp)
                            event.description?.let {
                                Text(text = "Description: $it", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
