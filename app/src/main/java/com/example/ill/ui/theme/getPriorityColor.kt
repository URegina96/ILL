package com.example.ill.ui.theme

import androidx.compose.ui.graphics.Color

fun getPriorityColor(priority: Int): Color {
    return when (priority) {
        3 -> Color(0xFF81C784)
        2 -> Color(0xFFFFF59D)
        1 -> Color(0xFFFF8A65)
        else -> Color.LightGray
    }
}
