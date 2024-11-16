@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ill

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import com.example.ill.room.Note
import com.example.ill.ui.theme.PastelBlue
import com.example.ill.ui.theme.PastelGray
import com.example.ill.ui.theme.PastelGreen
import com.example.ill.ui.theme.PastelOrange

@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteDialog(onDismiss: () -> Unit, onAddNote: (Note) -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(1) }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Note") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PastelBlue,
                        unfocusedBorderColor = PastelGray,
                        cursorColor = PastelBlue,
                        focusedLabelColor = PastelBlue,
                        unfocusedLabelColor = PastelGray
                    )
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PastelGreen,
                        unfocusedBorderColor = PastelGray,
                        cursorColor = PastelGreen,
                        focusedLabelColor = PastelGreen,
                        unfocusedLabelColor = PastelGray
                    )
                )
                OutlinedTextField(
                    value = priority.toString(),
                    onValueChange = { priority = it.toIntOrNull() ?: 1 },
                    label = { Text("Priority (1-3)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PastelOrange,
                        unfocusedBorderColor = PastelGray,
                        cursorColor = PastelOrange,
                        focusedLabelColor = PastelOrange,
                        unfocusedLabelColor = PastelGray
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isBlank() || content.isBlank()) {
                        Toast.makeText(context, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
                    } else {
                        onAddNote(
                            Note(
                                title = title,
                                content = content,
                                priority = priority,
                                date = System.currentTimeMillis()
                            )
                        )
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PastelBlue)
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = PastelGray)
            ) {
                Text("Cancel")
            }
        },
        containerColor = Color.White
    )
}
