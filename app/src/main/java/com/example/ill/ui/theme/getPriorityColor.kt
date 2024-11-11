package com.example.ill.ui.theme

import androidx.compose.ui.graphics.Color


fun getPriorityColor(priority: Int): Color {
    return when (priority) {
        1 -> Color.Red
        2 -> Color.Yellow
        3 -> Color.Green
        else -> Color.Gray
    }
}
