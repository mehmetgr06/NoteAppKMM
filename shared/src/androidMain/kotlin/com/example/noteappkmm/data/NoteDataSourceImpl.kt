package com.example.noteappkmm.data

import com.example.noteappkmm.database.NoteDatabase
import com.example.noteappkmm.domain.DateTimeUtil
import com.example.noteappkmm.domain.Note
import com.example.noteappkmm.domain.NoteDataSource
import com.example.noteappkmm.domain.toNote

class NoteDataSourceImpl(
    database: NoteDatabase
) : NoteDataSource {

    private val queries = database.noteQueries

    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            created = DateTimeUtil.toEpochMillis(note.created)
        )
    }

    override suspend fun getNote(id: Long): Note? =
        queries.getNote(id).executeAsOneOrNull()?.toNote()

    override suspend fun getAllNotes(): List<Note> = queries.getAllNotes().executeAsList().map {
        it.toNote()
    }

    override suspend fun deleteNote(id: Long) = queries.deleteNote(id)
}