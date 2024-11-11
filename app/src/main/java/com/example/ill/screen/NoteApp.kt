package com.example.ill.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ill.AddNoteDialog
import com.example.ill.room.Note
import com.example.ill.room.NoteDatabase
import com.example.ill.room.NoteRepository
import com.example.ill.screen.NoteItem
import com.example.ill.viewModel.getViewModel
import com.example.ill.viewModel.NoteViewModel
import com.example.ill.viewModel.NoteViewModelFactory

@Composable
fun NoteApp() {
    val context = LocalContext.current
    val noteViewModel: NoteViewModel = getViewModel(factory = NoteViewModelFactory(NoteRepository(
        NoteDatabase.getDatabase(context).noteDao())))
    val allNotes by noteViewModel.allNotes.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // LazyColumn для отображения всех заметок
                LazyColumn {
                    items(allNotes) { note ->
                        NoteItem(note = note, onClick = { /* действия по клику, если необходимо */ })
                    }
                }
            }
        }
    )

    if (showDialog) {
        AddNoteDialog(onDismiss = { showDialog = false }, onAddNote = { note ->
            noteViewModel.insert(note)
            showDialog = false // Закрыть диалог после добавления заметки
        })
    }
}

