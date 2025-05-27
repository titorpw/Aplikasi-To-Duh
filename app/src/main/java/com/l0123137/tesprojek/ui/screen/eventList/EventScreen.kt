package com.l0123137.tesprojek.ui.screen.eventList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.l0123137.tesprojek.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen() {
    Scaffold(
        topBar = {
            Surface(
                shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp),
                shadowElevation = 4.dp,
                color = Color(0xFF0A3D4F)
            ) {
                TopAppBar(
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.logo_to_duh),
                            contentDescription = "Logo",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(50.dp)
                                .scale(4f)
                                .padding(25.dp, 8.dp, 0.dp, 0.dp),
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.height(80.dp)
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF0A3D4F),
                contentColor = Color.White
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* TODO: Handle list action */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_list),
                            contentDescription = "List",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    IconButton(onClick = { /* TODO: Handle add action */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_add),
                            contentDescription = "Add",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    IconButton(onClick = { /* TODO: Handle search action */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_search),
                            contentDescription = "Search",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    IconButton(onClick = { /* TODO: Handle settings action */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_settings),
                            contentDescription = "Settings",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    ListScreen()
}