package com.l0123137.tesprojek.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.l0123137.tesprojek.R
import androidx.compose.material3.Typography

val CreatoDisplay = FontFamily(
    Font(R.font.creato_display_thin, FontWeight.Thin),
    Font(R.font.creato_display_light, FontWeight.Light),
    Font(R.font.creato_display_regular, FontWeight.Normal),
    Font(R.font.creato_display_medium, FontWeight.Medium),
    Font(R.font.creato_display_bold, FontWeight.Bold),
    Font(R.font.creato_display_black, FontWeight.Black),
    Font(R.font.creato_display_extrabold, FontWeight.ExtraBold),
)

val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = CreatoDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = CreatoDisplay,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    )
)
