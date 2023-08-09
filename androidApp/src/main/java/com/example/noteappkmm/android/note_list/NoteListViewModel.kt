package com.example.noteappkmm.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappkmm.domain.DateTimeUtil
import com.example.noteappkmm.domain.Note
import com.example.noteappkmm.domain.NoteDataSource
import com.example.noteappkmm.domain.SearchNotesUseCase
import com.example.noteappkmm.ui.RedOrangeHex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchNotesUseCase = SearchNotesUseCase()

    private val notes = savedStateHandle.getStateFlow("notes", emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val state = combine(notes, searchText, isSearchActive) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = searchNotesUseCase.execute(notes, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteListState())

    init {
        viewModelScope.launch {
            (1..10).forEach {
                noteDataSource.insertNote(
                    Note(
                        null,
                        "title $it",
                        "content $it",
                        RedOrangeHex,
                        DateTimeUtil.now()
                    )
                )
            }
        }
    }

    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle["notes"] = noteDataSource.getAllNotes()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle["searchText"] = text
    }

    fun onToggleSearch() {
        savedStateHandle["isSearchActive"] = isSearchActive.value.not()
        if (isSearchActive.value.not()) {
            savedStateHandle["searchText"] = ""
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNote(id)
            loadNotes()
        }
    }

}