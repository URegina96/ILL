package com.example.ill.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ill.room.Note
import com.example.ill.room.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes: Flow<List<Note>> = repository.allNotes

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    fun searchNotes(searchQuery: String): Flow<List<Note>> {
        return repository.searchNotes(searchQuery)
    }
}
