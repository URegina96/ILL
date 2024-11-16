package com.example.ill.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ill.AddNoteDialog
import com.example.ill.FilterBar
import com.example.ill.ilterBar.FilterOption
import com.example.ill.room.NoteDatabase
import com.example.ill.room.NoteRepository
import com.example.ill.viewModel.getViewModel
import com.example.ill.viewModel.NoteViewModel
import com.example.ill.viewModel.NoteViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteApp() {
    val context = LocalContext.current
    val noteViewModel: NoteViewModel = getViewModel(factory = NoteViewModelFactory(NoteRepository(
        NoteDatabase.getDatabase(context).noteDao())))
    val allNotes by noteViewModel.allNotes.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var isListView by remember { mutableStateOf(true) }
    var filterOption by remember { mutableStateOf<FilterOption?>(null) }
    var filteredNotes by remember { mutableStateOf(allNotes) }

    LaunchedEffect(allNotes, filterOption) {
        filteredNotes = when (filterOption) {
            FilterOption.BY_TITLE -> allNotes.sortedBy { it.title }
            FilterOption.BY_DATE -> allNotes.sortedBy { it.date }
            FilterOption.BY_PRIORITY -> allNotes.sortedBy { it.priority }
            else -> allNotes
        }
    }

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
                FilterBar(filterOption) { newFilter ->
                    filterOption = newFilter
                }
                if (isListView) {
                    LazyColumn {
                        items(filteredNotes) { note ->
                            NoteItem(note = note, onClick = { /* действия по клику, если необходимо */ })
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 128.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(filteredNotes) { note ->
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
