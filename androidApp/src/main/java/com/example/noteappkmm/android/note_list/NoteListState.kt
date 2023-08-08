package com.example.noteappkmm.android.note_list

import com.example.noteappkmm.domain.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)
