package com.example.ill

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FilterBar(filterOption: FilterOption?, onFilterChange: (FilterOption?) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        color = Color(0xFFF0F4C3), // Пастельный цвет фона
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Filter by:",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF33691E), // Пастельный цвет текста
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.FilterList, contentDescription = "Filter Options")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Title, contentDescription = "By Title", tint = Color(0xFF64B5F6)) // Пастельный цвет иконки
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Title", color = Color(0xFF64B5F6)) // Пастельный цвет текста
                            }
                        },
                        onClick = {
                            onFilterChange(FilterOption.BY_TITLE)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.DateRange, contentDescription = "By Date", tint = Color(0xFF81C784)) // Пастельный цвет иконки
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Date: New to Old", color = Color(0xFF81C784)) // Пастельный цвет текста
                            }
                        },
                        onClick = {
                            onFilterChange(FilterOption.BY_DATE_DESC)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.DateRange, contentDescription = "By Date", tint = Color(0xFF81C784)) // Пастельный цвет иконки
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Date: Old to New", color = Color(0xFF81C784)) // Пастельный цвет текста
                            }
                        },
                        onClick = {
                            onFilterChange(FilterOption.BY_DATE_ASC)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.PriorityHigh, contentDescription = "By Priority", tint = Color(0xFFFF8A65)) // Пастельный цвет иконки
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Priority", color = Color(0xFFFF8A65)) // Пастельный цвет текста
                            }
                        },
                        onClick = {
                            onFilterChange(FilterOption.BY_PRIORITY)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear Filter", tint = Color(0xFFBDBDBD)) // Пастельный цвет иконки
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Clear", color = Color(0xFFBDBDBD)) // Пастельный цвет текста
                            }
                        },
                        onClick = {
                            onFilterChange(null)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
