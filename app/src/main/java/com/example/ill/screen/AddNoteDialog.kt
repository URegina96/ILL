package com.example.ill

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.ill.room.Note
import com.example.ill.ui.theme.AppTheme

@Composable
fun AddNoteDialog(onDismiss: () -> Unit, onAddNote: (Note) -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(1) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Note") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = priority.toString(),
                    onValueChange = { priority = it.toIntOrNull() ?: 1 },
                    label = { Text("Priority (1-3)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onAddNote(Note(
                    title = title,
                    content = content,
                    priority = priority,
                    date = System.currentTimeMillis()
                ))
                onDismiss()
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
