package com.example.to_do.screens.main

import androidx.lifecycle.ViewModel
import com.example.to_do.App

class MainViewModel: ViewModel() {
    val notesLiveData get() = App.instance.noteDao.getAllLiveData()
}