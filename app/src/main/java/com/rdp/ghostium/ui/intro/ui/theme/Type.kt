package com.rdp.ghostium.ui.intro.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rdp.ghostium.R

// Set of Material typography styles to start with
val motserrat = FontFamily(
    Font(R.font.montserrat_compose),
    Font(R.font.montserrat_compose_bold, FontWeight.Bold)
)


val Typography = Typography(
    body1 = TextStyle(
        fontFamily = motserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    h1 = TextStyle(
        fontFamily = motserrat,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    )
)