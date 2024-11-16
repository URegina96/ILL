package com.example.ill.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.ill.AddNoteDialog
import com.example.ill.FilterBar
import com.example.ill.FilterOption
import com.example.ill.R
import com.example.ill.button.AddNoteButton
import com.example.ill.button.DeleteAllNotesButton
import com.example.ill.room.NoteDatabase
import com.example.ill.room.NoteRepository
import com.example.ill.viewModel.getViewModel
import com.example.ill.viewModel.NoteViewModel
import com.example.ill.viewModel.NoteViewModelFactory
import com.example.ill.ui.theme.PastelBlue
import com.example.ill.ui.theme.PastelGray
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteApp() {
    val context = LocalContext.current
    val noteViewModel: NoteViewModel = getViewModel(
        factory = NoteViewModelFactory(
            NoteRepository(
                NoteDatabase.getDatabase(context).noteDao()
            )
        )
    )
    val allNotes by noteViewModel.allNotes.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var isListView by remember { mutableStateOf(true) }
    var filterOption by remember { mutableStateOf<FilterOption?>(null) }

    val filteredNotes = produceState(initialValue = allNotes, allNotes, filterOption) {
        value = when (filterOption) {
            FilterOption.BY_TITLE -> allNotes.sortedBy { it.title }
            FilterOption.BY_DATE_DESC -> allNotes.sortedByDescending { it.date }
            FilterOption.BY_DATE_ASC -> allNotes.sortedBy { it.date }
            FilterOption.BY_PRIORITY -> allNotes.sortedBy { it.priority }
            else -> allNotes
        }
    }.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Note App",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { isListView = !isListView }) {
                        Icon(
                            imageVector = if (isListView) Icons.Default.ViewModule else Icons.Default.ViewList,
                            contentDescription = "Switch View",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PastelGray
                )
            )
        },
        floatingActionButton = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                AddNoteButton(onClick = { showDialog = true })
                Spacer(modifier = Modifier.height(16.dp))
                DeleteAllNotesButton(onClick = {
                    noteViewModel.allNotes.value.forEach { noteViewModel.delete(it) }
                })
            }
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Image(
                    painter = rememberImagePainter(data = R.drawable.background_image1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.3f) // Прозрачность изображения фона
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    FilterBar(filterOption) { newFilter ->
                        filterOption = newFilter
                    }
                    if (isListView) {
                        LazyColumn {
                            items(filteredNotes) { note ->
                                NoteItem(
                                    note = note,
                                    onClick = { /* действия по клику, если необходимо */ },
                                    onDelete = { noteViewModel.delete(note) },
                                    onEdit = { updatedNote -> noteViewModel.update(updatedNote) }
                                )
                            }
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 128.dp),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(filteredNotes) { note ->
                                StickerNoteItem(
                                    note = note,
                                    onClick = { /* действия по клику, если необходимо */ },
                                    onDelete = { noteViewModel.delete(note) },
                                    onEdit = { updatedNote -> noteViewModel.update(updatedNote) }
                                )
                            }
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
