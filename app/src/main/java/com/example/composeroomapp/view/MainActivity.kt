package com.example.composeroomapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.composeroomapp.viewmodel.TaskViewModelFactory
import com.example.composeroomapp.data.AppDatabase
import com.example.composeroomapp.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar la base de datos
        val database = AppDatabase.getDatabase(this)
        val viewModelFactory = TaskViewModelFactory(database.taskDao())
        val taskViewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]

        setContent {
            TaskApp(taskViewModel)
        }
    }
}