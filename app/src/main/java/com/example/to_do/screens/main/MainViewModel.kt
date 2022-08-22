package com.example.to_do.screens.main


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.to_do.App

class MainViewModel: ViewModel() {
    val notesLiveData get() = App.instance.noteDao.getAllLiveData()
    val colorOfTasks = MutableLiveData<Drawable>()
    fun getCol() = colorOfTasks
    init {
        colorOfTasks.value = ColorDrawable(Color.MAGENTA)
    }
}