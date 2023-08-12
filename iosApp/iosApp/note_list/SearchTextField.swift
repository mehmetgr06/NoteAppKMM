//
//  SearchTextField.swift
//  iosApp
//
//  Created by Mehmet Gür on 12.08.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SearchTextField<Destination : View>: View {
    
    var onSearchToggled: () -> Void
    var destinationProvider: () -> Destination
    var isSearchActive: Bool
    @Binding var searchText: String
    
    var body: some View {
        HStack {
            TextField("Search...", text: $searchText)
                .textFieldStyle(.roundedBorder)
                .opacity(isSearchActive ? 1 : 0)
            if !isSearchActive {
                Spacer()
            }
            Button(action : onSearchToggled) {
                Image(systemName: isSearchActive ? "xmark" : "magnifyingglass")
            }
            NavigationLink(destination: destinationProvider()) {
                Image(systemName: "plus")
            }
        }
    }
}

struct SearchTextField_Previews: PreviewProvider {
    static var previews: some View {
        SearchTextField(
            onSearchToggled: { },
            destinationProvider: {EmptyView()},
            isSearchActive: true,
            searchText: .constant("Hello mateq"))
    }
}
