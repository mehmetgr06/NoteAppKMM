//
//  NoteDetailViewModel.swift
//  iosApp
//
//  Created by Mehmet Gür on 16.08.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteDetailView {
    @MainActor class NoteDetailViewModel: ObservableObject {
        private var noteDataSource: NoteDataSource?
        
        private var noteId: Int64? = nil
        @Published var noteTitle = ""
        @Published var noteContent = ""
        @Published private(set) var noteColor = Note.Companion().generateColors()
        
        init(noteDataSource: NoteDataSource? = nil) {
            self.noteDataSource = noteDataSource
        }
        
        func loadNote(id: Int64?) {
            if id != nil {
                self.noteId = id
                noteDataSource?.getNote(id: id!, completionHandler: {
                    note, error in
                    self.noteTitle = note?.title ?? ""
                    self.noteContent = note?.content ?? ""
                    self.noteColor = note?.colorHex ?? Note.Companion().generateColors()
                })
            }
        }
        
        func saveNote(onSaved: @escaping () -> Void) {
            noteDataSource?.insertNote(note: Note(
                id: noteId == nil ? nil : KotlinLong(value: noteId!),
                title: self.noteTitle,
                content: self.noteContent,
                colorHex: self.noteColor,
                created: DateTimeUtil().now()),
                completionHandler: { error in
                onSaved()
            })
        }
        
        func setParams(noteDataSource: NoteDataSource, noteId: Int64?) {
            self.noteDataSource = noteDataSource
            loadNote(id: noteId)
        }
        
    }
}
