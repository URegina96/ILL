package com.example.ill.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY date DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :searchQuery || '%'")
    fun searchNotes(searchQuery: String): Flow<List<Note>>
}
