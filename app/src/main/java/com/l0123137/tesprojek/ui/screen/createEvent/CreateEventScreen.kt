package com.l0123137.tesprojek.ui.screen.createEvent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Calendar
import android.app.DatePickerDialog
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    navController: NavController,
    createEventViewModel: CreateEventViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    eventViewModel: EventViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val eventName by createEventViewModel::eventName
    val category by createEventViewModel::selectedCategory
    val date by createEventViewModel::date
    val description by createEventViewModel::description
    val showValidationError by createEventViewModel::showValidationError

    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }
    val calendar = remember { Calendar.getInstance() }

    LaunchedEffect(showDatePicker) {
        if (showDatePicker) {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val formattedDate = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
                    createEventViewModel.updateDate(formattedDate)
                    showDatePicker = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        Text(
            text = "Create Event",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = eventName,
            onValueChange = { createEventViewModel.updateEventName(it) },
            label = { Text("Event*", color = MaterialTheme.colorScheme.onPrimary) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category*", color = MaterialTheme.colorScheme.onPrimary) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "dropdown arrow",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                createEventViewModel.categoryList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            createEventViewModel.updateCategory(item)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = date,
            onValueChange = {},
            label = { Text("Date*", color = MaterialTheme.colorScheme.onPrimary) },
            trailingIcon = {
                Icon(
                    Icons.Default.CalendarToday,
                    contentDescription = "calendar",
                    modifier = Modifier.clickable { showDatePicker = true },
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary
            ),
            readOnly = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { createEventViewModel.updateDescription(it) },
            label = { Text("Description", color = MaterialTheme.colorScheme.onPrimary) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary
            )
        )

        if (showValidationError) {
            Text(
                text = "*This field is required",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                createEventViewModel.onSubmit(eventViewModel)
                if (!createEventViewModel.showValidationError) {
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(120.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007ACC)
            )
        ) {
            Text("Create", color = Color.White)
        }
    }
}