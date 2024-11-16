package com.example.ill.button

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ill.ui.theme.PastelOrange
import com.example.ill.ui.theme.PastelYellow

@Composable
fun AddNoteButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = PastelYellow,
        modifier = modifier.padding(16.dp)
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add Note")
    }
}

@Composable
fun DeleteAllNotesButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = PastelOrange,
        modifier = modifier.padding(16.dp)
    ) {
        Icon(Icons.Default.Delete, contentDescription = "Delete All Notes")
    }
}
