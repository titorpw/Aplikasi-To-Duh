package com.l0123137.tesprojek.ui.screen.signup

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun CustomDatePicker(
    label: String = "Born Date",
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    showError: Boolean,
    modifier: Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = selectedDate?.format(DateTimeFormatter.ofPattern("dd / MM / yyyy")) ?: "",
            onValueChange = {},
            label = { Text("$label*") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    datePickerDialog.show()
                }) {
                    Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Select Date")
                }
            },
            isError = showError,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF6EE0E0),
                unfocusedContainerColor = Color(0xFF6EE0E0),
            )
        )
        if (showError) {
            Text(
                text = "$label is required",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}