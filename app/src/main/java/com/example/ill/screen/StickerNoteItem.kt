package com.example.ill.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ill.room.Note
import com.example.ill.ui.theme.getPriorityColor
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StickerNoteItem(
    note: Note,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onEdit: (Note) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(Date(note.date))
    val showEditDialog = remember { mutableStateOf(false) }
    val showFullContentDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp)
            .clickable { showFullContentDialog.value = true },
        colors = CardDefaults.cardColors(containerColor = getPriorityColor(note.priority)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                note.title,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                note.content,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Divider(color = Color.Gray, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Date: $formattedDate", style = MaterialTheme.typography.labelSmall, color = Color.DarkGray)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { showEditDialog.value = true }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Note", tint = Color.Black)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Note", tint = Color.Black)
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

    if (showFullContentDialog.value) {
        AlertDialog(
            onDismissRequest = { showFullContentDialog.value = false },
            title = { Text(note.title) },
            text = { Text(note.content) },
            confirmButton = {
                Button(onClick = { showFullContentDialog.value = false }) {
                    Text("Close")
                }
            }
        )
    }
}
