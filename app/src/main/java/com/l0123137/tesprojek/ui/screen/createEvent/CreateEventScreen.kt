package com.l0123137.tesprojek.ui.screen.createEvent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    navController: NavController,
    viewModel: CreateEventViewModel = viewModel()
) {
    val eventName = viewModel.eventName
    val category = viewModel.selectedCategory
    val date = viewModel.date
    val description = viewModel.description
    val showValidationError = viewModel.showValidationError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Event",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF0050B3),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = eventName,
            onValueChange = { viewModel.eventName = it },
            label = { Text("Event*") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF3ED8D8),
                unfocusedContainerColor = Color(0xFF3ED8D8)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category*") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF3ED8D8),
                    unfocusedContainerColor = Color(0xFF3ED8D8)
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                viewModel.categoryList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            viewModel.selectedCategory = item
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = date,
            onValueChange = { viewModel.date = it },
            label = { Text("Date*") },
            trailingIcon = {
                Icon(Icons.Default.CalendarToday, contentDescription = "calendar")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF3ED8D8),
                unfocusedContainerColor = Color(0xFF3ED8D8)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { viewModel.description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF3ED8D8),
                unfocusedContainerColor = Color(0xFF3ED8D8)
            )
        )

        if (showValidationError) {
            Text(
                text = "*This field required to fill",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.onSubmit() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(120.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007ACC)
            )
        ) {
            Text("Create")
        }
    }
}