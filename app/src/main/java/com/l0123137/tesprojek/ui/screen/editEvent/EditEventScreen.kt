package com.l0123137.tesprojek.ui.screen.editEvent

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.l0123137.tesprojek.ui.screen.createEvent.Event
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEventScreen(
    navController: NavController,
    event: Event,
    onUpdateEvent: (Event) -> Unit
) {
    var name by remember { mutableStateOf(event.name) }
    var category by remember { mutableStateOf(event.category) }
    var date by remember { mutableStateOf(event.date) }
    var description by remember { mutableStateOf(event.description) }

    var nameError by remember { mutableStateOf(false) }
    var categoryError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }

    val categoryOptions = listOf("Work", "Study", "Meeting", "Personal")
    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            calendar.set(year, month, day)
            date = dateFormatter.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Edit Event",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = false
            },
            isError = nameError,
            label = { Text("Event Name*") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF3ED8D8),
                unfocusedContainerColor = Color(0xFF3ED8D8)
            )
        )
        if (nameError) {
            Text("Event name is required", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                isError = categoryError,
                label = { Text("Category*") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF3ED8D8),
                    unfocusedContainerColor = Color(0xFF3ED8D8)
                )
            )
            ExposedDropdownMenu(expanded, onDismissRequest = { expanded = false }) {
                categoryOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            category = option
                            categoryError = false
                            expanded = false
                        }
                    )
                }
            }
        }
        if (categoryError) {
            Text("Category is required", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = date,
            onValueChange = {},
            readOnly = true,
            isError = dateError,
            label = { Text("Date*") },
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF3ED8D8),
                unfocusedContainerColor = Color(0xFF3ED8D8)
            )
        )
        if (dateError) {
            Text("Date is required", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF3ED8D8),
                unfocusedContainerColor = Color(0xFF3ED8D8)
            )
        )

        Spacer(Modifier.height(24.dp))

        androidx.compose.material3.Button(
            onClick = {
                nameError = name.isBlank()
                categoryError = category.isBlank()
                dateError = date.isBlank()

                if (!nameError && !categoryError && !dateError) {
                    val updatedEvent = event.copy(
                        name = name,
                        category = category,
                        date = date,
                        description = description
                    )
                    onUpdateEvent(updatedEvent)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(120.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007ACC)
            )
        ) {
            Text("Edit", color = Color.White)
        }
    }
}
