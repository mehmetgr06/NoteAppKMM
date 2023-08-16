package com.example.noteappkmm.di

import com.example.noteappkmm.data.DatabaseDriverFactory
import com.example.noteappkmm.data.NoteDataSourceImpl
import com.example.noteappkmm.database.NoteDatabase
import com.example.noteappkmm.domain.NoteDataSource

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val noteDataSource: NoteDataSource by lazy {
        NoteDataSourceImpl(NoteDatabase(factory.createDriver()))
    }
}