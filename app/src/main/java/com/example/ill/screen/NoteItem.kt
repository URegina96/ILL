package com.example.ill.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ill.room.Note
import com.example.ill.ui.theme.getPriorityColor
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteItem(
    note: Note,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onEdit: (Note) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(Date(note.date))
    val showEditDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(note.title, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(note.content, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(getPriorityColor(note.priority))
                    )
                    Text(
                        text = "Priority: ${note.priority}",
                        modifier = Modifier.padding(start = 8.dp),
                        color = Color.DarkGray
                    )
                }
                Text("Date: $formattedDate", color = Color.DarkGray)
                Row {
                    IconButton(onClick = { showEditDialog.value = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Note", tint = Color.Black)
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Note", tint = Color.Black)
                    }
                }
            }
        }
    }

    if (showEditDialog.value) {
        EditNoteDialog(
            note = note,
            onDismiss = { showEditDialog.value = false },
            onSave = { updatedNote ->
                onEdit(updatedNote)
                showEditDialog.value = false
            }
        )
    }
}
