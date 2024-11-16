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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteApp() {
    val context = LocalContext.current
    val noteViewModel: NoteViewModel = getViewModel(factory = NoteViewModelFactory(NoteRepository(
        NoteDatabase.getDatabase(context).noteDao())))
    val allNotes by noteViewModel.allNotes.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var isListView by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Note App") },
                actions = {
                    IconButton(onClick = { isListView = !isListView }) {
                        Icon(if (isListView) Icons.Default.ViewModule else Icons.Default.ViewList, contentDescription = "Switch View")
                    }
                }
            )
        },
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
                if (isListView) {
                    LazyColumn {
                        items(allNotes) { note ->
                            NoteItem(note = note, onClick = { /* действия по клику, если необходимо */ })
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 128.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(allNotes) { note ->
                            StickerNoteItem(note = note, onClick = { /* действия по клику, если необходимо */ })
                        }
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

