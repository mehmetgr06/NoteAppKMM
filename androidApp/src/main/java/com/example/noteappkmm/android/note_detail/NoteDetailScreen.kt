package com.example.noteappkmm.android.note_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NoteDetailScreen(
    noteId: Long,
    viewModel: NoteDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val isNoteSaved by viewModel.isNoteSaved.collectAsState()

    LaunchedEffect(key1 = isNoteSaved) {
        if (isNoteSaved) {
            // todo:
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::saveNote,
                backgroundColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save Note",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(Color(state.noteColor))
                .fillMaxWidth()
                .padding(padding)
        ) {
            TransparentHintTextField(
                text = state.noteTitle,
                hint = "Enter a title...",
                isHintVisible = state.isNoteTitleHintVisible,
                onValueChanged = viewModel::onNoteTitleChanged,
                onFocusChanged = {
                    viewModel.onNoteTitleFocusChanged(it.isFocused)
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.noteContent,
                hint = "Enter a content...",
                isHintVisible = state.isNoteContentHintVisible,
                onValueChanged = viewModel::onNoteContentChanged,
                onFocusChanged = {
                    viewModel.onNoteContentFocusChanged(it.isFocused)
                },
                singleLine = false,
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier.weight(1f)
            )
        }
    }
}