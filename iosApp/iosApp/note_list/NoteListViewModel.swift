//
//  NoteListViewModel.swift
//  iosApp
//
//  Created by Mehmet Gür on 12.08.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteListScreen {
    @MainActor class NoteListViewModel: ObservableObject {
        private var noteDataSource: NoteDataSource? = nil
        
        private let searchNotesUseCase = SearchNotesUseCase()
        
        private var notes = [Note]()
        @Published private(set) var filteredNotes = [Note]()
        @Published var searchText = "" {
            didSet {
                self.filteredNotes = searchNotesUseCase.execute(notes: self.notes, query: searchText)
            }
        }
        @Published private(set) var isSearchActive = false
        
        init(noteDataSource: NoteDataSource? = nil) {
            self.noteDataSource = noteDataSource
        }
        
        func loadNotes() {
            noteDataSource?.getAllNotes(completionHandler: { notes, error in
                self.notes = notes ?? []
                self.filteredNotes = self.notes
            })
        }
        
        func deleteNote(id: Int64?) {
            if id != nil {
                noteDataSource?.deleteNote(id: id!, completionHandler: { error in
                    self.loadNotes()
                })
            }
        }
        
        func toggleIsSearchActive() {
            isSearchActive = !isSearchActive
            if !isSearchActive {
                searchText = ""
            }
        }
        
        func setNoteDataSource(dataSource: NoteDataSource) {
            self.noteDataSource = dataSource
            dataSource.insertNote(note: Note(id: nil, title: "NOTE TITLE", content: "noteContent", colorHex: 0xFF2345, created: DateTimeUtil().now()), completionHandler: {
                error in 
            })
        }
    }
}
