//
//  NoteListScreen.swift
//  iosApp
//
//  Created by Mehmet Gür on 12.08.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteListScreen: View {
    private var noteDataSource: NoteDataSource
    @StateObject var viewModel = NoteListViewModel(noteDataSource: nil)
    
    init(noteDataSource: NoteDataSource) {
        self.noteDataSource = noteDataSource
    }
    
    var body: some View {
        VStack {
            ZStack {
                SearchTextField<EmptyView>(
                    onSearchToggled: {
                        viewModel.toggleIsSearchActive()
                        
                    },
                    destinationProvider: {
                        EmptyView()
                    },
                    isSearchActive: viewModel.isSearchActive,
                    searchText: $viewModel.searchText
                )
                    .frame(maxWidth: .infinity, minHeight: 40)
                    .padding()
                
                if !viewModel.isSearchActive {
                    Text("All Notes")
                        .font(.title2)
                }
                
                List {
                    ForEach(viewModel.filteredNotes, id: \.self.id) { note in
                        Button(action: {
                            
                        }) {
                            NoteItem(note: note, onDeleteClick: {
                                viewModel.deleteNote(
                                    id:note.id?.int64Value
                                )
                            })
                        }
                    }
                    
                }.onAppear {
                    viewModel.loadNotes()
                }
                .listStyle(.plain)
                .listRowSeparator(.hidden)
            }
        }.onAppear {
            viewModel.setNoteDataSource(dataSource: noteDataSource)
        }
    }
}

struct NoteListScreen_Previews: PreviewProvider {
    static var previews: some View {
        NoteListScreen()
    }
}
