package com.example.ill.room

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(id: Int) {
        noteDao.delete(id)
    }

    fun searchNotes(searchQuery: String): Flow<List<Note>> {
        return noteDao.searchNotes(searchQuery)
    }
}
