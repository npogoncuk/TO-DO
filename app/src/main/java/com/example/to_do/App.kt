package com.example.to_do

import android.app.Application
import androidx.room.Room
import com.example.to_do.data.AppDatabase
import com.example.to_do.data.NoteDao

class App: Application() {

    private lateinit var _database: AppDatabase
    val database get() = _database
    private lateinit var _noteDao: NoteDao
    val noteDao get() = _noteDao

    companion object {
        private var _instance: App? = null
        val instance get() = _instance!!
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
        _database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app-db")
            .allowMainThreadQueries()
            .build()
        _noteDao = _database.noteDao()
    }
}