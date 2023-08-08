package com.example.noteappkmm.domain

interface NoteDataSource {
    suspend fun insertNote(note: Note)
    suspend fun getNote(id: Long): Note?
    suspend fun getAllNotes(): List<Note>
    suspend fun deleteNote(id: Long)
}