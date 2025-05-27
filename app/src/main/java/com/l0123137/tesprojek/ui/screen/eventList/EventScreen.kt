package com.l0123137.tesprojek.ui.screen.eventList

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(parentNavController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Event List",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0055CC)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // TODO: Tambahkan daftar event di sini nanti
    }
}

