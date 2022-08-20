package com.example.to_do.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.to_do.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}